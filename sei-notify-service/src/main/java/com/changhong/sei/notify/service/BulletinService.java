package com.changhong.sei.notify.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.dto.serach.SearchOrder;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.edm.sdk.DocumentManager;
import com.changhong.sei.notify.dao.BulletinDao;
import com.changhong.sei.notify.dao.BulletinUserDao;
import com.changhong.sei.notify.dao.ContentBodyDao;
import com.changhong.sei.notify.entity.Bulletin;
import com.changhong.sei.notify.entity.BulletinUser;
import com.changhong.sei.notify.entity.ContentBody;
import com.changhong.sei.notify.entity.compose.BulletinCompose;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 实现功能：维护消息通告的业务逻辑
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-23 17:46
 */
@Service
public class BulletinService extends BaseEntityService<Bulletin> {

    @Autowired
    private BulletinDao dao;
    @Autowired
    private ContentBodyDao contentBodyDao;
    @Autowired
    private BulletinUserDao bulletinUserDao;
    @Autowired(required = false)
    private DocumentManager documentManager;

    @Override
    protected BaseEntityDao<Bulletin> getDao() {
        return dao;
    }

    /**
     * 基于动态组合条件对象和分页(含排序)对象查询数据集合
     */
    @Override
    public PageResult<Bulletin> findByPage(Search searchConfig) {
        if (Objects.isNull(searchConfig)) {
            searchConfig = Search.createSearch();
        }
        searchConfig.addFilter(new SearchFilter(Bulletin.FIELD_DEL, Boolean.FALSE));
        searchConfig
                .addSortOrder(new SearchOrder("invalidDate", SearchOrder.Direction.DESC))
                .addSortOrder(new SearchOrder("priority"))
                .addSortOrder(new SearchOrder("releaseDate", SearchOrder.Direction.DESC));
        PageResult<Bulletin> result = super.findByPage(searchConfig);
        // 获取内容
        if (CollectionUtils.isEmpty(result.getRows())) {
            return result;
        }
        List<Bulletin> bulletins = result.getRows();
        bulletins.forEach(b -> {
            ContentBody contentBody = contentBodyDao.findOne(b.getContentId());
            if (Objects.nonNull(contentBody)) {
                b.setContent(contentBody.getContent());
            }
        });
        return result;
    }

    /**
     * 新增修改通告
     *
     * @param bulletin 消息通告
     * @param content  通告内容
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResult saveBulletin(Bulletin bulletin, String content) {
        if (Objects.isNull(bulletin) || StringUtils.isBlank(content)) {
            // 维护的通告以及内容不能为空！
            return OperateResult.operationFailure("00002");
        }
        Set<String> docIds = bulletin.getDocIds();

        ContentBody body = new ContentBody(content);
        String id = bulletin.getId();
        if (StringUtils.isBlank(id)) {
            // 保存内容
            contentBodyDao.save(body);
            bulletin.setContentId(body.getId());
            // 保存通告
            dao.save(bulletin);
        } else {
            // 编辑
            Bulletin editBulletin = dao.findOne(id);
            if (Objects.isNull(editBulletin)) {
                // 编辑失败,通告【{0}】不存在!
                return OperateResult.operationFailure("00003", id);
            }
            // 删除原内容
            String contentId = bulletin.getContentId();
            if (StringUtils.isNotBlank(contentId)) {
                contentBodyDao.delete(contentId);
            }
            // 保存内容
            contentBodyDao.save(body);
            bulletin.setContentId(body.getId());
        }
        dao.save(bulletin);

        //绑定附件
        if (CollectionUtils.isNotEmpty(docIds) && Objects.nonNull(documentManager)) {
            documentManager.bindBusinessDocuments(bulletin.getId(), docIds);
        }

        return OperateResult.operationSuccess();
    }

    /**
     * 发布通告
     *
     * @param ids 要发布的通告id
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResult releaseBulletin(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            // 发布通告参数不能为空!
            return OperateResult.operationFailure("00004");
        }
        List<Bulletin> bulletins = dao.findAllById(ids);
        if (!CollectionUtils.isEmpty(bulletins)) {
            LocalDateTime date = LocalDateTime.now();
            SessionUser sessionUser = ContextUtil.getSessionUser();
            for (Bulletin bulletin : bulletins) {
                bulletin.setRelease(Boolean.TRUE);
                bulletin.setReleaseDate(date);
                bulletin.setReleaseUserAccount(sessionUser.getAccount());
                bulletin.setReleaseUserName(sessionUser.getUserName());
            }
            dao.save(bulletins);
        }
        return OperateResult.operationSuccess();
    }

    /**
     * 撤销通告
     *
     * @param ids 要撤销的通告id
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResult cancelBulletin(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            // 撤销通告参数不能为空!
            return OperateResult.operationFailure("00005");
        }
        List<Bulletin> bulletins = dao.findAllById(ids);
        if (!CollectionUtils.isEmpty(bulletins)) {
            LocalDateTime date = LocalDateTime.now();
            SessionUser sessionUser = ContextUtil.getSessionUser();
            for (Bulletin bulletin : bulletins) {
                bulletin.setRelease(Boolean.FALSE);
                bulletin.setCancelDate(date);
                bulletin.setCancelUserAccount(sessionUser.getAccount());
                bulletin.setCancelUserName(sessionUser.getUserName());
            }
            dao.save(bulletins);
            // 按通告id删除用户阅读数据
            bulletinUserDao.deleteByBulletinIdIn(ids);
        }

        return OperateResult.operationSuccess();
    }

    /**
     * 删除通告
     *
     * @param ids 要删除的通告id
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResult deleteBulletin(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            // 删除通告参数不能为空!
            return OperateResult.operationFailure("00006");
        }
        List<Bulletin> bulletins = dao.findAllById(ids);
        if (!CollectionUtils.isEmpty(bulletins)) {
            LocalDateTime date = LocalDateTime.now();
            SessionUser sessionUser = ContextUtil.getSessionUser();
            for (Bulletin bulletin : bulletins) {
                bulletin.setDel(Boolean.TRUE);
                bulletin.setDelDate(date);
                bulletin.setDelUserAccount(sessionUser.getAccount());
                bulletin.setDelUserName(sessionUser.getUserName());
            }
            dao.save(bulletins);
        }
        return OperateResult.operationSuccess();
    }

    /**
     * 查看通告
     *
     * @param id id
     * @return 返回通告明细
     */
    public BulletinCompose getBulletin(String id) {
        BulletinCompose result = new BulletinCompose();
        Bulletin bulletin = dao.findOne(id);
        if (Objects.nonNull(bulletin)) {
            result.setBulletin(bulletin);
            String contentId = bulletin.getContentId();
            if (StringUtils.isNotBlank(contentId)) {
                ContentBody body = contentBodyDao.findOne(contentId);
                if (Objects.nonNull(body)) {
                    result.setContent(body.getContent());
                }
            }
        }
        return result;
    }

    public BulletinUser saveBulletinUser(BulletinUser bulletinUser) {
        return bulletinUserDao.save(bulletinUser);
    }

    /**
     * 按通告id删除用户阅读数据
     * 通告撤销时使用
     *
     * @param bulletinIds 通告id
     */
    public void deleteByBulletinIdIn(Collection<String> bulletinIds) {
        bulletinUserDao.deleteByBulletinIdIn(bulletinIds);
    }

    public BulletinUser findByBulletinIdAndUserId(String bulletinId, String userId) {
        return bulletinUserDao.findByBulletinIdAndUserId(bulletinId, userId);
    }

    /**
     * 获取未读通告数
     */
    public Long getUnreadCount(String userId, Set<String> targetCodes) {
        return dao.getUnreadCount(userId, targetCodes);
    }

    /**
     * 获取未读通告
     */
    public List<Bulletin> getUnreadBulletin(String userId, Set<String> targetCodes) {
        return dao.getUnreadBulletin(userId, targetCodes);
    }

    /**
     * 获取优先级最高的未读通告
     */
    public Bulletin getFirstUnreadBulletin(String userId, Set<String> targetCodes) {
        return dao.getFirstUnreadBulletin(userId, targetCodes);
    }

    /**
     * 分页获取用户通告
     */
    public PageResult<BulletinCompose> findPage4User(Search search, String userId, Set<String> targetCodes) {
        return dao.findPage4User(search, userId, targetCodes);
    }

}

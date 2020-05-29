package com.changhong.sei.notify.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.notify.dao.BulletinDao;
import com.changhong.sei.notify.dao.MessageUserDao;
import com.changhong.sei.notify.entity.Bulletin;
import com.changhong.sei.notify.entity.Message;
import com.changhong.sei.notify.entity.compose.BulletinCompose;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    private MessageService messageService;
    @Autowired
    private MessageUserDao bulletinUserDao;

    @Override
    protected BaseEntityDao<Bulletin> getDao() {
        return dao;
    }

    /**
     * 新增修改通告
     *
     * @param bulletin 消息通告
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResult saveBulletin(Bulletin bulletin, Message message) {
        ResultData<Message> resultData = messageService.saveMessage(message);
        if (resultData.successful()) {
            message = resultData.getData();
            bulletin.setMsgId(message.getId());
            dao.save(bulletin);

            return OperateResult.operationSuccess();
        } else {
            return OperateResult.operationFailure(resultData.getMessage());
        }
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
            SessionUser user = ContextUtil.getSessionUser();
            String account = user.getAccount();
            String userName = user.getUserName();
            LocalDateTime now = LocalDateTime.now();

            Set<String> msgIds = new HashSet<>();
            for (Bulletin bulletin : bulletins) {
                msgIds.add(bulletin.getMsgId());

                bulletin.setPublish(Boolean.TRUE);
                bulletin.setPublishUserAccount(account);
                bulletin.setPublishUserName(userName);
                bulletin.setPublishDate(now);
            }
            dao.save(bulletins);

            messageService.publishMessage(msgIds, account, userName);
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
            Set<String> msgIds = new HashSet<>();
            SessionUser sessionUser = ContextUtil.getSessionUser();
            String account = sessionUser.getAccount();
            String userName = sessionUser.getUserName();
            for (Bulletin bulletin : bulletins) {
                msgIds.add(bulletin.getMsgId());

                bulletin.setCancelDate(date);
                bulletin.setCancelUserAccount(account);
                bulletin.setCancelUserName(userName);

                bulletin.setPublish(Boolean.FALSE);
                bulletin.setPublishUserAccount(null);
                bulletin.setPublishUserName(null);
                bulletin.setPublishDate(null);
            }
            dao.save(bulletins);

            // 撤销发布的消息
            messageService.cancelPublishMessage(msgIds);
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
        if (CollectionUtils.isNotEmpty(bulletins)) {
            for (Bulletin bulletin : bulletins) {
                if (bulletin.getPublish()) {
                    // 通告已发布不能删除
                    return OperateResult.operationFailure("00028");
                }
            }

            SessionUser user = ContextUtil.getSessionUser();
            Set<String> msgIds = bulletins.stream().map(Bulletin::getMsgId).collect(Collectors.toSet());
            messageService.delMessage(msgIds, user.getAccount(), user.getUserName());

            dao.delete(ids);
        }
        return OperateResult.operationSuccess();
    }

    /**
     * 查看通告
     *
     * @param id id
     * @return 返回通告明细
     */
    public ResultData<BulletinCompose> getBulletin(String id) {
        BulletinCompose result = new BulletinCompose();
        Bulletin bulletin = dao.findOne(id);
        if (Objects.nonNull(bulletin)) {
            result.setBulletin(bulletin);
            ResultData<Message> resultData = messageService.detail(bulletin.getMsgId());
            if (resultData.successful()) {
                result.setMessage(resultData.getData());

                return ResultData.success(result);
            } else {
                return ResultData.fail(resultData.getMessage());
            }
        }
        return ResultData.fail(id + " - 消息不存在");
    }

}

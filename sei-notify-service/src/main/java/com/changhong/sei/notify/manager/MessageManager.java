package com.changhong.sei.notify.manager;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.enums.UserType;
import com.changhong.sei.notify.dao.BulletinDao;
import com.changhong.sei.notify.dao.BulletinUserDao;
import com.changhong.sei.notify.dao.ContentBodyDao;
import com.changhong.sei.notify.dto.BaseMessageDto;
import com.changhong.sei.notify.dto.BulletinDto;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.entity.Bulletin;
import com.changhong.sei.notify.entity.BulletinUser;
import com.changhong.sei.notify.entity.ContentBody;
import com.changhong.sei.notify.entity.compose.BulletinCompose;
import com.changhong.sei.notify.service.GroupService;
import com.changhong.sei.notify.service.client.EmployeeClient;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * <strong>实现功能:</strong>
 * <p>通告消息管理业务逻辑</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-15 16:47
 */
@Component
public class MessageManager {
    @Autowired
    private BulletinDao bulletinDao;
    @Autowired
    private BulletinUserDao bulletinUserDao;
    @Autowired
    private ContentBodyDao contentBodyDao;
    @Autowired
    private GroupService groupService;
    @Autowired
    private EmployeeClient employeeClient;

    /**
     * 未读消息数
     *
     * @return 返回未读消息数
     */
    public Long unreadCount() {
        SessionUser user = ContextUtil.getSessionUser();
        long count = 0L;

        // 未读通告
        Long bulletinCount = bulletinDao.getUnreadCount(user.getUserId(), getTargetCodeByUser(user));
        if (Objects.nonNull(bulletinCount)) {
            count += bulletinCount;
        }

        // 未读消息(站内消息)


        // 未读提醒


        return count;
    }

    /**
     * 获取用户的权限集合{组织机构、岗位}
     */
    @Cacheable(value = "UserAuthorizedFeaturesCache", key = "'TargetCodeByUser:'+#userId")
    public Set<String> getTargetCodeByUser(SessionUser user) {
        Set<String> targetCodes = Sets.newHashSet();

        String userId = user.getUserId();
        if (!user.isAnonymous() && UserType.Employee == user.getUserType()) {
            // 获取用户的组织代码清单
            ResultData<List<String>> orgCodesResult = employeeClient.getEmployeeOrgCodes(userId);
            if (orgCodesResult.successful() && CollectionUtils.isNotEmpty(orgCodesResult.getData())) {
                targetCodes.addAll(orgCodesResult.getData());
            }
//        // todo 没有组织，获取用户岗位上的组织
//        ResultData<List<String>> positionCodesResult = employeeClient.getEmployeePositionCodes(userId);
//        if (positionCodesResult.successful() && CollectionUtils.isNotEmpty(positionCodesResult.getData())) {
//            return positionCodesResult.getData();
//        }
        }

        ResultData<Set<String>> groupCodeResult = groupService.getGroupCodes(userId);
        if (groupCodeResult.successful() && CollectionUtils.isNotEmpty(groupCodeResult.getData())) {
            targetCodes.addAll(groupCodeResult.getData());
        }

        return targetCodes;
    }

    /**
     * 用户未读数据
     *
     * @return 返回未读数据
     */
    public OperateResultWithData<Map<String, List<BaseMessageDto>>> unreadData() {
        SessionUser user = ContextUtil.getSessionUser();
        Map<String, List<BaseMessageDto>> data = new HashMap<>();
        List<BaseMessageDto> messageDtos = new ArrayList<>();
        // 未读通告
        List<Bulletin> bulletins = bulletinDao.getUnreadBulletin(user.getUserId(), this.getTargetCodeByUser(user));
        if (!CollectionUtils.isEmpty(bulletins)) {
            for (Bulletin bulletin : bulletins) {
                BaseMessageDto messageDto = new BaseMessageDto();
                messageDto.setId(bulletin.getId());
                messageDto.setCategory(NotifyType.SEI_BULLETIN);
                messageDto.setSubject(bulletin.getSubject());
                messageDto.setContentId(bulletin.getContentId());
                messageDto.setPriority(bulletin.getPriority());
                messageDtos.add(messageDto);
            }
            data.put(NotifyType.SEI_BULLETIN.name(), messageDtos);
        }
        // 未读消息
        // 未读提醒
        return OperateResultWithData.operationSuccessWithData(data);
    }

    /**
     * 用户阅读通告
     *
     * @param category 消息类型
     * @param id       id
     * @return 返回结果
     */
    public OperateResult read(NotifyType category, String id) {
        if (Objects.nonNull(category) && StringUtils.isNotBlank(id)) {
            SessionUser user = ContextUtil.getSessionUser();
            switch (category) {
                case SEI_BULLETIN:
                    BulletinUser bulletinUser = bulletinUserDao.findByBulletinIdAndUserId(id, user.getUserId());
                    if (Objects.isNull(bulletinUser)) {
                        bulletinUser = new BulletinUser();
                    }
                    bulletinUser.setBulletinId(id);
                    bulletinUser.setRead(Boolean.TRUE);
                    bulletinUser.setReadDate(new Date());
                    bulletinUser.setReadNum(bulletinUser.getReadNum() + 1);
                    bulletinUser.setUserId(user.getUserId());
                    bulletinUser.setUserAccount(user.getAccount());
                    bulletinUser.setUserName(user.getUserName());
                    bulletinUser.setUserType(user.getUserType());
                    bulletinUserDao.save(bulletinUser);
                    break;
                case SEI_MESSAGE:
                    break;
                case SEI_REMIND:
                    break;
                default:
                    return OperateResult.operationFailure("不支持的类型!");
            }
            return OperateResult.operationSuccess();
        } else {
            return OperateResult.operationFailure("参数不能为空!");
        }
    }

    /**
     * 用户查看
     *
     * @param category 消息类型
     * @param id       id
     * @return 返回明细
     */
    public OperateResultWithData<BaseMessageDto> detail(NotifyType category, String id) {
        if (Objects.nonNull(category) && StringUtils.isNotBlank(id)) {
            String contentId = null;
            BaseMessageDto message = new BaseMessageDto();
            switch (category) {
                case SEI_BULLETIN:
                    Bulletin bulletin = bulletinDao.findOne(id);
                    if (Objects.nonNull(bulletin)) {
                        contentId = bulletin.getContentId();
                        message.setId(bulletin.getId());
                        message.setSubject(bulletin.getSubject());
                        message.setContentId(bulletin.getContentId());
                        message.setCategory(NotifyType.SEI_BULLETIN);
                        message.setPriority(bulletin.getPriority());
                    }
                    break;
                case SEI_MESSAGE:

                    break;
                case SEI_REMIND:

                    break;
                default:
                    return OperateResultWithData.operationFailure("不支持的类型!");
            }
            //加载内容
            if (StringUtils.isNotBlank(contentId)) {
                ContentBody body = contentBodyDao.findOne(contentId);
                if (Objects.nonNull(body)) {
                    message.setContent(body.getContent());
                }
            }
            return OperateResultWithData.operationSuccessWithData(message);
        } else {
            return OperateResultWithData.operationFailure("参数不能为空!");
        }
    }

    /**
     * 用户未读数据
     *
     * @return 返回未读数据
     */
    public OperateResultWithData<BaseMessageDto> getFirstUnreadBulletin() {
        SessionUser user = ContextUtil.getSessionUser();
        Bulletin bulletin = bulletinDao.getFirstUnreadBulletin(user.getUserId(), getTargetCodeByUser(user));
        BulletinDto dto = null;
        if (Objects.nonNull(bulletin)) {
            dto = new BulletinDto();
            dto.setId(bulletin.getId());
            // 消息主题
            dto.setSubject(bulletin.getSubject());
            // 优先级
            dto.setPriority(bulletin.getPriority());
            // 生效时间
            dto.setEffectiveDate(bulletin.getEffectiveDate());
            // 失效时间
            dto.setInvalidDate(bulletin.getInvalidDate());
            dto.setContentId(bulletin.getContentId());
        }
        return OperateResultWithData.operationSuccessWithData(dto);
    }

    /**
     * 用户查询通告
     *
     * @param search 查询参数
     * @return 分页查询通告
     */
    public PageResult<BulletinCompose> findBulletinByPage4User(Search search) {
        if (Objects.isNull(search)) {
            search = Search.createSearch();
        }
        SessionUser user = ContextUtil.getSessionUser();
        return bulletinDao.findPage4User(search, user.getUserId(), this.getTargetCodeByUser(user));
    }
}

package com.changhong.sei.notify.manager;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.enums.UserType;
import com.changhong.sei.notify.dto.BaseMessageDto;
import com.changhong.sei.notify.dto.BulletinDto;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.entity.Bulletin;
import com.changhong.sei.notify.entity.BulletinUser;
import com.changhong.sei.notify.entity.ContentBody;
import com.changhong.sei.notify.entity.Remind;
import com.changhong.sei.notify.entity.compose.BulletinCompose;
import com.changhong.sei.notify.service.BulletinService;
import com.changhong.sei.notify.service.ContentBodyService;
import com.changhong.sei.notify.service.GroupService;
import com.changhong.sei.notify.service.RemindService;
import com.changhong.sei.notify.service.cust.BasicIntegration;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private BulletinService bulletinService;
    @Autowired
    private RemindService remindService;
    @Autowired
    private ContentBodyService contentBodyService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private BasicIntegration basicIntegration;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 未读消息数
     *
     * @return 返回未读消息数
     */
    public Long unreadCount() {
        SessionUser user = ContextUtil.getSessionUser();
        String userId = user.getUserId();
        long count = 0L;

        // 未读通告
        Long bulletinCount = bulletinService.getUnreadCount(userId, getTargetCodeByUser(user));
        if (Objects.nonNull(bulletinCount)) {
            count += bulletinCount;
        }

        // 未读消息(站内消息)


        // 未读提醒
        Long remindCount = remindService.countUnRead(user.getTenantCode(), userId);
        if (Objects.nonNull(remindCount)) {
            count += remindCount;
        }

        return count;
    }

    /**
     * 用户未读数据
     *
     * @return 返回未读数据
     */
    public OperateResultWithData<Map<String, List<BaseMessageDto>>> unreadData() {
        SessionUser user = ContextUtil.getSessionUser();
        String userId = user.getUserId();
        BaseMessageDto messageDto;
        Map<String, List<BaseMessageDto>> data = new HashMap<>();

        // 未读通告
        List<Bulletin> bulletins = bulletinService.getUnreadBulletin(userId, this.getTargetCodeByUser(user));
        if (CollectionUtils.isNotEmpty(bulletins)) {
            List<BaseMessageDto> messageDtos = new ArrayList<>();
            for (Bulletin bulletin : bulletins) {
                messageDto = new BaseMessageDto();
                messageDto.setId(bulletin.getId());
                messageDto.setCategory(NotifyType.SEI_BULLETIN);
                messageDto.setSubject(bulletin.getSubject());
                messageDto.setContentId(bulletin.getContentId());
                messageDto.setPriority(bulletin.getPriority());
                messageDtos.add(messageDto);
            }
            data.put(NotifyType.SEI_BULLETIN.name(), messageDtos);
            bulletins.clear();
        }

        // 未读消息


        // 未读提醒
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter(Remind.FIELD_USER_ID, userId));
        search.addFilter(new SearchFilter(Remind.FIELD_READ, Boolean.FALSE));
        List<Remind> reminds = remindService.findByFilters(search);
        if (Objects.nonNull(reminds)) {
            List<BaseMessageDto> messageDtos = new ArrayList<>();
            for (Remind remind : reminds) {
                messageDto = new BaseMessageDto();
                messageDto.setId(remind.getId());
                messageDto.setCategory(NotifyType.SEI_REMIND);
                messageDto.setSubject(remind.getSubject());
                messageDto.setContentId(remind.getContentId());
                messageDto.setPriority(remind.getPriority());
                messageDtos.add(messageDto);
            }
            data.put(NotifyType.SEI_REMIND.name(), messageDtos);
            reminds.clear();
        }

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
            String userId = user.getUserId();
            LocalDateTime date = LocalDateTime.now();
            switch (category) {
                case SEI_BULLETIN:
                    BulletinUser bulletinUser = bulletinService.findByBulletinIdAndUserId(id, userId);
                    if (Objects.isNull(bulletinUser)) {
                        bulletinUser = new BulletinUser();
                    }
                    bulletinUser.setBulletinId(id);
                    bulletinUser.setRead(Boolean.TRUE);
                    bulletinUser.setReadDate(date);
                    bulletinUser.setReadNum(bulletinUser.getReadNum() + 1);
                    bulletinUser.setUserId(userId);
                    bulletinUser.setUserAccount(user.getAccount());
                    bulletinUser.setUserName(user.getUserName());
                    bulletinUser.setUserType(user.getUserType());
                    bulletinService.saveBulletinUser(bulletinUser);
                    break;
                case SEI_MESSAGE:

                    break;
                case SEI_REMIND:
                    Search search = Search.createSearch();
                    search.addFilter(new SearchFilter(Remind.ID, id));
                    search.addFilter(new SearchFilter(Remind.FIELD_USER_ID, userId));
                    Remind remind = remindService.findOneByFilters(search);
                    if (Objects.isNull(remind)) {
                        return OperateResult.operationFailure("消息[" + id + "]不属于用户[" + user + "].");
                    }
                    remind.setRead(Boolean.TRUE);
                    remind.setReadDate(LocalDateTime.now());
                    remind.setReadNum(remind.getReadNum() + 1);
                    remindService.save(remind);
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
                    Bulletin bulletin = bulletinService.findOne(id);
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
                    Remind remind = remindService.findOne(id);
                    if (Objects.nonNull(remind)) {
                        contentId = remind.getContentId();
                        message.setId(remind.getId());
                        message.setSubject(remind.getSubject());
                        message.setContentId(remind.getContentId());
                        message.setCategory(NotifyType.SEI_REMIND);
                        message.setPriority(remind.getPriority());
                    }
                    break;
                default:
                    return OperateResultWithData.operationFailure("不支持的类型!");
            }
            //加载内容
            if (StringUtils.isNotBlank(contentId)) {
                ContentBody body = contentBodyService.findOne(contentId);
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
        Bulletin bulletin = bulletinService.getFirstUnreadBulletin(user.getUserId(), getTargetCodeByUser(user));
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
        return bulletinService.findPage4User(search, user.getUserId(), this.getTargetCodeByUser(user));
    }

    /**
     * 用户查询通告
     *
     * @param search 查询参数
     * @return 分页查询通告
     */
    public ResultData<PageResult<BaseMessageDto>> findMessageByPage(NotifyType notifyType, Search search) {
        if (Objects.isNull(search)) {
            search = Search.createSearch();
        }

        PageResult<BaseMessageDto> resultData;
        SessionUser user = ContextUtil.getSessionUser();
        switch (notifyType) {
            case SEI_BULLETIN:
                try {
                    PageResult<BulletinCompose> pageResult = findBulletinByPage4User(search);
                    resultData = new PageResult<>(pageResult);
                    List<BaseMessageDto> rows = pageResult.getRows().stream().map(obj -> {
                        BaseMessageDto dto = new BulletinDto();
                        modelMapper.map(obj.getBulletin(), dto);

                        dto.setPublishDate(obj.getBulletin().getReleaseDate());
                        dto.setRead(obj.getUser().getRead());
                        return dto;
                    }).collect(Collectors.toList());
                    resultData.setRows(rows);
                } catch (Exception e) {
                    LogUtil.error("用户查询通告异常！", e);
                    // 用户查询通告异常！{0}
                    return ResultData.fail(ContextUtil.getMessage("00018", e.getMessage()));
                }
                break;
            case SEI_REMIND:
                search.addFilter(new SearchFilter(Remind.FIELD_USER_ID, user.getUserId()));
                try {
                    PageResult<Remind> pageResult = remindService.findByPage(search);
                    resultData = new PageResult<>(pageResult);
                    List<BaseMessageDto> rows = pageResult.getRows().stream().map(obj -> {
                        BaseMessageDto dto = new BulletinDto();
                        modelMapper.map(obj, dto);

                        dto.setPublishDate(obj.getRemindDate());
                        dto.setRead(obj.getRead());
                        return dto;
                    }).collect(Collectors.toList());
                    resultData.setRows(rows);
                } catch (Exception e) {
                    LogUtil.error("用户查询提醒异常！", e);
                    // 用户查询提醒异常！{0}
                    return ResultData.fail(ContextUtil.getMessage("00026", e.getMessage()));
                }
                break;
            default:
                resultData = new PageResult<>();
        }

        return ResultData.success(resultData);
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
            ResultData<List<String>> orgCodesResult = basicIntegration.getEmployeeOrgCodes(userId);
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
}

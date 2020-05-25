package com.changhong.sei.notify.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.edm.sdk.DocumentManager;
import com.changhong.sei.enums.UserType;
import com.changhong.sei.notify.dao.MessageDao;
import com.changhong.sei.notify.dao.MessageUserDao;
import com.changhong.sei.notify.dto.MessageDto;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.entity.ContentBody;
import com.changhong.sei.notify.entity.Message;
import com.changhong.sei.notify.entity.MessageUser;
import com.changhong.sei.notify.entity.compose.MessageCompose;
import com.changhong.sei.notify.service.cust.BasicIntegration;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


/**
 * 消息(Message)业务逻辑实现类
 *
 * @author sei
 * @since 2020-05-22 11:04:12
 */
@Service("messageService")
public class MessageService extends BaseEntityService<Message> {
    @Autowired
    private MessageDao dao;
    @Autowired
    private MessageUserDao messageUserDao;

    @Autowired
    private ContentBodyService contentBodyService;
    @Autowired
    private GroupService groupService;
    @Autowired(required = false)
    private DocumentManager documentManager;
    @Autowired
    private BasicIntegration basicIntegration;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    protected BaseEntityDao<Message> getDao() {
        return dao;
    }

    /**
     * 新增修改消息
     *
     * @param message 消息
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<Message> saveMessage(Message message) {
        if (Objects.isNull(message)) {
            // 维护的通告以及内容不能为空！
            return ResultData.fail("00002");
        }
        String content = message.getContent();
        if (Objects.isNull(content)) {
            // 维护的通告以及内容不能为空！
            return ResultData.fail("00002");
        }
        Set<String> docIds = message.getDocIds();

        ContentBody body = new ContentBody(content);
        String id = message.getId();
        if (StringUtils.isBlank(id)) {
            // 保存内容
            contentBodyService.save(body);
            message.setContentId(body.getId());
            // 保存通告
            dao.save(message);
        } else {
            // 编辑
            Message editMessage = dao.findOne(id);
            if (Objects.isNull(editMessage)) {
                // 编辑失败,通告【{0}】不存在!
                return ResultData.fail(ContextUtil.getMessage("00003", id));
            }
            // 删除原内容
            String contentId = message.getContentId();
            if (StringUtils.isNotBlank(contentId)) {
                contentBodyService.delete(contentId);
            }
            // 保存内容
            contentBodyService.save(body);
            message.setContentId(body.getId());
        }
        dao.save(message);

        //绑定附件  注意:这里是通过内容id关联附件
        if (CollectionUtils.isNotEmpty(docIds) && Objects.nonNull(documentManager)) {
            documentManager.bindBusinessDocuments(message.getContentId(), docIds);
        }

        return ResultData.success(message);
    }

    /**
     * 保存并发布消息
     *
     * @param content  消息内容
     * @param docIds   附件id
     * @param messages 消息
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<String> sendMessage(String content, Set<String> docIds, Message... messages) {
        if (Objects.isNull(messages)) {
            // 维护的通告以及内容不能为空！
            return ResultData.fail("00002");
        }
        if (Objects.isNull(content)) {
            // 维护的通告以及内容不能为空！
            return ResultData.fail("00002");
        }

        ContentBody body = new ContentBody(content);
        contentBodyService.save(body);
        String contentId = body.getId();

        LocalDateTime now = LocalDateTime.now();
        SessionUser user = ContextUtil.getSessionUser();
        for (Message message : messages) {
            message.setContentId(contentId);
            message.setPublish(Boolean.TRUE);
            message.setPublishDate(now);
            if (StringUtils.isBlank(message.getPublishUserAccount())) {
                message.setPublishUserAccount(user.getAccount());
            }
            if (StringUtils.isBlank(message.getPublishUserName())) {
                message.setPublishUserName(user.getUserName());
            }
            // 保存通告
            dao.save(message);
        }

        //绑定附件  注意:这里是通过内容id关联附件
        if (CollectionUtils.isNotEmpty(docIds) && Objects.nonNull(documentManager)) {
            documentManager.bindBusinessDocuments(contentId, docIds);
        }

        return ResultData.success("ok");
    }

    /**
     * 发布消息
     *
     * @param ids 要发布的消息id
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<String> publishMessage(Set<String> ids, String publishUserAccount, String publishUserName) {
        if (CollectionUtils.isEmpty(ids)) {
            // 发布通告参数不能为空!
            return ResultData.fail("00004");
        }
        List<Message> messages = dao.findAllById(ids);
        if (!CollectionUtils.isEmpty(messages)) {
            LocalDateTime date = LocalDateTime.now();
            for (Message message : messages) {
                message.setPublish(Boolean.TRUE);
                message.setPublishDate(date);
                message.setPublishUserAccount(publishUserAccount);
                message.setPublishUserName(publishUserName);
            }
            dao.save(messages);
        }
        return ResultData.success("ok");
    }

    /**
     * 撤销发布消息
     *
     * @param ids 撤销发布的消息id
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<String> cancelPublishMessage(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            // 发布通告参数不能为空!
            return ResultData.fail("00004");
        }
        List<Message> messages = dao.findAllById(ids);
        if (!CollectionUtils.isEmpty(messages)) {
            for (Message message : messages) {
                message.setPublish(Boolean.FALSE);
            }
            dao.save(messages);

            // 按通告id删除用户阅读数据
            messageUserDao.deleteByMsgIdIn(ids);
        }
        return ResultData.success("ok");
    }

    /**
     * 发布消息
     *
     * @param ids 要发布的消息id
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultData<String> delMessage(Set<String> ids, String delUserAccount, String delUserName) {
        if (CollectionUtils.isEmpty(ids)) {
            // 发布通告参数不能为空!
            return ResultData.fail("00004");
        }
        List<Message> messages = dao.findAllById(ids);
        if (!CollectionUtils.isEmpty(messages)) {
            LocalDateTime date = LocalDateTime.now();
            for (Message message : messages) {
                message.setDel(Boolean.TRUE);
                message.setDelDate(date);
                message.setDelUserAccount(delUserAccount);
                message.setDelUserName(delUserName);
            }
            dao.save(messages);
        }
        return ResultData.success("ok");
    }

    /**
     * 获取未读消息数
     */
    public Long getUnreadCount(SessionUser user) {
        Set<String> targetValues = getTargetValueByUser(user);

        return getUnreadCount(user.getUserId(), targetValues);
    }

    /**
     * 获取未读消息数
     */
    public Long getUnreadCount(String userId, Set<String> targetValues) {
        return messageUserDao.getUnreadCount(userId, targetValues);
    }

    /**
     * 获取未读消息
     */
    public Map<String, List<MessageDto>> getUnreadMessage(SessionUser user) {
        Set<String> targetValues = getTargetValueByUser(user);

        return getUnreadMessage(user.getUserId(), targetValues);
    }

    /**
     * 获取未读消息
     */
    public Map<String, List<MessageDto>> getUnreadMessage(String userId, Set<String> targetValues) {
        Map<String, List<MessageDto>> listMap = new HashMap<>();
        List<MessageDto> messageList;

        List<Message> messages = messageUserDao.getUnreadMessage(userId, targetValues);
        MessageDto messageDto;
        NotifyType notifyType;
        for (Message message : messages) {
            notifyType = message.getNotifyType();
            messageList = listMap.get(notifyType.name());
            if (Objects.isNull(messageList)) {
                messageList = new ArrayList<>();
            }

            messageDto = modelMapper.map(message, MessageDto.class);

            messageList.add(messageDto);
            listMap.put(notifyType.name(), messageList);
        }

        return listMap;
    }

    /**
     * 获取优先级最高的未读消息
     */
    public Message getFirstUnreadMessage(SessionUser user) {
        Set<String> targetValues = getTargetValueByUser(user);

        return getFirstUnreadMessage(user.getUserId(), targetValues);
    }

    /**
     * 获取优先级最高的未读消息
     */
    public Message getFirstUnreadMessage(String userId, Set<String> targetValues) {
        return messageUserDao.getFirstUnreadMessage(userId, targetValues);
    }

    /**
     * 分页获取用户消息
     */
    public PageResult<MessageCompose> findPage4User(Search search, SessionUser user) {
        Set<String> targetValues = getTargetValueByUser(user);

        return findPage4User(search, user.getUserId(), targetValues);
    }

    /**
     * 分页获取用户消息
     */
    public PageResult<MessageCompose> findPage4User(Search search, String userId, Set<String> targetValues) {
        return messageUserDao.findPage4User(search, userId, targetValues);
    }

    /**
     * 用户阅读消息
     *
     * @param msgId 消息id
     * @return 返回结果
     */
    public OperateResult read(String msgId) {
        if (StringUtils.isNotBlank(msgId)) {
            SessionUser user = ContextUtil.getSessionUser();
            String userId = user.getUserId();
            LocalDateTime date = LocalDateTime.now();
            MessageUser messageUser = messageUserDao.findByMsgIdAndUserId(msgId, userId);
            if (Objects.isNull(messageUser)) {
                messageUser = new MessageUser();
            }
            messageUser.setMsgId(msgId);
            messageUser.setRead(Boolean.TRUE);
            messageUser.setReadDate(date);
            messageUser.setReadNum(messageUser.getReadNum() + 1);
            messageUser.setUserId(userId);
            messageUser.setUserAccount(user.getAccount());
            messageUser.setUserName(user.getUserName());
            messageUser.setUserType(user.getUserType());
            messageUserDao.save(messageUser);
            return OperateResult.operationSuccess();
        } else {
            return OperateResult.operationFailure("参数不能为空!");
        }
    }

    /**
     * 用户查看
     *
     * @param msgId 消息id
     * @return 返回明细
     */
    public ResultData<Message> detail(String msgId) {
        if (StringUtils.isNotBlank(msgId)) {
            Message message = dao.findOne(msgId);
            if (Objects.nonNull(message)) {
                String contentId = message.getContentId();
                //加载内容
                if (StringUtils.isNotBlank(contentId)) {
                    ContentBody body = contentBodyService.findOne(contentId);
                    if (Objects.nonNull(body)) {
                        message.setContent(body.getContent());
                    }
                }
                return ResultData.success(message);
            } else {
                return ResultData.fail(msgId + " - 消息不存在");
            }
        } else {
            return ResultData.fail("参数不能为空!");
        }
    }

    /**
     * 获取用户的权限集合{组织机构、岗位}
     */
    @Cacheable(value = "UserAuthorizedFeaturesCache", key = "'TargetValueByUser:'+#userId")
    public Set<String> getTargetValueByUser(SessionUser user) {
        Set<String> targetValues = Sets.newHashSet();

        String userId = user.getUserId();
        Set<String> groupItem = new HashSet<>();
        if (!user.isAnonymous() && UserType.Employee == user.getUserType()) {
            // 获取用户的组织代码清单
            ResultData<List<String>> orgCodesResult = basicIntegration.getEmployeeOrgCodes(userId);
            if (orgCodesResult.successful() && CollectionUtils.isNotEmpty(orgCodesResult.getData())) {
                groupItem.addAll(orgCodesResult.getData());
            }
//        // todo 没有组织，获取用户岗位上的组织
//        ResultData<List<String>> positionCodesResult = employeeClient.getEmployeePositionCodes(userId);
//        if (positionCodesResult.successful() && CollectionUtils.isNotEmpty(positionCodesResult.getData())) {
//            return positionCodesResult.getData();
//        }
        }

        groupItem.add(user.getAccount());

        // 添加群组
        ResultData<Set<String>> groupCodeResult = groupService.getGroupCodes(groupItem);
        if (groupCodeResult.successful() && CollectionUtils.isNotEmpty(groupCodeResult.getData())) {
            targetValues.addAll(groupCodeResult.getData());
        }

        // 添加用户id,获取个人点对点消息
        targetValues.add(userId);

        return targetValues;
    }
}
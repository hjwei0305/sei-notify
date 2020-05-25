package com.changhong.sei.notify.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.notify.api.MsgApi;
import com.changhong.sei.notify.dto.MessageDto;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.dto.Priority;
import com.changhong.sei.notify.entity.Message;
import com.changhong.sei.notify.entity.compose.MessageCompose;
import com.changhong.sei.notify.service.MessageService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <strong>实现功能:</strong>
 * <p>站内消息管理的服务实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-15 17:16
 */
@RestController
@Api(value = "MsgApi", tags = "站内消息管理API服务")
@RequestMapping(path = "message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MsgController implements MsgApi {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 获取优先级枚举值清单
     *
     * @return 枚举值清单
     */
    @Override
    public ResultData<Map<String, String>> getPriority() {
        return ResultDataUtil.getEnumMap(Priority.class);
    }

    /**
     * 获取消息类别枚举值清单
     *
     * @return 枚举值清单
     */
    @Override
    public ResultData<Map<String, String>> getCategory() {
        return ResultDataUtil.getEnumMap(NotifyType.class);
    }

    /**
     * 获取未读消息数
     *
     * @return 查询结果
     */
    @Override
    public ResultData<Long> unreadCount() {
        Long unread;
        try {
            unread = messageService.getUnreadCount(ContextUtil.getSessionUser());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error("获取未读消息数异常！", e);
            // 获取未读消息数异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00013", e.getMessage()));
        }
        return ResultData.success(unread);
    }

    /**
     * 获取用户未读数据
     *
     * @return 查询结果
     */
    @Override
    public ResultData<Map<String, List<MessageDto>>> unreadData() {
        try {
            Map<String, List<MessageDto>> messageMap = messageService.getUnreadMessage(ContextUtil.getSessionUser());
            return ResultData.success(messageMap);
        } catch (Exception e) {
            LogUtil.error("获取用户未读数据异常！", e);
            // 获取用户未读数据异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00014", e.getMessage()));
        }
    }

    /**
     * 用户阅读
     *
     * @param msgId 消息Id
     * @return 操作结果
     */
    @Override
    public ResultData<String> read(String msgId) {
        OperateResult operateResult;
        try {
            operateResult = messageService.read(msgId);
        } catch (Exception e) {
            LogUtil.error("执行用户阅读异常！", e);
            // 执行用户阅读异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00015", e.getMessage()));
        }
        return ResultDataUtil.convertFromOperateResult(operateResult);
    }

    /**
     * 用户查看
     *
     * @param msgId 消息Id
     * @return 操作结果
     */
    @Override
    public ResultData<MessageDto> detail(String msgId) {
        try {
            ResultData<Message> messageResultData = messageService.detail(msgId);
            if (messageResultData.successful()) {
                MessageDto messageDto = modelMapper.map(messageResultData.getData(), MessageDto.class);
                return ResultData.success(messageDto);
            } else {
                return ResultData.fail(messageResultData.getMessage());
            }
        } catch (Exception e) {
            LogUtil.error("执行用户查看异常！", e);
            // 执行用户查看异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00016", e.getMessage()));
        }
    }

    /**
     * 默认获取优先级高的通告
     *
     * @return 操作结果
     */
    @Override
    public ResultData<MessageDto> getFirstUnreadMessage() {
        try {
            Message message = messageService.getFirstUnreadMessage(ContextUtil.getSessionUser());
            return ResultData.success(modelMapper.map(message, MessageDto.class));
        } catch (Exception e) {
            LogUtil.error("默认获取优先级高的通告异常！", e);
            // 默认获取优先级高的通告异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00017", e.getMessage()));
        }
    }

    /**
     * 用户查询通告
     *
     * @param search 查询参数
     * @return 查询结果
     */
    @Override
    public ResultData<PageResult<MessageDto>> findMessageByPage(Search search) {
        PageResult<MessageCompose> pageResult = messageService.findPage4User(search, ContextUtil.getSessionUser());

        MessageDto dto;
        PageResult<MessageDto> result = new PageResult<>(pageResult);
        for (MessageCompose compose : pageResult.getRows()) {
            dto = modelMapper.map(compose.getMessage(), MessageDto.class);
            if (Objects.nonNull(compose.getUser())) {
                dto.setRead(compose.getUser().getRead());
            }
        }

        return ResultData.success(result);
    }
}

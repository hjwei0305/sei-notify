package com.changhong.sei.notify.manager.remind;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.dto.SendMessage;
import com.changhong.sei.notify.dto.TargetType;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import com.changhong.sei.notify.entity.Message;
import com.changhong.sei.notify.entity.MessageHistory;
import com.changhong.sei.notify.manager.NotifyManager;
import com.changhong.sei.notify.service.MessageHistoryService;
import com.changhong.sei.notify.service.MessageService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-24 23:37
 */
@Component("SEI_REMIND")
public class RemindManager implements NotifyManager {
    private static final Logger LOG = LoggerFactory.getLogger(RemindManager.class);
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageHistoryService historyService;

    /**
     * 发送消息通知
     *
     * @param message 发送的消息
     */
    @Override
    public ResultData<String> send(SendMessage message) {
        LOG.info("收到消息[{}],开始执行发送站内提醒消息", message);
        Message messageObj;
        //收件人清单
        List<UserNotifyInfo> receivers = message.getReceivers();
        if (CollectionUtils.isNotEmpty(receivers)) {
            boolean success = Boolean.TRUE;
            String log = "success";
            String content = message.getContent();
            MessageHistory history;
            List<MessageHistory> histories = new ArrayList<>();
            Set<String> docIds = null;
            try {
                docIds = message.getDocIds();
                int i = 0;
                UserNotifyInfo sender = message.getSender();
                Message[] messages = new Message[receivers.size()];
                for (UserNotifyInfo receiver : receivers) {
                    messageObj = new Message();
                    messageObj.setCategory(NotifyType.SEI_REMIND);
                    messageObj.setSubject(message.getSubject());
                    messageObj.setTargetType(TargetType.PERSONAL);
                    messageObj.setTargetValue(receiver.getUserId());
                    messageObj.setTargetName(receiver.getUserName());

                    if (Objects.nonNull(sender)) {
                        messageObj.setPublishUserAccount(sender.getUserAccount());
                        messageObj.setPublishUserName(sender.getUserName());
                    }
                    messages[i++] = messageObj;

                    history = new MessageHistory();
                    history.setCategory(NotifyType.SEI_REMIND);
                    history.setSubject(message.getSubject());
                    history.setTargetType(TargetType.PERSONAL);
                    history.setTargetValue(receiver.getUserId());
                    history.setTargetName(receiver.getUserName());
                    histories.add(history);
                }

                ResultData<String> result = messageService.sendMessage(message.getContent(), docIds, messages);
                if (result.failed()) {
                    success = Boolean.FALSE;
                    log = result.getMessage();
                }
                LOG.info("站内提醒消息发送结果: {}", result.getMessage());
                return result;
            } catch (Exception e) {
                success = Boolean.FALSE;
                log = "发送提醒消息异常" + e.getMessage();
                LOG.error("发送提醒消息异常", e);
                return ResultData.fail("发送提醒消息异常:" + e.getMessage());
            } finally {
                try {
                    historyService.recordHistory(histories, content, success, log, docIds);
                } catch (Exception e) {
                    LogUtil.error("记录消息历史异常", e);
                }
            }
        } else {
            return ResultData.fail("消息接收人不能为空.");
        }
    }
}

package com.changhong.sei.notify.manager.remind;

import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.dto.SendMessage;
import com.changhong.sei.notify.dto.TargetType;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import com.changhong.sei.notify.entity.Message;
import com.changhong.sei.notify.manager.NotifyManager;
import com.changhong.sei.notify.service.MessageService;
import com.changhong.sei.util.thread.ThreadLocalHolder;
import com.changhong.sei.utils.MockUserHelper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

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

//    @Value("${sei.mock.user.tenant-code}")
//    private String tenant;
//    @Value("${sei.mock.user.account}")
//    private String account;

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
            try {
//                ThreadLocalHolder.begin();

//                SessionUser user = MockUserHelper.mockUser(tenant, account);
//                LOG.info("当前模拟会话用户: {}", user);

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
                }

                ResultData<String> result = messageService.sendMessage(message.getContent(), message.getDocIds(), messages);
                LOG.info("站内提醒消息发送结果: {}", result.getMessage());
                return result;
            } catch (Exception e) {
                LOG.error("发送提醒消息异常", e);
                return ResultData.fail("发送提醒消息异常:" + e.getMessage());
//            } finally {
//                ThreadLocalHolder.end();
            }
        } else {
            return ResultData.fail("消息接收人不能为空.");
        }
    }
}

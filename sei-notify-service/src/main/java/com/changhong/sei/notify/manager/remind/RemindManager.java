package com.changhong.sei.notify.manager.remind;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.SendMessage;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import com.changhong.sei.notify.entity.Remind;
import com.changhong.sei.notify.manager.NotifyManager;
import com.changhong.sei.notify.service.RemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-24 23:37
 */
@Component("SEI_REMIND")
public class RemindManager implements NotifyManager {
    @Autowired
    private RemindService remindService;

    /**
     * 发送消息通知
     *
     * @param message 发送的消息
     */
    @Override
    public ResultData<String> send(SendMessage message) {
        String subject = message.getSubject();
        List<UserNotifyInfo> userList = message.getReceivers();
        Remind[] reminds = new Remind[userList.size()];
        Remind remind;
        int i = 0;
        for (UserNotifyInfo user : userList) {
            remind = new Remind();
            remind.setSubject(subject);
            remind.setUserId(user.getUserId());
            remind.setUserAccount(user.getUserAccount());
            remind.setUserName(user.getUserName());

            reminds[i++] = remind;
        }

        ResultData<String> result = remindService.sendRemind(message.getContent(), message.getDocIds(), reminds);
        return result;
    }
}

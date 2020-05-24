package com.changhong.sei.notify.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.api.RemindApi;
import com.changhong.sei.notify.dto.Priority;
import com.changhong.sei.notify.dto.RemindRequest;
import com.changhong.sei.notify.dto.RemindUser;
import com.changhong.sei.notify.entity.Remind;
import com.changhong.sei.notify.service.RemindService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 提醒信息(Remind)控制类
 *
 * @author sei
 * @since 2020-05-22 18:09:16
 */
@RestController
@Api(value = "RemindApi", tags = "提醒信息服务")
@RequestMapping(path = "remind", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RemindController implements RemindApi {
    /**
     * 提醒信息服务对象
     */
    @Autowired
    private RemindService service;

    /**
     * 发送提醒
     *
     * @param remindRequest 发起提醒请求
     */
    @Override
    public ResultData<String> sendRemind(RemindRequest remindRequest) {
        String subject = remindRequest.getSubject();
        Priority priority = remindRequest.getPriority();
        String origin = remindRequest.getOrigin();
        Set<RemindUser> userSet = remindRequest.getRemindUsers();
        Remind[] reminds = new Remind[userSet.size()];
        Remind remind;
        int i = 0;
        for (RemindUser user : userSet) {
            remind = new Remind();
            remind.setSubject(subject);
            remind.setPriority(priority);
            remind.setOrigin(origin);
            remind.setUserId(user.getUserId());
            remind.setUserAccount(user.getUserAccount());
            remind.setUserName(user.getUserName());
            remind.setUserType(user.getUserType());

            reminds[i++] = remind;
        }

        return service.sendRemind(remindRequest.getContent(), remindRequest.getDocIds(), reminds);
    }
}
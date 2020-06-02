package com.changhong.sei.notify.manager.miniapp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.dto.SendMessage;
import com.changhong.sei.notify.dto.TargetType;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import com.changhong.sei.notify.entity.Message;
import com.changhong.sei.notify.manager.NotifyManager;
import com.changhong.sei.notify.service.MessageService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-24 23:37
 */
@Component("MiniApp")
public class MiniAppManager implements NotifyManager {

    private static final Logger LOG = LoggerFactory.getLogger(MiniAppManager.class);

    @Autowired(required = false)
    private WxMaService wxService;

    /**
     * 发送消息通知
     *
     * @param message 发送的消息
     */
    @Override
    public ResultData<String> send(SendMessage message) {
        LOG.info("收到消息[{}],开始执行发送微信小程序提醒消息", message);
        if (Objects.isNull(wxService)){
            LOG.error("后台未配置微信小程序消息发送功能");
            return ResultData.fail("后台未配置微信小程序消息发送功能");
        }
        //收件人清单
        List<UserNotifyInfo> receivers = message.getReceivers();
        if (CollectionUtils.isNotEmpty(receivers)) {
            try {
                //获得内容
                WxMaSubscribeMessage wxMaSubscribeMessage = JsonUtils.fromJson(message.getContent(),WxMaSubscribeMessage.class);
                for (UserNotifyInfo receiver : receivers) {
                    for (String openId : receiver.getMiniProgramOpenId()){
                        if (StringUtils.isNotEmpty(openId)){
                            wxMaSubscribeMessage.setToUser(openId);
                            wxService.getMsgService().sendSubscribeMsg(wxMaSubscribeMessage);
                        }
                    }
                }
                LOG.info("站内提醒消息发送成功");
                return ResultData.success("站内提醒消息发送成功");
            } catch (Exception e) {
                LOG.error("发送提醒消息异常", e);
                return ResultData.fail("发送提醒消息异常:" + e.getMessage());
            }
        } else {
            return ResultData.fail("消息接收人不能为空.");
        }
    }
}

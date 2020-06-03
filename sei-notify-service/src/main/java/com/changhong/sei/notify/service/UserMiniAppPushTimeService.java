package com.changhong.sei.notify.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.notify.dao.ContentTemplateDao;
import com.changhong.sei.notify.dao.UserMiniAppPushTimeDao;
import com.changhong.sei.notify.dto.UserMiniAppPushTimeDto;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import com.changhong.sei.notify.entity.ContentTemplate;
import com.changhong.sei.notify.entity.UserMiniAppPushTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * <strong>实现功能:</strong>
 * <p>小程序推送次数逻辑实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 15:28
 */
@Service
public class UserMiniAppPushTimeService extends BaseEntityService<UserMiniAppPushTime> {
    @Autowired
    private UserMiniAppPushTimeDao dao;

    @Override
    protected BaseEntityDao<UserMiniAppPushTime> getDao() {
        return dao;
    }

    /**
     * 推送次数加一
     * @param openId 小程序openId
     * @return 结果
     */
    public ResultData<String> plus(String openId){
        UserMiniAppPushTime userMiniAppPushTime =  dao.findByMiniProgramOpenId(openId);
        if (Objects.isNull(userMiniAppPushTime)){
            SessionUser user = ContextUtil.getSessionUser();
            userMiniAppPushTime = new UserMiniAppPushTime();
            userMiniAppPushTime.setUserId(user.getUserId());
            userMiniAppPushTime.setUserAccount(user.getAccount());
            userMiniAppPushTime.setUserName(user.getUserName());
            userMiniAppPushTime.setMiniProgramOpenId(openId);
            userMiniAppPushTime.setPushTime(1);
        }else {
            userMiniAppPushTime.setPushTime(userMiniAppPushTime.getPushTime()+1);
        }
        dao.save(userMiniAppPushTime);
        return ResultData.success("ok");
    }

    /**
     * 推送次数减一
     * @param userNotifyInfo 接收人
     * @param openId 小程序openId
     * @return 结果
     */
    public ResultData<String> subtract(UserNotifyInfo userNotifyInfo,String openId){
        UserMiniAppPushTime userMiniAppPushTime =  dao.findByMiniProgramOpenId(openId);
        if (Objects.isNull(userMiniAppPushTime)){
            userMiniAppPushTime = new UserMiniAppPushTime();
            userMiniAppPushTime.setUserId(userNotifyInfo.getUserId());
            userMiniAppPushTime.setUserAccount(userNotifyInfo.getUserAccount());
            userMiniAppPushTime.setUserName(userNotifyInfo.getUserName());
            userMiniAppPushTime.setMiniProgramOpenId(openId);
            userMiniAppPushTime.setPushTime(0);
        }else {
            userMiniAppPushTime.setPushTime(userMiniAppPushTime.getPushTime()-1);
        }
        dao.save(userMiniAppPushTime);
        return ResultData.success("ok");
    }

    /**
     * 推送次数减一
     * @param openId 小程序openId
     * @return 结果
     */
    public ResultData<String> subtract(String openId){
        UserMiniAppPushTime userMiniAppPushTime =  dao.findByMiniProgramOpenId(openId);
        if (Objects.isNull(userMiniAppPushTime)){
            SessionUser user = ContextUtil.getSessionUser();
            userMiniAppPushTime = new UserMiniAppPushTime();
            userMiniAppPushTime.setUserId(user.getUserId());
            userMiniAppPushTime.setUserAccount(user.getAccount());
            userMiniAppPushTime.setUserName(user.getUserName());
            userMiniAppPushTime.setMiniProgramOpenId(openId);
            userMiniAppPushTime.setPushTime(0);
        }else {
            userMiniAppPushTime.setPushTime(userMiniAppPushTime.getPushTime()-1);
        }
        dao.save(userMiniAppPushTime);
        return ResultData.success("ok");
    }

    /**
     * 获取剩余推送次数
     * @param openId 小程序openId
     * @return 结果
     */
    public ResultData<UserMiniAppPushTime> findByOpenId(String openId){
        UserMiniAppPushTime userMiniAppPushTime =  dao.findByMiniProgramOpenId(openId);
        if (Objects.nonNull(userMiniAppPushTime)){
            return ResultData.success(userMiniAppPushTime);
        }else {
            return ResultData.fail("指定数据不存在");
        }
    }

}

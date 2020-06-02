package com.changhong.sei.notify.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <strong>实现功能:</strong>
 * <p>小程序推送次数DTO</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 15:56
 */
@ApiModel(description = "小程序推送次数DTO")
public class UserMiniAppPushTimeDto extends BaseEntityDto implements Serializable {
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 用户
     */
    private String userAccount;
    /**
     * 用户
     */
    private String userName;

    /**
     * 微信小程序openId
     */
    private String miniProgramOpenId;

    /**
     * 剩余推送次数
     */
    private Integer pushTime = 0;
}

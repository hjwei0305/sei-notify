package com.changhong.sei.notify.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-01-14 21:04
 */
@ApiModel(description = "账户信息")
public class AccountResponse extends BaseEntityDto {
    private static final long serialVersionUID = 2974541194405245535L;
    /**
     * 租户代码
     */
    @ApiModelProperty(notes = "租户代码", required = true)
    private String tenantCode;
    /**
     * 用户id
     */
    @ApiModelProperty(notes = "用户id", required = true)
    private String userId;
    /**
     * 账号
     */
    @ApiModelProperty(notes = "账号", required = true)
    private String account;
    /**
     * 名称
     */
    @ApiModelProperty(notes = "名称", required = true)
    private String name;
    /**
     * 账户类型
     */
    @ApiModelProperty(notes = "账户类型", required = true)
    private String accountType;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}

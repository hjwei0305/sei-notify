package com.changhong.sei.notify.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 实现功能: 企业用户DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 15:35
 */
@ApiModel(description = "企业用户DTO")
public class EmployeeDto implements Serializable {
    private static final long serialVersionUID = -6798573793244735798L;

    /**
     * userId
     */
    protected String id;
    /**
     * 员工编号
     */
    @NotBlank
    @Size(max = 10)
    @ApiModelProperty(value = "员工编号(max = 10)", required = true)
    private String code;

    /**
     * 用户名称
     */
    @NotBlank
    @ApiModelProperty(value = "用户名称")
    private String userName;

    /**
     * 组织机构Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "组织机构Id")
    private String organizationId;

    /**
     * 组织机构代码
     */
    @ApiModelProperty(value = "组织机构代码")
    private String organizationCode;

    /**
     * 组织机构名称
     */
    @ApiModelProperty(value = "组织机构名称")
    private String organizationName;

    /**
     * 组织机构代码路径
     */
    @ApiModelProperty(value = "组织机构代码路径")
    private String organizationCodePath;

    /**
     * 组织机构名称路径
     */
    @ApiModelProperty(value = "组织机构名称路径")
    private String organizationNamePath;

    /**
     * 是否冻结
     */
    @NotNull
    @ApiModelProperty(value = "是否冻结", required = true)
    private Boolean frozen = Boolean.FALSE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationCodePath() {
        return organizationCodePath;
    }

    public void setOrganizationCodePath(String organizationCodePath) {
        this.organizationCodePath = organizationCodePath;
    }

    public String getOrganizationNamePath() {
        return organizationNamePath;
    }

    public void setOrganizationNamePath(String organizationNamePath) {
        this.organizationNamePath = organizationNamePath;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }
}

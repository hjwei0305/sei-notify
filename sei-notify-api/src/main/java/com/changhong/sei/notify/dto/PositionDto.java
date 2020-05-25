package com.changhong.sei.notify.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 实现功能: 岗位DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-27 15:54
 */
@ApiModel(description = "岗位DTO")
public class PositionDto extends BaseEntityDto {
    private static final long serialVersionUID = -401899269508557905L;
    /**
     * 代码
     */
    @ApiModelProperty(value = "代码(系统给号)")
    private String code;
    /**
     * 名称
     */
    @NotBlank
    @Size(max = 50)
    @ApiModelProperty(value = "名称(max = 50)", required = true)
    private String name;

    /**
     * 租户代码
     */
    @ApiModelProperty(value = "租户代码")
    private String tenantCode;

    /**
     * 组织机构Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "组织机构Id(max = 36)", required = true)
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
     * 组织机构的名称路径
     */
    @ApiModelProperty(value = "组织机构的名称路径")
    private String organizationNamePath;

    /**
     * 岗位类别Id
     */
    @NotBlank
    @Size(max = 36)
    @ApiModelProperty(value = "岗位类别Id(max = 36)", required = true)
    private String positionCategoryId;

    /**
     * 岗位类别代码
     */
    @ApiModelProperty(value = "岗位类别代码")
    private String positionCategoryCode;

    /**
     * 岗位类别名称
     */
    @ApiModelProperty(value = "岗位类别名称")
    private String positionCategoryName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
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

    public String getOrganizationNamePath() {
        return organizationNamePath;
    }

    public void setOrganizationNamePath(String organizationNamePath) {
        this.organizationNamePath = organizationNamePath;
    }

    public String getPositionCategoryId() {
        return positionCategoryId;
    }

    public void setPositionCategoryId(String positionCategoryId) {
        this.positionCategoryId = positionCategoryId;
    }

    public String getPositionCategoryCode() {
        return positionCategoryCode;
    }

    public void setPositionCategoryCode(String positionCategoryCode) {
        this.positionCategoryCode = positionCategoryCode;
    }

    public String getPositionCategoryName() {
        return positionCategoryName;
    }

    public void setPositionCategoryName(String positionCategoryName) {
        this.positionCategoryName = positionCategoryName;
    }
}

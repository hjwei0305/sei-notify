package com.changhong.sei.notify.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.auth.IDataAuthTreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 实现功能: 组织机构DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-20 16:17
 */
@ApiModel(description = "组织机构DTO")
public class OrganizationDto extends BaseEntityDto implements IDataAuthTreeEntity {
    private static final long serialVersionUID = 1238334249742703970L;
    /**
     * 组织机构代码
     */
    @Size(max = 10)
    @ApiModelProperty(value = "组织机构代码(系统给号)")
    private String code;

    /**
     * 组织机构名称
     */
    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(value = "组织机构名称(max = 100)", required = true)
    private String name;

    /**
     * 简称
     */
    @ApiModelProperty(value = "简称(max = 100)")
    private String shortName;

    /**
     * 层级
     */
    @NotNull
    @Min(0)
    @ApiModelProperty(value = "层级")
    private Integer nodeLevel = 0;

    /**
     * 代码路径
     */
    @Size(max = 100)
    @ApiModelProperty(value = "代码路径")
    private String codePath;

    /**
     * 名称路径
     */
    @Size(max = 1000)
    @ApiModelProperty(value = "名称路径")
    private String namePath;

    /**
     * 父节点Id
     */
    @Size(max = 36)
    @ApiModelProperty(value = "父节点Id")
    private String parentId;

    /**
     * 租户代码
     */
    @ApiModelProperty(value = "租户代码")
    private String tenantCode;

    /**
     * 排序
     */
    @NotNull
    @Min(0)
    @ApiModelProperty(value = "排序")
    private Integer rank = 0;

    /**
     * 是否冻结
     */
    @NotNull
    @ApiModelProperty(value = "是否冻结", required = true)
    private Boolean frozen = Boolean.FALSE;

    private List<OrganizationDto> children;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public Integer getNodeLevel() {
        return nodeLevel;
    }

    @Override
    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    @Override
    public String getCodePath() {
        return codePath;
    }

    @Override
    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }

    @Override
    public String getNamePath() {
        return namePath;
    }

    @Override
    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public Integer getRank() {
        return rank;
    }

    @Override
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String getTenantCode() {
        return tenantCode;
    }

    @Override
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public List<OrganizationDto> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationDto> children) {
        this.children = children;
    }
}

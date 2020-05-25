package com.changhong.sei.notify.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 群组(Group)DTO类
 *
 * @author sei
 * @since 2020-05-22 11:04:28
 */
@ApiModel(description = "群组DTO")
public class GroupDto extends BaseEntityDto {
    private static final long serialVersionUID = 128901001160602539L;
    /**
     * 类别
     */
    @JsonSerialize(using = EnumJsonSerializer.class)
    private GroupCategory category;
    /**
     * 代码
     */
    @ApiModelProperty(value = "代码")
    private String code;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 冻结
     */
    @ApiModelProperty(value = "冻结")
    private Boolean frozen;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer rank;

    public GroupCategory getCategory() {
        return category;
    }

    public void setCategory(GroupCategory category) {
        this.category = category;
    }

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

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
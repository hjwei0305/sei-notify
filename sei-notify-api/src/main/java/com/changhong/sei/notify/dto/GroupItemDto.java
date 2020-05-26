package com.changhong.sei.notify.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 群组(GroupUser)DTO类
 *
 * @author sei
 * @since 2020-05-22 11:04:28
 */
@ApiModel(description = "群组DTO")
public class GroupItemDto extends BaseEntityDto {
    private static final long serialVersionUID = 755755634778478503L;
    /**
     * 群组id
     */
    @NotBlank
    @ApiModelProperty(value = "群组id")
    private String groupId;
    /**
     * 类别
     */
    @JsonSerialize(using = EnumJsonSerializer.class)
    private GroupCategory category;
    /**
     * 群组项id
     */
    private String itemId;
    /**
     * 群组项代码
     */
    private String itemCode;
    /**
     * 群组项名称
     */
    private String itemName;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public GroupCategory getCategory() {
        return category;
    }

    public void setCategory(GroupCategory category) {
        this.category = category;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
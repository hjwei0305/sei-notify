package com.changhong.sei.notify.entity;

import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ITenant;
import com.changhong.sei.notify.dto.GroupCategory;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 群组(GroupUser)实体类
 *
 * @author sei
 * @since 2020-05-22 11:04:12
 */
@Entity
@Table(name = "group_item")
@DynamicInsert
@DynamicUpdate
public class GroupItem extends BaseAuditableEntity implements /*ITenant,*/ Serializable {
    private static final long serialVersionUID = -18017322204395690L;
    public static final String FIELD_GROUP_ID = "groupId";
    public static final String FIELD_CATEGORY = "category";
    /**
     * 群组id
     */
    @Column(name = "group_id")
    private String groupId;
    /**
     * 类别
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "category_")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private GroupCategory category;
    /**
     * 群组项id
     */
    @Column(name = "item_id")
    private String itemId;
    /**
     * 群组项代码
     */
    @Column(name = "item_code")
    private String itemCode;
    /**
     * 群组项名称
     */
    @Column(name = "item_name")
    private String itemName;
    /**
     * 租户代码
     */
    @Column(name = "tenant_code")
    private String tenantCode;

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

//    @Override
    public String getTenantCode() {
        return tenantCode;
    }

//    @Override
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}
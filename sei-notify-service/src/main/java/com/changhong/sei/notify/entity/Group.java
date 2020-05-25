package com.changhong.sei.notify.entity;

import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ICodeUnique;
import com.changhong.sei.core.entity.ITenant;
import com.changhong.sei.notify.dto.GroupCategory;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 群组(Group)实体类
 *
 * @author sei
 * @since 2020-05-22 11:04:12
 */
@Entity
@Table(name = "group_info")
@DynamicInsert
@DynamicUpdate
public class Group extends BaseAuditableEntity implements /*ITenant,*/ ICodeUnique, Serializable {
    private static final long serialVersionUID = 182142067251684618L;
    /**
     * 类别
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "category_")
    @JsonSerialize(using = EnumJsonSerializer.class)
    private GroupCategory category;
    /**
     * 代码
     */
    @Column(name = "code")
    private String code;
    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 租户代码
     */
    @Column(name = "tenant_code")
    private String tenantCode;
    /**
     * 冻结
     */
    @Column(name = "frozen")
    private Boolean frozen = Boolean.FALSE;
    /**
     * 排序
     */
    @Column(name = "rank")
    private Integer rank = 0;

    public GroupCategory getCategory() {
        return category;
    }

    public void setCategory(GroupCategory category) {
        this.category = category;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @Override
    public String getTenantCode() {
        return tenantCode;
    }

//    @Override
    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
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
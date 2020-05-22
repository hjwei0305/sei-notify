package com.changhong.sei.notify.entity;

import com.changhong.sei.core.entity.BaseAuditableEntity;
import com.changhong.sei.core.entity.ITenant;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 群组(GroupUser)实体类
 *
 * @author sei
 * @since 2020-05-22 11:04:12
 */
@Entity
@Table(name = "group_user")
@DynamicInsert
@DynamicUpdate
public class GroupUser extends BaseAuditableEntity implements ITenant, Serializable {
    private static final long serialVersionUID = -18017322204395690L;
    public static final String FIELD_GROUP_ID = "groupId";
    /**
     * 群组id
     */
    @Column(name = "group_id")
    private String groupId;
    /**
     * 用户账号
     */
    @Column(name = "user_account")
    private String userAccount;
    /**
     * 用户名称
     */
    @Column(name = "user_name")
    private String userName;
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

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

}
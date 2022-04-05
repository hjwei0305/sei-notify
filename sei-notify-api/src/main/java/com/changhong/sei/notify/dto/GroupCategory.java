package com.changhong.sei.notify.dto;

import com.changhong.sei.annotation.Remark;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-25 10:31
 */
public enum GroupCategory {
    /**
     * 用户
     */
    @Remark("用户")
    USER,
    /**
     * 组织机构
     */
    @Remark("组织机构")
    ORG,
    /**
     * 岗位
     */
    @Remark("岗位")
    POS,
    /**
     * 功能角色
     */
    @Remark("功能角色")
    ROLE
}

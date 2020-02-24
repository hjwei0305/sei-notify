package com.changhong.sei.notify.dto;


import com.changhong.sei.annotation.Remark;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-19 17:16
 */
public enum Priority {

    /**
     * 紧急
     */
    @Remark("紧急")
    Urgent,
    /**
     * 高
     */
    @Remark("高")
    High,
    /**
     * 一般
     */
    @Remark("一般")
    General
}

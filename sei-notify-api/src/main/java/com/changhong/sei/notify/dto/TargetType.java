package com.changhong.sei.notify.dto;

import com.changhong.sei.annotation.Remark;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-19 17:27
 */
public enum TargetType {

    /**
     * 系统
     */
    @Remark("系统")
    SYSTEM,

    /**
     * 群组
     */
    @Remark("群组")
    GROUP,

    /**
     * 个人
     */
    @Remark("个人")
    PERSONAL
}

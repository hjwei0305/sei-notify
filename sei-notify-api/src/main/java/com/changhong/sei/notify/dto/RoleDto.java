package com.changhong.sei.notify.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2022-04-02 10:18
 */
public class RoleDto extends BaseEntityDto {
    private static final long serialVersionUID = -1832921673237211162L;
    /**
     * 代码
     */
    @ApiModelProperty(value = "代码(max = 50)")
    private String code;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称(max = 50)")
    private String name;

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
}

package com.changhong.sei.notify.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

/**
 * 实现功能: 获取内容的参数
 *
 * @author 王锦光 wangjg
 * @version 2020-03-06 9:04
 */
@ApiModel("获取内容的参数")
public class ContentParams implements Serializable {
    /**
     * 内容模板代码
     */
    @NotBlank
    @ApiModelProperty(value = "内容模板代码", required = true)
    private String templateCode;

    /**
     * 模板中需要的参数值
     */
    @ApiModelProperty(value = "模板中需要的参数值")
    private Map<String, Object> params;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}

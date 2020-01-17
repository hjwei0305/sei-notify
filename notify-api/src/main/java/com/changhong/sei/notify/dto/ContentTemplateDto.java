package com.changhong.sei.notify.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <strong>实现功能:</strong>
 * <p>内容模板DTO</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 15:56
 */
@ApiModel(description = "内容模板DTO")
public class ContentTemplateDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -4354516375966548357L;
    /**
     * 代码
     */
    @ApiModelProperty(notes = "代码")
    @NotBlank(message = "内容模板代码不能为空")
    @Size(max = 50)
    private String code;
    /**
     * 名称
     */
    @ApiModelProperty(notes = "名称")
    @NotBlank(message = "内容模板名称不能为空")
    @Size(max = 50)
    private String name;
    /**
     * 内容模板
     */
    @ApiModelProperty(notes = "内容模板")
    @NotBlank
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

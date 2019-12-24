package com.changhong.sei.notify.dto;

import java.io.Serializable;

/**
 * <strong>实现功能:</strong>
 * <p>内容模板DTO</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 15:56
 */
public class ContentTemplateDto implements Serializable {
    /**
     * Id标识
     */
    private String id;
    /**
     * 代码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 内容模板
     */
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

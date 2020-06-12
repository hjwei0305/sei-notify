package com.changhong.sei.notify.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:xiaogang.su@changhong.com">粟小刚</a>
 * @description 实现功能: 微信小程序模板
 * @date 2020/06/12 9:34
 */
public class MiniAppTemplate implements Serializable {

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 跳转url
     */
    private String page;

    /**
     * 标题
     */
    private String title;

    /**
     * 参数列表
     */
    private List<MiniAppTemplateParam> data;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<MiniAppTemplateParam> getData() {
        return data;
    }

    public void setData(List<MiniAppTemplateParam> data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

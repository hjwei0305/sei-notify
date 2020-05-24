package com.changhong.sei.notify.dto;

import com.changhong.sei.core.dto.BaseEntityDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 提醒信息(Remind)DTO类
 *
 * @author sei
 * @since 2020-05-22 18:09:43
 */
@ApiModel(description = "提醒信息请求")
public class RemindRequest implements Serializable {
    private static final long serialVersionUID = 390597983994017121L;
    /**
     * 提醒主题
     */
    @NotBlank
    @ApiModelProperty(value = "提醒主题")
    private String subject;
    /**
     * 内容id
     */
    @NotBlank
    @ApiModelProperty(value = "内容")
    private String content;
    /**
     * 优先级
     */
    @ApiModelProperty(value = "优先级")
    private Priority priority = Priority.General;
    /**
     * 来源
     */
    @ApiModelProperty(value = "来源")
    private String origin;
    /**
     * 用户
     */
    @NotNull
    @ApiModelProperty(value = "用户")
    private Set<RemindUser> remindUsers;
    /**
     * 附件id
     */
    @ApiModelProperty(value = "附件id")
    private Set<String> docIds;

    public RemindRequest() {
    }

    public RemindRequest(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public RemindRequest setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContent() {
        return content;
    }

    public RemindRequest setContent(String content) {
        this.content = content;
        return this;
    }

    public Priority getPriority() {
        return priority;
    }

    public RemindRequest setPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public RemindRequest setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public Set<RemindUser> getRemindUsers() {
        return remindUsers;
    }

    public RemindRequest setRemindUsers(Set<RemindUser> remindUsers) {
        this.remindUsers = remindUsers;
        return this;
    }

    public Set<String> getDocIds() {
        return docIds;
    }

    public RemindRequest setDocIds(Set<String> docIds) {
        this.docIds = docIds;
        return this;
    }

    public Set<RemindUser> addRemindUser(RemindUser user) {
        if (Objects.isNull(remindUsers)) {
            remindUsers = new HashSet<>();
        }
        if (Objects.nonNull(user)) {
            remindUsers.add(user);
        }
        return remindUsers;
    }

    public Set<RemindUser> addRemindUsers(Collection<RemindUser> users) {
        if (Objects.isNull(remindUsers)) {
            remindUsers = new HashSet<>();
        }
        if (Objects.nonNull(users)) {
            remindUsers.addAll(users);
        }
        return remindUsers;
    }
}
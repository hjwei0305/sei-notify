package com.changhong.sei.notify.dao;

import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.notify.entity.Bulletin;
import com.changhong.sei.notify.entity.Message;
import com.changhong.sei.notify.entity.compose.MessageCompose;

import java.util.List;
import java.util.Set;

/**
 * 实现功能：消息通告数据访问扩展接口
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-26 14:07
 */
public interface MessageUserExtDao {

    /**
     * 获取未读消息数
     */
    Long getUnreadCount(String userId, Set<String> targetCodes);

    /**
     * 获取未读消息
     */
    List<Message> getUnreadMessage(String userId, Set<String> targetCodes);

    /**
     * 获取优先级最高的未读消息
     */
    Message getFirstUnreadMessage(String userId, Set<String> targetCodes);

    /**
     * 分页获取用户消息
     */
    PageResult<MessageCompose> findPage4User(Search search, String userId, Set<String> targetCodes);
}

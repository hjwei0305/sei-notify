package com.changhong.sei.notify.dao;

import com.changhong.sei.notify.entity.MessageHistory;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.stereotype.Repository;

/**
 * 消息历史(MessageHistory)数据库访问类
 *
 * @author sei
 * @since 2020-06-11 14:36:17
 */
@Repository
public interface MessageHistoryDao extends BaseEntityDao<MessageHistory> {

}
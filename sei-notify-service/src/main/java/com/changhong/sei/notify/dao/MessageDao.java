package com.changhong.sei.notify.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.notify.entity.Group;
import com.changhong.sei.notify.entity.Message;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息(Message)数据库访问类
 *
 * @author sei
 * @since 2020-05-22 11:04:12
 */
@Repository
public interface MessageDao extends BaseEntityDao<Message> {

}
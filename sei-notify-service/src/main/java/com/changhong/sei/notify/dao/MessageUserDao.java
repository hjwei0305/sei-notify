package com.changhong.sei.notify.dao;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.notify.entity.MessageUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-20 14:33
 */
@Repository
public interface MessageUserDao extends BaseEntityDao<MessageUser>, MessageUserExtDao {
    /**
     * 按消息id删除用户阅读数据
     * 通告撤销时使用
     *
     * @param msgIds 消息id
     */
    @Modifying
    void deleteByMsgIdIn(Collection<String> msgIds);

    MessageUser findByMsgIdAndUserId(String msgId, String userId);

    List<MessageUser> findByMsgIdInAndUserId(Set<String> msgIds, String userId);
}

package com.changhong.sei.notify.dao;

import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.notify.entity.Bulletin;
import com.changhong.sei.notify.entity.compose.BulletinCompose;

import java.util.List;

/**
 * 实现功能：消息通告数据访问扩展接口
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-26 14:07
 */
public interface BulletinExtDao {

    Long getUnreadCount(String userId, List<String> orgCodes);

    List<Bulletin> getUnreadBulletin(String userId, List<String> orgCodes);

    Bulletin getFirstUnreadBulletin(String userId, List<String> orgCodes);

    PageResult<BulletinCompose> findPage4User(Search search, String userId, List<String> orgCodes);
}

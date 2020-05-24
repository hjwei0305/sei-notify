package com.changhong.sei.notify.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.edm.sdk.DocumentManager;
import com.changhong.sei.notify.dao.ContentBodyDao;
import com.changhong.sei.notify.dao.RemindDao;
import com.changhong.sei.notify.entity.ContentBody;
import com.changhong.sei.notify.entity.Remind;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * 提醒信息(Remind)业务逻辑实现类
 *
 * @author sei
 * @since 2020-05-22 18:09:16
 */
@Service("remindService")
public class RemindService extends BaseEntityService<Remind> {
    @Autowired
    private RemindDao dao;
    @Autowired
    private ContentBodyDao contentBodyDao;
    @Autowired(required = false)
    private DocumentManager documentManager;

    @Override
    protected BaseEntityDao<Remind> getDao() {
        return dao;
    }

    /**
     * 获取指定用户未读提醒数
     *
     * @param tenantCode 租户代码
     * @param userId     用户id
     * @return 未读提醒数
     */
    public Long countUnRead(String tenantCode, String userId) {
        return dao.countUnRead(tenantCode, userId);
    }

    /**
     * 发送提醒
     *
     * @param content 提醒内容
     * @param docIds  提醒附件id
     * @param reminds 提醒
     * @return 返回操作结果
     */
    public ResultData<String> sendRemind(String content, Set<String> docIds, Remind... reminds) {
        if (Objects.isNull(reminds) || StringUtils.isBlank(content)) {
            // 发送的提醒以及内容不能为空！
            return ResultData.fail("00021");
        }

        ContentBody body = new ContentBody(content);
        // 保存内容
        contentBodyDao.save(body);
        String contentId = body.getId();

        LocalDateTime now = LocalDateTime.now();
        List<Remind> remindList = new ArrayList<>();
        for (Remind remind : reminds) {
            remind.setId(null);
            remind.setContentId(contentId);
            remind.setRemindDate(now);
            remindList.add(remind);
        }
        // 保存提醒
        dao.save(remindList);

        //绑定附件
        if (CollectionUtils.isNotEmpty(docIds) && Objects.nonNull(documentManager)) {
            documentManager.bindBusinessDocuments(contentId, docIds);
        }
        return ResultData.success("OK");
    }
}
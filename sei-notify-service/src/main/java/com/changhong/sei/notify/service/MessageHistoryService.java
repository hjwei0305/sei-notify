package com.changhong.sei.notify.service;

import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.notify.dao.MessageHistoryDao;
import com.changhong.sei.notify.entity.ContentBody;
import com.changhong.sei.notify.entity.MessageHistory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


/**
 * 消息历史(MessageHistory)业务逻辑实现类
 *
 * @author sei
 * @since 2020-06-11 14:36:17
 */
@Service("messageHistoryService")
public class MessageHistoryService extends BaseEntityService<MessageHistory> {
    @Autowired
    private MessageHistoryDao dao;
    @Autowired
    private ContentBodyService contentBodyService;

    @Override
    protected BaseEntityDao<MessageHistory> getDao() {
        return dao;
    }

    @Transactional
    public ResultData<String> recordHistory(List<MessageHistory> histories, String content, boolean success, String log) {
        LogUtil.debug("记录消息历史内容:{0}", content);
        String contentId;
        if (StringUtils.isNotBlank(content)) {
            ContentBody contentBody = new ContentBody(content);
            contentBodyService.save(contentBody);

            contentId = contentBody.getId();
        } else {
            LogUtil.error("记录消息历史失败, 消息内容不能为空.");
            return ResultData.fail("消息内容不存在.");
        }
        LocalDateTime now = LocalDateTime.now();
        for (MessageHistory history : histories) {
            history.setContentId(contentId);
            history.setSendDate(now);
            if (Objects.isNull(history.getSendStatus())) {
                history.setSendStatus(success);
            }
            if (Objects.isNull(history.getSendLog())) {
                history.setSendLog(log);
            }
        }
        dao.save(histories);
        return ResultData.success("ok");
    }

    /**
     * 用户查看
     *
     * @param id 消息id
     * @return 返回明细
     */
    public ResultData<MessageHistory> detail(String id) {
        if (StringUtils.isNotBlank(id)) {
            MessageHistory message = dao.findOne(id);
            if (Objects.nonNull(message)) {
                String contentId = message.getContentId();
                //加载内容
                if (StringUtils.isNotBlank(contentId)) {
                    ContentBody body = contentBodyService.findOne(contentId);
                    if (Objects.nonNull(body)) {
                        message.setContent(body.getContent());
                    }
                }
                return ResultData.success(message);
            } else {
                return ResultData.fail(id + " - 消息不存在");
            }
        } else {
            return ResultData.fail("参数不能为空!");
        }
    }
}
package com.changhong.sei.notify.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.context.SessionUser;
import com.changhong.sei.core.dao.BaseEntityDao;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.notify.dao.BulletinDao;
import com.changhong.sei.notify.dto.BulletinDto;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.entity.Bulletin;
import com.changhong.sei.notify.entity.Message;
import com.changhong.sei.notify.entity.compose.BulletinCompose;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 实现功能：维护消息通告的业务逻辑
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-23 17:46
 */
@Service
public class BulletinService extends BaseEntityService<Bulletin> {

    @Autowired
    private BulletinDao dao;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    protected BaseEntityDao<Bulletin> getDao() {
        return dao;
    }

    /**
     * 新增修改通告
     *
     * @param bulletin 消息通告
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResultWithData<Bulletin> saveBulletin(Bulletin bulletin, Message message) {
        ResultData<Message> resultData = messageService.saveMessage(message);
        if (resultData.successful()) {
            message = resultData.getData();
            bulletin.setMsgId(message.getId());
            dao.save(bulletin);

            return OperateResultWithData.operationSuccessWithData(bulletin);
        } else {
            return OperateResultWithData.operationFailure(resultData.getMessage());
        }
    }

    /**
     * 发布通告
     *
     * @param bulletinDto 消息通告DTO
     * @return 业务处理结果
     */
    public ResultData<String> sendBulletin(BulletinDto bulletinDto) {
        // DTO转换为Entity
        Bulletin bulletin = modelMapper.map(bulletinDto, Bulletin.class);
        try {
            Message message = new Message();
            message.setId(bulletinDto.getMsgId());
            message.setCategory(NotifyType.SEI_BULLETIN);
            message.setSubject(bulletinDto.getSubject());
            message.setContentId(bulletinDto.getContentId());
            message.setContent(bulletinDto.getContent());
            message.setPriority(bulletinDto.getPriority());
            message.setTargetType(bulletinDto.getTargetType());
            message.setTargetValue(bulletinDto.getTargetValue());
            message.setTargetName(bulletinDto.getTargetName());
            message.setDocIds(bulletinDto.getDocIds());

            // 执行业务逻辑
            OperateResultWithData<Bulletin> result = this.saveBulletin(bulletin, message);
            if (result.successful()) {
                OperateResult result1 = this.releaseBulletin(Sets.newHashSet(result.getData().getId()));

                return ResultDataUtil.convertFromOperateResult(result1);
            } else {
                return ResultData.fail(ContextUtil.getMessage("00009", result.getMessage()));
            }
        } catch (Exception e) {
            LogUtil.error("发布通告异常！", e);
            // 发布通告异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00009", e.getMessage()));
        }
    }

    /**
     * 发布通告
     *
     * @param ids 要发布的通告id
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResult releaseBulletin(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            // 发布通告参数不能为空!
            return OperateResult.operationFailure("00004");
        }
        List<Bulletin> bulletins = dao.findAllById(ids);
        if (!CollectionUtils.isEmpty(bulletins)) {
            LocalDateTime now = LocalDateTime.now();
            LocalDate nowDate = now.toLocalDate();

            boolean allowPublish = Boolean.TRUE;
            for (Bulletin bulletin : bulletins) {
                // 检查有效时间是否大于当前时间
                if (nowDate.isAfter(bulletin.getInvalidDate())) {
                    bulletin.setEffective(Boolean.FALSE);
                    allowPublish = Boolean.FALSE;
                }
            }
            if (!allowPublish) {
                dao.save(bulletins);
                return OperateResult.operationFailure("发布失败,通告有效时间小于当前时间.");
            }

            SessionUser user = ContextUtil.getSessionUser();
            String account = user.getAccount();
            String userName = user.getUserName();

            Set<String> msgIds = new HashSet<>();
            for (Bulletin bulletin : bulletins) {
                msgIds.add(bulletin.getMsgId());

                bulletin.setPublish(Boolean.TRUE);
                bulletin.setPublishUserAccount(account);
                bulletin.setPublishUserName(userName);
                bulletin.setPublishDate(now);
            }
            dao.save(bulletins);

            messageService.publishMessage(msgIds, account, userName);
        }
        return OperateResult.operationSuccess();
    }

    /**
     * 撤销通告
     *
     * @param ids 要撤销的通告id
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResult cancelBulletin(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            // 撤销通告参数不能为空!
            return OperateResult.operationFailure("00005");
        }
        List<Bulletin> bulletins = dao.findAllById(ids);
        if (!CollectionUtils.isEmpty(bulletins)) {
            LocalDateTime date = LocalDateTime.now();
            Set<String> msgIds = new HashSet<>();
            SessionUser sessionUser = ContextUtil.getSessionUser();
            String account = sessionUser.getAccount();
            String userName = sessionUser.getUserName();
            for (Bulletin bulletin : bulletins) {
                msgIds.add(bulletin.getMsgId());

                bulletin.setCancelDate(date);
                bulletin.setCancelUserAccount(account);
                bulletin.setCancelUserName(userName);

                bulletin.setPublish(Boolean.FALSE);
                bulletin.setPublishUserAccount(null);
                bulletin.setPublishUserName(null);
                bulletin.setPublishDate(null);
            }
            dao.save(bulletins);

            // 撤销发布的消息
            messageService.cancelPublishMessage(msgIds);
        }

        return OperateResult.operationSuccess();
    }

    /**
     * 删除通告
     *
     * @param ids 要删除的通告id
     * @return 返回操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public OperateResult deleteBulletin(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            // 删除通告参数不能为空!
            return OperateResult.operationFailure("00006");
        }
        List<Bulletin> bulletins = dao.findAllById(ids);
        if (CollectionUtils.isNotEmpty(bulletins)) {
            for (Bulletin bulletin : bulletins) {
                if (bulletin.getPublish()) {
                    // 通告已发布不能删除
                    return OperateResult.operationFailure("00028");
                }
            }

            SessionUser user = ContextUtil.getSessionUser();
            Set<String> msgIds = bulletins.stream().map(Bulletin::getMsgId).collect(Collectors.toSet());
            messageService.delMessage(msgIds, user.getAccount(), user.getUserName());

            dao.delete(ids);
        }
        return OperateResult.operationSuccess();
    }

    /**
     * 查看通告
     *
     * @param id id
     * @return 返回通告明细
     */
    public ResultData<BulletinCompose> getBulletin(String id) {
        BulletinCompose result = new BulletinCompose();
        Bulletin bulletin = dao.findOne(id);
        if (Objects.nonNull(bulletin)) {
            result.setBulletin(bulletin);
            ResultData<Message> resultData = messageService.detail(bulletin.getMsgId());
            if (resultData.successful()) {
                result.setMessage(resultData.getData());

                return ResultData.success(result);
            } else {
                return ResultData.fail(resultData.getMessage());
            }
        }
        return ResultData.fail(id + " - 消息不存在");
    }

    @Transactional
    public ResultData<Integer> updateEffective() {
        int count = 0;
        // 失效时间大于当天
        Search search = Search.createSearch();
        search.addFilter(new SearchFilter("effective", Boolean.TRUE));
        search.addFilter(new SearchFilter("invalidDate", LocalDate.now(), SearchFilter.Operator.LT));
        List<Bulletin> bulletins = dao.findByFilters(search);
        if (CollectionUtils.isNotEmpty(bulletins)) {
            Set<String> msgIds = bulletins.stream().map(Bulletin::getMsgId).collect(Collectors.toSet());
            List<Message> messages = messageService.findByIds(msgIds);
            if (CollectionUtils.isNotEmpty(messages)) {
                for (Message message : messages) {
                    message.setEffective(Boolean.FALSE);
                }
                messageService.save(messages);
            }
        }
        return ResultData.success(count);
    }

}

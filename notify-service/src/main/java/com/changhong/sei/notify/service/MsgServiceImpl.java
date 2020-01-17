package com.changhong.sei.notify.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.manager.bo.OperateResult;
import com.changhong.sei.core.manager.bo.OperateResultWithData;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.notify.api.MsgService;
import com.changhong.sei.notify.dto.BaseMessageDto;
import com.changhong.sei.notify.dto.BulletinDto;
import com.changhong.sei.notify.dto.MessageCategory;
import com.changhong.sei.notify.dto.Priority;
import com.changhong.sei.notify.entity.compose.BulletinCompose;
import com.changhong.sei.notify.manager.MsgManager;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <strong>实现功能:</strong>
 * <p>通告消息管理的服务实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-15 17:16
 */
@Service
@Api(value = "MsgService", tags = "通告消息管理API服务")
public class MsgServiceImpl implements MsgService {
    @Autowired
    private MsgManager msgManager;
    @Autowired
    private ModelMapper modelMapper;
    /**
     * 获取优先级枚举值清单
     *
     * @return 枚举值清单
     */
    @Override
    public ResultData<Map<String, String>> getPriority() {
        return ResultDataUtil.getEnumMap(Priority.class);
    }

    /**
     * 获取消息类别枚举值清单
     *
     * @return 枚举值清单
     */
    @Override
    public ResultData<Map<String, String>> getCategory() {
        return ResultDataUtil.getEnumMap(MessageCategory.class);
    }

    /**
     * 获取未读消息数
     *
     * @return 查询结果
     */
    @Override
    public ResultData<Long> unreadCount() {
        Long unread;
        try {
           unread = msgManager.unreadCount();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error("获取未读消息数异常！", e);
            // 获取未读消息数异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00013", e.getMessage()));
        }
        return ResultData.success(unread);
    }

    /**
     * 获取用户未读数据
     *
     * @return 查询结果
     */
    @Override
    public ResultData<Map<String, List<BaseMessageDto>>> unreadData() {
        OperateResultWithData<Map<String, List<BaseMessageDto>>> operateResult;
        try {
            operateResult = msgManager.unreadData();
        } catch (Exception e) {
            LogUtil.error("获取用户未读数据异常！", e);
            // 获取用户未读数据异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00014", e.getMessage()));
        }
        return ResultDataUtil.convertFromOperateResult(operateResult);
    }

    /**
     * 用户阅读
     *
     * @param category 消息类型
     * @param id       通告Id
     * @return 操作结果
     */
    @Override
    public ResultData read(MessageCategory category, String id) {
        OperateResult operateResult;
        try {
            operateResult = msgManager.read(category, id);
        } catch (Exception e) {
            LogUtil.error("执行用户阅读异常！", e);
            // 执行用户阅读异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00015", e.getMessage()));
        }
        return ResultDataUtil.convertFromOperateResult(operateResult);
    }

    /**
     * 用户查看
     *
     * @param category 消息类型
     * @param id       通告Id
     * @return 操作结果
     */
    @Override
    public ResultData<BaseMessageDto> detail(MessageCategory category, String id) {
        OperateResultWithData<BaseMessageDto> operateResult;
        try {
            operateResult = msgManager.detail(category, id);
        } catch (Exception e) {
            LogUtil.error("执行用户查看异常！", e);
            // 执行用户查看异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00016", e.getMessage()));
        }
        return ResultDataUtil.convertFromOperateResult(operateResult);
    }

    /**
     * 默认获取优先级高的通告
     *
     * @return 操作结果
     */
    @Override
    public ResultData<BaseMessageDto> getFirstUnreadBulletin() {
        OperateResultWithData<BaseMessageDto> operateResult;
        try {
            operateResult = msgManager.getFirstUnreadBulletin();
        } catch (Exception e) {
            LogUtil.error("默认获取优先级高的通告异常！", e);
            // 默认获取优先级高的通告异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00017", e.getMessage()));
        }
        return ResultDataUtil.convertFromOperateResult(operateResult);
    }

    /**
     * 将数据实体转换成DTO
     *
     * @param entity 业务实体
     * @return DTO
     */
    private BulletinDto convertToDto(BulletinCompose entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        BulletinDto dto = modelMapper.map(entity.getBulletin(), BulletinDto.class);
        dto.setContent(entity.getContent());
        return dto;
    }


    /**
     * 用户查询通告
     *
     * @param search 查询参数
     * @return 查询结果
     */
    @Override
    public ResultData<PageResult<BulletinDto>> findBulletinByPage4User(Search search) {
        PageResult<BulletinDto> resultData;
        try {
            PageResult<BulletinCompose> pageResult = msgManager.findBulletinByPage4User(search);
            resultData = new PageResult<>(pageResult);
            List<BulletinDto> rows = pageResult.getRows().stream().map(this::convertToDto).collect(Collectors.toList());
            resultData.setRows(rows);
        } catch (Exception e) {
            LogUtil.error("用户查询通告异常！", e);
            // 用户查询通告异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00018", e.getMessage()));
        }
        return ResultData.success(resultData);
    }
}

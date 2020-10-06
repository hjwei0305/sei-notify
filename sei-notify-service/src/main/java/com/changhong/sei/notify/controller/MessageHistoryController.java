package com.changhong.sei.notify.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.notify.api.MessageHistoryApi;
import com.changhong.sei.notify.dto.MessageHistoryDto;
import com.changhong.sei.notify.entity.MessageHistory;
import com.changhong.sei.notify.service.MessageHistoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 消息历史(MessageHistory)控制类
 *
 * @author sei
 * @since 2020-06-11 14:36:17
 */
@RestController
@Api(value = "MessageHistoryApi", tags = "消息历史服务")
@RequestMapping(path = "messageHistory", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MessageHistoryController extends BaseEntityController<MessageHistory, MessageHistoryDto>
        implements MessageHistoryApi {
    /**
     * 消息历史服务对象
     */
    @Autowired
    private MessageHistoryService service;

    @Override
    public BaseEntityService<MessageHistory> getService() {
        return service;
    }

    /**
     * 获取数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<MessageHistory> getEntityClass() {
        return MessageHistory.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<MessageHistoryDto> getDtoClass() {
        return MessageHistoryDto.class;
    }

    /**
     * 通过Id获取一个消息历史
     *
     * @param id 消息历史Id
     * @return 消息历史
     */
    @Override
    public ResultData<MessageHistoryDto> findOne(String id) {
        ResultData<MessageHistory> resultData;
        try {
            resultData = service.detail(id);
        } catch (Exception e) {
            LogUtil.error("获取业务实体异常！", e);
            // 获取业务实体异常！{0}
            return ResultData.fail(ContextUtil.getMessage("core_service_00005", e.getMessage()));
        }
        if (resultData.successful()) {
            // 转换数据 to DTO
            MessageHistoryDto dto = convertToDto(resultData.getData());
            return ResultData.success(dto);
        } else {
            return ResultData.fail(resultData.getMessage());
        }
    }

    /**
     * 分页查询消息历史实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<MessageHistoryDto>> findByPage(Search search) {
        if (Objects.isNull(search)) {
            search = Search.createSearch();
        }
        PageResult<MessageHistory> bulletinPageResult = service.findByPage(search);

        return convertToDtoPageResult(bulletinPageResult);
    }
}
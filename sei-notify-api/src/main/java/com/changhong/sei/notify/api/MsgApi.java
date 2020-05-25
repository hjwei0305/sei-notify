package com.changhong.sei.notify.api;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.notify.dto.BaseMessageDto;
import com.changhong.sei.notify.dto.BulletinDto;
import com.changhong.sei.notify.dto.NotifyType;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * <strong>实现功能:</strong>
 * <p>通告消息管理API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-15 17:11
 */
@FeignClient(name = "sei-notify", path = "message")
public interface MsgApi {

    /**
     * 获取优先级枚举值清单
     * @return 枚举值清单
     */
    @GetMapping(path = "getPriority")
    @ApiOperation(value = "获取优先级枚举值清单", notes = "获取优先级枚举值和描述的键值对")
    ResultData<Map<String, String>> getPriority();

    /**
     * 获取消息类别枚举值清单
     * @return 枚举值清单
     */
    @GetMapping(path = "getCategory")
    @ApiOperation(value = "获取消息类别枚举值清单", notes = "获取消息类别枚举值和描述的键值对")
    ResultData<Map<String, String>> getCategory();

    /**
     * 获取未读消息数
     * @return 查询结果
     */
    @GetMapping(path = "unreadCount")
    @ApiOperation(value = "获取未读消息数", notes = "获取当前用户的所有未读消息数量")
    ResultData<Long> unreadCount();

    /**
     * 获取用户未读数据
     * @return 查询结果
     */
    @GetMapping(path = "unreadData")
    @ApiOperation(value = "获取用户未读数据", notes = "获取当前用户的所有未读消息数据")
    ResultData<Map<String, List<BaseMessageDto>>> unreadData();

    /**
     * 用户阅读
     * @param category 消息类型
     * @param id 通告Id
     * @return 操作结果
     */
    @PostMapping(path = "read")
    @ApiOperation(value = "用户阅读", notes = "用户已阅读,变更阅读状态")
    ResultData<String> read(@RequestParam("category") NotifyType category, @RequestParam("id") String id);

    /**
     * 用户查看
     * @param category 消息类型
     * @param id 通告Id
     * @return 操作结果
     */
    @GetMapping(path = "detail")
    @ApiOperation(value = "用户查看", notes = "用户查看通告内容")
    ResultData<BaseMessageDto> detail(@RequestParam("category") NotifyType category, @RequestParam("id") String id);

    /**
     * 默认获取优先级高的通告
     * @return 操作结果
     */
    @GetMapping(path = "getFirstUnreadBulletin")
    @ApiOperation(value = "默认获取优先级高的通告", notes = "获取当前用户优先级最高的通告")
    ResultData<BaseMessageDto> getFirstUnreadBulletin();

    /**
     * 用户查询通告
     * @param search 查询参数
     * @return 查询结果
     */
    @PostMapping(path = "findMessageByPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "用户查询通告", notes = "一般用户分页查询通告")
    ResultData<PageResult<BulletinDto>> findBulletinByPage4User(@RequestBody Search search);
}

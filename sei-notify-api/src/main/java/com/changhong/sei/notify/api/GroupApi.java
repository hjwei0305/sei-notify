package com.changhong.sei.notify.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.notify.dto.GroupDto;
import com.changhong.sei.notify.dto.GroupUserDto;
import com.changhong.sei.notify.dto.PositionDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * 群组(Group)API
 *
 * @author sei
 * @since 2020-05-22 11:04:28
 */
@Valid
@FeignClient(name = "sei-notify", path = "group")
public interface GroupApi extends BaseEntityApi<GroupDto> {

    /**
     * 屏蔽删除接口
     */
    @Override
    ResultData<String> delete(String id);

    /**
     * 冻结群组
     *
     * @param ids 群组id集合
     * @return 操作结果
     */
    @PostMapping(path = "frozen", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "冻结群组", notes = "冻结群组")
    ResultData<String> frozen(@RequestBody List<String> ids);

    /**
     * 解冻群组
     *
     * @param ids 群组id集合
     * @return 操作结果
     */
    @PostMapping(path = "unfrozen", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "解冻群组", notes = "解冻群组")
    ResultData<String> unfrozen(@RequestBody List<String> ids);

    /**
     * 获取所有群组实体
     *
     * @return 群组实体清单
     */
    @GetMapping(path = "findAll")
    @ApiOperation(value = "获取所有群组实体", notes = "获取所有群组实体")
    ResultData<List<GroupDto>> findAll();

    /**
     * 获取所有未冻结的群组实体
     *
     * @return 群组实体清单
     */
    @GetMapping(path = "findAllUnfrozen")
    @ApiOperation(value = "获取所有未冻结群组实体", notes = "获取所有未冻结群组实体")
    ResultData<List<GroupDto>> findAllUnfrozen();

    /**
     * 添加群组用户
     *
     * @param groupUserDtos 群组用户集合
     * @return 操作结果
     */
    @PostMapping(path = "addGroupItem", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "添加群组项", notes = "添加群组项")
    ResultData<String> addGroupItem(@RequestBody @Valid List<GroupUserDto> groupUserDtos);

    /**
     * 移除群组用户
     *
     * @param groupUserIds 群组用户id集合
     * @return 操作结果
     */
    @PostMapping(path = "removeGroupItem", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "移除群组项", notes = "移除群组项")
    ResultData<String> removeGroupItem(@RequestBody List<String> groupUserIds);

    /**
     * 获取指定群组用户
     *
     * @param groupId 群组id
     * @return 返回指定群组用户对象
     */
    @GetMapping(path = "getGroupItems")
    @ApiOperation(value = "获取指定群组项", notes = "获取指定群组项")
    ResultData<List<GroupUserDto>> getGroupItems(@RequestParam("groupId") String groupId);

    /**
     * 获取用户账号分页数据
     */
    @PostMapping(path = "getUserAccounts", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取用户账号分页数据", notes = "获取用户账号分页数据")
    ResultData<PageResult<GroupUserDto>> getUserAccounts(@RequestBody Search search);

    /**
     * 分页查询岗位实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    ResultData<PageResult<PositionDto>> findPositionByPage(@RequestBody Search search);

    /**
     * 根据群组获取用户id集合
     *
     * @param groupCode 群组代码
     * @return 用户id集合
     */
    @GetMapping(path = "getUserIdsByGroup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "根据群组获取用户id集合", notes = "根据群组获取用户id集合")
    ResultData<List<String>> getUserIdsByGroup(@RequestParam("groupCode") String groupCode);
}
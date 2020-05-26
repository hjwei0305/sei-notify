package com.changhong.sei.notify.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.notify.api.GroupApi;
import com.changhong.sei.notify.dto.GroupDto;
import com.changhong.sei.notify.dto.GroupItemDto;
import com.changhong.sei.notify.dto.PositionDto;
import com.changhong.sei.notify.entity.Group;
import com.changhong.sei.notify.entity.GroupItem;
import com.changhong.sei.notify.service.GroupService;
import com.changhong.sei.notify.dto.AccountResponse;
import com.changhong.sei.notify.service.cust.BasicIntegration;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 群组(Group)控制类
 *
 * @author sei
 * @since 2020-05-22 11:04:12
 */
@RestController
@Api(value = "GroupApi", tags = "群组服务")
@RequestMapping(path = "group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GroupController extends BaseEntityController<Group, GroupDto> implements GroupApi {

    @Autowired
    private ModelMapper modelMapper;
    /**
     * 群组服务对象
     */
    @Autowired
    private GroupService service;

    @Autowired
    private BasicIntegration basicIntegration;

    @Override
    public BaseEntityService<Group> getService() {
        return service;
    }

    /**
     * 删除业务实体
     *
     * @param id 业务实体Id
     * @return 操作结果
     */
    @Override
    public ResultData<String> delete(@PathVariable("id") String id) {
        // 屏蔽delete,使用冻结
        return ResultData.fail("接口不可用");
    }

    /**
     * 冻结群组
     *
     * @param ids 群组id集合
     * @return 操作结果
     */
    @Override
    public ResultData<String> frozen(List<String> ids) {
        return service.frozen(ids, Boolean.TRUE);
    }

    /**
     * 解冻群组
     *
     * @param ids 群组id集合
     * @return 操作结果
     */
    @Override
    public ResultData<String> unfrozen(List<String> ids) {
        return service.frozen(ids, Boolean.FALSE);
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<GroupDto>> findAll() {
        List<GroupDto> data;
        try {
            List<Group> entities = getService().findAll();
            data = entities.stream().map(this::convertToDtoWithoutContent).collect(Collectors.toList());
        } catch (Exception e) {
            LogUtil.error("获取所有业务实体异常！", e);
            // 获取所有业务实体异常！{0}
            return ResultData.fail(ContextUtil.getMessage("core_service_00006", e.getMessage()));
        }
        return ResultData.success(data);
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<GroupDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAllUnfrozen()));
    }

    /**
     * 添加群组用户
     *
     * @param groupUserDtos 群组用户集合
     * @return 操作结果
     */
    @Override
    public ResultData<String> addGroupItem(@Valid List<GroupItemDto> groupUserDtos) {
        if (CollectionUtils.isNotEmpty(groupUserDtos)) {
            List<GroupItem> groupUsers = groupUserDtos.stream().map(
                    v -> modelMapper.map(v, GroupItem.class)
            ).collect(Collectors.toList());
            return service.addGroupUsers(groupUsers);
        }
        return ResultData.fail("群组用户为空,不能添加");
    }

    /**
     * 移除群组用户
     *
     * @param groupUserIds 群组用户id集合
     * @return 操作结果
     */
    @Override
    public ResultData<String> removeGroupItem(List<String> groupUserIds) {
        return service.delGroupUser(groupUserIds);
    }

    /**
     * 获取指定群组用户
     *
     * @param groupId 群组id
     * @return 返回指定群组用户对象
     */
    @Override
    public ResultData<List<GroupItemDto>> getGroupItems(String groupId) {
        ResultData<List<GroupItem>> resultData = service.getGroupUsers(groupId);
        if (resultData.successful()) {
            List<GroupItemDto> list;
            List<GroupItem> groupUsers = resultData.getData();
            if (CollectionUtils.isNotEmpty(groupUsers)) {
                list = groupUsers.stream().map(v -> modelMapper.map(v, GroupItemDto.class)).collect(Collectors.toList());
            } else {
                list = Lists.newArrayList();
            }
            return ResultData.success(list);
        } else {
            return ResultData.fail(resultData.getMessage());
        }
    }

    /**
     * 获取用户账号分页数据
     */
    @Override
    public ResultData<PageResult<GroupItemDto>> getUserAccounts(Search search) {
        search.setQuickSearchProperties(Sets.newHashSet("account", "name"));
        ResultData<PageResult<AccountResponse>> resultData = basicIntegration.findAccountByPage(search);
        if (resultData.successful()) {
            PageResult<AccountResponse> pageResult = resultData.getData();
            List<AccountResponse> accounts = pageResult.getRows();

            GroupItemDto dto;
            List<GroupItemDto> list = new ArrayList<>();
            for (AccountResponse account : accounts) {
                dto = new GroupItemDto();
                dto.setItemId(account.getUserId());
                dto.setItemCode(account.getAccount());
                dto.setItemName(account.getName());
                list.add(dto);
            }
            PageResult<GroupItemDto> dtoPageResult = new PageResult<>(pageResult);
            dtoPageResult.setRows(list);

            return ResultData.success(dtoPageResult);
        }
        return ResultData.fail(resultData.getMessage());
    }

    /**
     * 分页查询岗位实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<PositionDto>> findPositionByPage(Search search) {
        return basicIntegration.findPositionByPage(search);
    }

    /**
     * 根据群组获取用户id集合
     *
     * @param groupCode 群组代码
     * @return 用户id集合
     */
    @Override
    public ResultData<List<String>> getUserIdsByGroup(String groupCode) {
        return service.getUserIdsByGroup(groupCode);
    }

    /**
     * 将数据实体转换成DTO（不含内容属性）
     *
     * @param entity 业务实体
     * @return DTO
     */
    private GroupDto convertToDtoWithoutContent(Group entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        // 自定义规则
        return modelMapper.map(entity, GroupDto.class);
    }
}
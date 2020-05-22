package com.changhong.sei.notify.controller;

import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.notify.api.GroupApi;
import com.changhong.sei.notify.dto.GroupDto;
import com.changhong.sei.notify.entity.Group;
import com.changhong.sei.notify.service.GroupService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    /**
     * 群组服务对象
     */
    @Autowired
    private GroupService service;

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
    public ResultData delete(@PathVariable("id") String id) {
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
}
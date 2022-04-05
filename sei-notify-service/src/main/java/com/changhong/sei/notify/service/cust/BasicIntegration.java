package com.changhong.sei.notify.service.cust;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.notify.dto.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 实现功能：sei基础应用(basic)集成
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-25 06:37
 */
public interface BasicIntegration {

    /**
     * 获取当前用户有权限的树形组织实体清单
     *
     * @param featureCode 功能项代码
     * @return 有权限的树形组织实体清单
     */
    ResultData<List<OrganizationDto>> getUserAuthorizedTreeEntities(String featureCode);

    /**
     * 通过用户Id清单获取用户信息
     *
     * @param userIds 用户Id清单
     * @return 用户信息
     */
    ResultData<List<UserNotifyInfo>> findNotifyInfoByUserIds(List<String> userIds);


    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    ResultData<PageResult<AccountResponse>> findAccountByPage(Search search);

    /**
     * 获取用户的组织机构Id清单
     *
     * @param userId 用户Id
     * @return 组织机构Id清单
     */
    ResultData<List<String>> getEmployeeOrgIds(String userId);

    /**
     * 分页查询岗位实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    ResultData<PageResult<PositionDto>> findPositionByPage(Search search);

    /**
     * 按岗位获取接收者
     */
    ResultData<List<String>> getUserIdsByPosition(Set<String> positionCode);

    /**
     * 获取用户的岗位Id清单
     *
     * @param userId 用户Id
     * @return 岗位Id清单
     */
    ResultData<List<String>> getEmployeePositionIds(String userId);

    /**
     * 分页查询角色实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    ResultData<PageResult<RoleDto>> findRoleByPage(Search search);

    /**
     * 按岗位获取接收者
     */
    ResultData<List<String>> getUserIdsByRole(Set<String> featureRoleCode);

    /**
     * 获取用户的角色id清单
     *
     * @param userId 用户Id
     * @return 角色id清单
     */
    ResultData<List<String>> getRoleIds(String userId);

}

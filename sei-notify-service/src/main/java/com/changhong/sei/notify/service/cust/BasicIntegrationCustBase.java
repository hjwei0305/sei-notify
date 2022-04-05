package com.changhong.sei.notify.service.cust;

import com.changhong.sei.apitemplate.ApiTemplate;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.notify.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;

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
public class BasicIntegrationCustBase implements BasicIntegration {
    @Value("${sei.auth.server-name:sei-auth}")
    private String authServiceName;

    @Value("${sei.basic.server-name:sei-basic}")
    private String basicServiceName;

    @Autowired
    private ApiTemplate apiTemplate;

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<AccountResponse>> findAccountByPage(Search search) {
        return apiTemplate.postByAppModuleCode(authServiceName, "/account/findByPage", new ParameterizedTypeReference<ResultData<PageResult<AccountResponse>>>() {
        }, search);
//        return accountClient.findByPage(search);
    }

    /**
     * 获取用户的组织机构Id清单
     *
     * @param userId 用户Id
     * @return 组织机构Id清单
     */
    @Override
    public ResultData<List<String>> getEmployeeOrgIds(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        return apiTemplate.getByAppModuleCode(basicServiceName, "/employee/getEmployeeOrgIds", new ParameterizedTypeReference<ResultData<List<String>>>() {
        }, params);
//        return employeeClient.getEmployeeOrgCodes(userId);
    }

    /**
     * 获取用户的岗位Id清单
     *
     * @param userId 用户Id
     * @return 岗位Id清单
     */
    @Override
    public ResultData<List<String>> getEmployeePositionIds(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        return apiTemplate.getByAppModuleCode(basicServiceName, "/employee/getEmployeePositionIds", new ParameterizedTypeReference<ResultData<List<String>>>() {
        }, params);
//        return employeeClient.getEmployeePositionCodes(userId);
    }

    /**
     * 获取当前用户有权限的树形组织实体清单
     *
     * @param featureCode 功能项代码
     * @return 有权限的树形组织实体清单
     */
    @Override
    public ResultData<List<OrganizationDto>> getUserAuthorizedTreeEntities(String featureCode) {
        Map<String, String> params = new HashMap<>();
        params.put("featureCode", featureCode);
        return apiTemplate.getByAppModuleCode(basicServiceName, "/organization/getUserAuthorizedTreeEntities", new ParameterizedTypeReference<ResultData<List<OrganizationDto>>>() {
        }, params);
//        return organizationClient.getUserAuthorizedTreeEntities(featureCode);
    }

    /**
     * 通过用户Id清单获取用户信息
     *
     * @param userIds 用户Id清单
     * @return 用户信息
     */
    @Override
    public ResultData<List<UserNotifyInfo>> findNotifyInfoByUserIds(List<String> userIds) {
        return apiTemplate.postByAppModuleCode(basicServiceName, "/userProfile/findNotifyInfoByUserIds", new ParameterizedTypeReference<ResultData<List<UserNotifyInfo>>>() {
        }, userIds);
//        return userNotifyInfoClient.findNotifyInfoByUserIds(userIds);
    }

    /**
     * 按岗位获取接收者
     */
    @Override
    public ResultData<List<String>> getUserIdsByPosition(Set<String> positionCodes) {
        return apiTemplate.postByAppModuleCode(basicServiceName, "/position/getUserIdsByPositionCode", new ParameterizedTypeReference<ResultData<List<String>>>() {
        }, positionCodes);
    }

    /**
     * 分页查询岗位实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<PositionDto>> findPositionByPage(Search search) {
        return apiTemplate.postByAppModuleCode(basicServiceName, "/position/findByPage", new ParameterizedTypeReference<ResultData<PageResult<PositionDto>>>() {
        }, search);
    }

    /**
     * 分页查询角色实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<RoleDto>> findRoleByPage(Search search) {
        return apiTemplate.postByAppModuleCode(basicServiceName, "/featureRole/findByPage", new ParameterizedTypeReference<ResultData<PageResult<RoleDto>>>() {
        }, search);
    }

    /**
     * 按角色获取接收者
     */
    @Override
    public ResultData<List<String>> getUserIdsByRole(Set<String> featureRoleCodes) {
        return apiTemplate.postByAppModuleCode(basicServiceName, "/featureRole/getUserIdsByFeatureRole", new ParameterizedTypeReference<ResultData<List<String>>>() {
        }, featureRoleCodes);
    }

    /**
     * 获取用户的角色id清单
     *
     * @param userId 用户Id
     * @return 角色id清单
     */
    @Override
    public ResultData<List<String>> getRoleIds(String userId) {
        return apiTemplate.postByAppModuleCode(basicServiceName, "/userFeatureRole/getRoleIdsByUserId", new ParameterizedTypeReference<ResultData<List<String>>>() {
        }, userId);
    }
}

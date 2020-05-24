package com.changhong.sei.notify.service.cust;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.notify.dto.OrganizationDto;
import com.changhong.sei.notify.dto.UserNotifyInfo;
import com.changhong.sei.notify.dto.AccountResponse;
import com.changhong.sei.notify.service.client.AccountClient;
import com.changhong.sei.notify.service.client.EmployeeClient;
import com.changhong.sei.notify.service.client.OrganizationClient;
import com.changhong.sei.notify.service.client.UserNotifyInfoClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 实现功能：sei基础应用(basic)集成
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-05-25 06:37
 */
public class BasicIntegrationCustBase implements BasicIntegration {

    @Autowired
    private AccountClient accountClient;
    @Autowired
    private EmployeeClient employeeClient;
    @Autowired
    private OrganizationClient organizationClient;
    @Autowired
    private UserNotifyInfoClient userNotifyInfoClient;

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<AccountResponse>> findByPage(Search search) {
        return accountClient.findByPage(search);
    }

    /**
     * 获取用户的组织机构代码清单
     *
     * @param userId 用户Id
     * @return 组织机构代码清单
     */
    @Override
    public ResultData<List<String>> getEmployeeOrgCodes(String userId) {
        return employeeClient.getEmployeeOrgCodes(userId);
    }

    /**
     * 获取用户的岗位代码清单
     *
     * @param userId 用户Id
     * @return 岗位代码清单
     */
    @Override
    public ResultData<List<String>> getEmployeePositionCodes(String userId) {
        return employeeClient.getEmployeePositionCodes(userId);
    }

    /**
     * 获取当前用户有权限的树形组织实体清单
     *
     * @param featureCode 功能项代码
     * @return 有权限的树形组织实体清单
     */
    @Override
    public ResultData<List<OrganizationDto>> getUserAuthorizedTreeEntities(String featureCode) {
        return organizationClient.getUserAuthorizedTreeEntities(featureCode);
    }

    /**
     * 通过用户Id清单获取用户信息
     *
     * @param userIds 用户Id清单
     * @return 用户信息
     */
    @Override
    public ResultData<List<UserNotifyInfo>> findNotifyInfoByUserIds(List<String> userIds) {
        return userNotifyInfoClient.findNotifyInfoByUserIds(userIds);
    }
}

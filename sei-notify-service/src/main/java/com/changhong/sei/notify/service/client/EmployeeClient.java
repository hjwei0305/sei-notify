package com.changhong.sei.notify.service.client;

import com.changhong.sei.core.dto.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <strong>实现功能:</strong>
 * <p>企业内部用户服务的API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-16 14:29
 */
@FeignClient(name = "sei-basic", path = "employee")
//@FeignClient(name = "${sei.basic.server-name}", url = "${sei.basic.server-url}", path = "employee")
public interface EmployeeClient {
    /**
     * 获取用户的组织机构代码清单
     * @param userId 用户Id
     * @return 组织机构代码清单
     */
    @GetMapping(path = "getEmployeeOrgCodes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultData<List<String>> getEmployeeOrgCodes(@RequestParam("userId") String userId);

    /**
     * 获取用户的岗位代码清单
     * @param userId 用户Id
     * @return 岗位代码清单
     */
    @GetMapping(path = "getEmployeePositionCodes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultData<List<String>> getEmployeePositionCodes(@RequestParam("userId") String userId);
}

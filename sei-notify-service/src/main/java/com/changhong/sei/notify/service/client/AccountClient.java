package com.changhong.sei.notify.service.client;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.notify.dto.AccountResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <strong>实现功能:</strong>
 * <p>用户账户服务的API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-16 14:29
 */
@FeignClient(name = "sei-auth", path = "account")
//@FeignClient(name = "${sei.basic.server-name}", url = "${sei.basic.server-url}", path = "employee")
public interface AccountClient {
    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @PostMapping(path = "findByPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "分页查询业务实体", notes = "分页查询业务实体")
    ResultData<PageResult<AccountResponse>> findByPage(@RequestBody Search search);
}

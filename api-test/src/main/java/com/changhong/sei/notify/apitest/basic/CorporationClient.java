package com.changhong.sei.notify.apitest.basic;

import com.changhong.sei.core.dto.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 实现功能: 调用basic的公司API服务的客户端
 *
 * @author 王锦光 wangjg
 * @version 2020-01-31 21:15
 */
@FeignClient(name = "sei-basic", path = "corporation")
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface CorporationClient {
    /**
     * 根据公司代码查询公司
     *
     * @param code 公司代码
     * @return 公司
     */
    @GetMapping(path = "findByCode")
    ResultData<CorporationDto> findByCode(@RequestParam("code")String code);
}

package com.changhong.sei.notify.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.ContentParams;
import com.changhong.sei.notify.dto.ContentTemplateDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * <strong>实现功能:</strong>
 * <p>内容模板API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 15:41
 */
@FeignClient(name = "sei-notify", path = "contentTemplate")
public interface ContentTemplateApi extends BaseEntityApi<ContentTemplateDto>
        , FindAllApi<ContentTemplateDto> {
    /**
     * 通过代码获取内容模板
     * @param code 代码
     * @return 内容模板
     */
    @GetMapping(path = "findByCode")
    @ApiOperation("通过代码获取内容模板")
    ResultData<ContentTemplateDto> findByCode(@RequestParam("code") String code);

    /**
     * 通过参数获取指定模板的内容
     * @param params 参数
     * @return 模板的内容
     */
    @PostMapping(path = "getContent", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultData<String> getContent(@RequestBody @Valid ContentParams params);
}

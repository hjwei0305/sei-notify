package com.changhong.sei.notify.api;

import com.changhong.sei.core.api.BaseEntityService;
import com.changhong.sei.core.api.FindAllService;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.ContentTemplateDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <strong>实现功能:</strong>
 * <p>内容模板API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 15:41
 */
@FeignClient(name = "sei-notify", path = "contentTemplate")
@RestController
@RequestMapping(path = "contentTemplate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface ContentTemplateService extends BaseEntityService<ContentTemplateDto>
        , FindAllService<ContentTemplateDto> {
    /**
     * 通过代码获取内容模板
     * @param code 代码
     * @return 内容模板
     */
    @GetMapping(path = "findByCode")
    @ApiOperation("通过代码获取内容模板")
    ResultData<ContentTemplateDto> findByCode(@RequestParam("code") String code);
}

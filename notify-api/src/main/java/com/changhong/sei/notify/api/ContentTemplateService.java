package com.changhong.sei.notify.api;

import com.changhong.sei.notify.dto.ResultData;
import com.changhong.sei.notify.dto.ContentTemplateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * <strong>实现功能:</strong>
 * <p>内容模板API接口</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 15:41
 */
@Path("contentTemplate")
@Api(value = "ContentTemplateService", tags = "内容模板API接口")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ContentTemplateService {
    /**
     * 获取所有数据
     *
     * @return 应用模块清单
     */
    @GET
    @Path("findAll")
    @ApiOperation("获取所有内容模板")
    ResultData<List<ContentTemplateDto>> findAll();

    /**
     * 保存内容模板
     * @param contentTemplateDto 内容模板DTO
     * @return 操作结果
     */
    @POST
    @Path("save")
    @ApiOperation("保存一个内容模板")
    ResultData<ContentTemplateDto> save(ContentTemplateDto contentTemplateDto);

    /**
     * 通过Id获取内容模板
     * @param id Id标识
     * @return 内容模板
     */
    @GET
    @Path("findOne")
    @ApiOperation("通过Id获取内容模板")
    ResultData<ContentTemplateDto> findOne(@QueryParam("id") String id);

    /**
     * 通过代码获取内容模板
     * @param code 代码
     * @return 内容模板
     */
    @GET
    @Path("findByCode")
    @ApiOperation("通过代码获取内容模板")
    ResultData<ContentTemplateDto> findByCode(@QueryParam("code") String code);
}

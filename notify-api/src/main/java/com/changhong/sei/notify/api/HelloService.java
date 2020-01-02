package com.changhong.sei.notify.api;

import com.changhong.sei.core.dto.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * <strong>实现功能:</strong>
 * <p>调试API接口你好</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2017-10-23 17:14
 */
@Path("hello")
@Api(value = "HelloService", tags = "调试API接口你好")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface HelloService {
    /**
     * say hello
     * @param name name
     * @return hello name
     */
    @GET
    @Path("sayHello")
    @ApiOperation("调试API接口说你好")
    ResultData<String> sayHello(@QueryParam("name") String name);
}

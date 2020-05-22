package com.changhong.sei.notify.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.notify.dto.GroupDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 群组(Group)API
 *
 * @author sei
 * @since 2020-05-22 11:04:28
 */
@Valid
@FeignClient(name = "sei-notify", path = "group")
public interface GroupApi extends BaseEntityApi<GroupDto> {
    /**
     * 冻结群组
     *
     * @param ids 群组id集合
     * @return 操作结果
     */
    @PostMapping(path = "frozen", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "冻结群组", notes = "冻结群组")
    ResultData<String> frozen(@RequestBody @Valid List<String> ids);

    /**
     * 解冻群组
     *
     * @param ids 群组id集合
     * @return 操作结果
     */
    @PostMapping(path = "unfrozen", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "解冻群组", notes = "解冻群组")
    ResultData<String> unfrozen(@RequestBody @Valid List<String> ids);
}
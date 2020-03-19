package com.changhong.sei.notify.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.controller.DefaultBaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.notify.api.BulletinApi;
import com.changhong.sei.notify.dto.BulletinDto;
import com.changhong.sei.notify.entity.Bulletin;
import com.changhong.sei.notify.entity.compose.BulletinCompose;
import com.changhong.sei.notify.service.BulletinService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * <strong>实现功能:</strong>
 * <p>维护消息通告的服务实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-15 15:03
 */
@RestController
@Api(value = "BulletinApi", tags = "维护消息通告的API服务")
@RequestMapping(path = "bulletin", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BulletinController extends BaseEntityController<Bulletin, BulletinDto>
        implements BulletinApi {
    @Autowired
    private BulletinService service;
    @Override
    public BaseEntityService<Bulletin> getService() {
        return service;
    }

    /**
     * 保存消息通告
     *
     * @param bulletinDto 消息通告DTO
     * @return 操作结果
     */
    @Override
    public ResultData saveBulletin(BulletinDto bulletinDto) {
        // DTO转换为Entity
        Bulletin bulletin = convertToEntity(bulletinDto);
        // 执行业务逻辑
        OperateResult result;
        try {
            result = service.saveBulletin(bulletin, bulletinDto.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error("保存消息通告异常！", e);
            // 保存消息通告异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00008", e.getMessage()));
        }
        return ResultDataUtil.convertFromOperateResult(result);
    }

    /**
     * 发布通告
     *
     * @param ids 通告Id清单
     * @return 业务处理结果
     */
    @Override
    public ResultData releaseBulletin(Set<String> ids) {
        // 执行业务逻辑
        OperateResult result;
        try {
            result = service.releaseBulletin(ids);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error("发布通告异常！", e);
            // 发布通告异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00009", e.getMessage()));
        }
        return ResultDataUtil.convertFromOperateResult(result);
    }

    /**
     * 撤销通告
     *
     * @param ids 通告Id清单
     * @return 业务处理结果
     */
    @Override
    public ResultData cancelBulletin(Set<String> ids) {
        // 执行业务逻辑
        OperateResult result;
        try {
            result = service.cancelBulletin(ids);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error("撤销通告异常！", e);
            // 撤销通告异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00010", e.getMessage()));
        }
        return ResultDataUtil.convertFromOperateResult(result);
    }

    /**
     * 删除通告
     *
     * @param ids 通告Id清单
     * @return 业务处理结果
     */
    @Override
    public ResultData deleteBulletin(Set<String> ids) {
        // 执行业务逻辑
        OperateResult result;
        try {
            result = service.deleteBulletin(ids);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error("删除通告异常！", e);
            // 删除通告异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00011", e.getMessage()));
        }
        return ResultDataUtil.convertFromOperateResult(result);
    }

    /**
     * 查看通告
     *
     * @param id 通告Id
     * @return 获取一个通告
     */
    @Override
    public ResultData<BulletinDto> getBulletin(String id) {
        if (StringUtils.isBlank(id)) {
            // 查看通告id不能为空!
            return ResultData.fail(ContextUtil.getMessage("00007"));
        }
        // 执行业务逻辑
        BulletinCompose compose;
        try {
            compose = service.getBulletin(id);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error("查看通告异常！", e);
            // 查看通告异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00012", e.getMessage()));
        }
        BulletinDto dto = convertToDto(compose.getBulletin());
        dto.setContent(compose.getContent());
        return ResultData.success(dto);
    }

    /**
     * 分页查询业务实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<BulletinDto>> findByPage(Search search) {
        return convertToDtoPageResult(service.findByPage(search));
    }
}

package com.changhong.sei.notify.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageResult;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.service.bo.OperateResult;
import com.changhong.sei.core.service.bo.OperateResultWithData;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.notify.api.BulletinApi;
import com.changhong.sei.notify.dto.BulletinDto;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.dto.OrganizationDto;
import com.changhong.sei.notify.entity.Bulletin;
import com.changhong.sei.notify.entity.Message;
import com.changhong.sei.notify.entity.compose.BulletinCompose;
import com.changhong.sei.notify.service.BulletinService;
import com.changhong.sei.notify.service.cust.BasicIntegration;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
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
@RequestMapping(path = "bulletin", produces = MediaType.APPLICATION_JSON_VALUE)
public class BulletinController extends BaseEntityController<Bulletin, BulletinDto>
        implements BulletinApi {
    @Autowired
    private BulletinService service;
    @Autowired
    private BasicIntegration basicIntegration;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseEntityService<Bulletin> getService() {
        return service;
    }

    /**
     * 分页查询消息通告实体
     *
     * @param search 查询参数
     * @return 分页查询结果
     */
    @Override
    public ResultData<PageResult<BulletinDto>> findByPage(Search search) {
        if (Objects.isNull(search)) {
            search = Search.createSearch();
        }
        PageResult<Bulletin> bulletinPageResult = service.findByPage(search);

        return convertToDtoPageResult(bulletinPageResult);
    }

    /**
     * 保存消息通告
     *
     * @param bulletinDto 消息通告DTO
     * @return 操作结果
     */
    @Override
    public ResultData<String> saveBulletin(BulletinDto bulletinDto) {
        // DTO转换为Entity
        Bulletin bulletin = convertToEntity(bulletinDto);
        // 执行业务逻辑
        OperateResultWithData<Bulletin> result;
        try {
            Message message = new Message();
            message.setId(bulletinDto.getMsgId());
            message.setCategory(NotifyType.SEI_BULLETIN);
            message.setSubject(bulletinDto.getSubject());
            message.setContentId(bulletinDto.getContentId());
            message.setContent(bulletinDto.getContent());
            message.setPriority(bulletinDto.getPriority());
            message.setTargetType(bulletinDto.getTargetType());
            message.setTargetValue(bulletinDto.getTargetValue());
            message.setTargetName(bulletinDto.getTargetName());
            message.setDocIds(bulletinDto.getDocIds());

            result = service.saveBulletin(bulletin, message);
        } catch (Exception e) {
            LogUtil.error("保存消息通告异常！", e);
            // 保存消息通告异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00008", e.getMessage()));
        }
        return ResultDataUtil.convertFromOperateResult(result, "ok");
    }

    /**
     * 发布通告
     *
     * @param ids 通告Id清单
     * @return 业务处理结果
     */
    @Override
    public ResultData<String> releaseBulletin(Set<String> ids) {
        // 执行业务逻辑
        OperateResult result;
        try {
            result = service.releaseBulletin(ids);
        } catch (Exception e) {
            LogUtil.error("发布通告异常！", e);
            // 发布通告异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00009", e.getMessage()));
        }
        return ResultDataUtil.convertFromOperateResult(result);
    }

    /**
     * 发布通告
     *
     * @param bulletinDto 消息通告DTO
     * @return 业务处理结果
     */
    @Override
    public ResultData<String> sendBulletin(BulletinDto bulletinDto) {
        return service.sendBulletin(bulletinDto);
    }

    /**
     * 撤销通告
     *
     * @param ids 通告Id清单
     * @return 业务处理结果
     */
    @Override
    public ResultData<String> cancelBulletin(Set<String> ids) {
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
    public ResultData<String> deleteBulletin(Set<String> ids) {
        // 执行业务逻辑
        OperateResult result;
        try {
            result = service.deleteBulletin(ids);
        } catch (Exception e) {
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
        ResultData<BulletinCompose> resultData;
        try {
            resultData = service.getBulletin(id);
            if (resultData.successful()) {
                BulletinCompose compose = resultData.getData();

                BulletinDto dto = modelMapper.map(compose.getMessage(), BulletinDto.class);
                Bulletin bulletin = compose.getBulletin();
                dto.setId(bulletin.getId());
                dto.setMsgId(bulletin.getMsgId());
                dto.setInvalidDate(bulletin.getInvalidDate());
                dto.setEffectiveDate(bulletin.getEffectiveDate());
                dto.setCancelUserAccount(bulletin.getCancelUserAccount());
                dto.setCancelUserName(bulletin.getCancelUserName());
                dto.setCancelDate(bulletin.getCancelDate());
                return ResultData.success(dto);
            } else {
                return ResultData.fail(resultData.getMessage());
            }
        } catch (Exception e) {
            LogUtil.error("查看通告异常！", e);
            // 查看通告异常！{0}
            return ResultData.fail(ContextUtil.getMessage("00012", e.getMessage()));
        }
    }

    /**
     * 获取当前用户有权限的树形组织实体清单
     *
     * @param featureCode 功能项代码
     * @return 有权限的树形组织实体清单
     */
    @Override
    public ResultData<List<OrganizationDto>> getUserAuthorizedTreeOrg(String featureCode) {
        ResultData<List<OrganizationDto>> resultData = basicIntegration.getUserAuthorizedTreeEntities(featureCode);
        return resultData;
    }
}

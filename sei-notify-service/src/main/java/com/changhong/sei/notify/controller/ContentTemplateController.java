package com.changhong.sei.notify.controller;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.controller.BaseEntityController;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.service.BaseEntityService;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.notify.api.ContentTemplateApi;
import com.changhong.sei.notify.dto.ContentParams;
import com.changhong.sei.notify.dto.ContentTemplateDto;
import com.changhong.sei.notify.entity.ContentTemplate;
import com.changhong.sei.notify.manager.ContentBuilder;
import com.changhong.sei.notify.service.ContentTemplateService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <strong>实现功能:</strong>
 * <p>内容模板服务实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 16:01
 */
@RestController
@Api(value = "ContentTemplateApi", tags = "内容模板API接口")
@RequestMapping(path = "contentTemplate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ContentTemplateController
        extends BaseEntityController<ContentTemplate, ContentTemplateDto>
        implements ContentTemplateApi {
    @Autowired
    private ContentTemplateService service;
    @Autowired
    private ContentBuilder contentBuilder;

    /**
     * 定义没有内容的模板转换
     */
    private static final ModelMapper templateModelMapper;
    static {
        templateModelMapper = new ModelMapper();
        // 自定义规则
        PropertyMap<ContentTemplate, ContentTemplateDto> propertyMap = new PropertyMap<ContentTemplate, ContentTemplateDto>() {

            /**
             * Called by ModelMapper to configure mappings as defined in the PropertyMap.
             */
            @Override
            protected void configure() {
                skip(destination.getContent());
            }
        };
        templateModelMapper.addMappings(propertyMap);
    }

    @Override
    public BaseEntityService<ContentTemplate> getService() {
        return service;
    }

    /**
     * 检查输入的DTO参数是否有效
     *
     * @param dto 数据传输对象
     * @return 检查结果
     */
    @Override
    public ResultData<?> checkDto(ContentTemplateDto dto) {
        if (Objects.isNull(dto)) {
            return ResultData.fail("输入的内容模板为空，禁止保存！");
        }
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ContentTemplateDto>> cvs = validator.validate(dto);
        StringBuilder msg = new StringBuilder();
        cvs.forEach(cv -> msg.append(cv.getPropertyPath()).append(cv.getMessage()).append("！"));
        if (CollectionUtils.isNotEmpty(cvs)){
            return ResultData.fail(msg.toString());
        }
        return super.checkDto(dto);
    }

    /**
     * 通过代码获取内容模板
     *
     * @param code 代码
     * @return 内容模板
     */
    @Override
    public ResultData<ContentTemplateDto> findByCode(String code) {
        ContentTemplate template;
        try {
            template = service.findByCode(code);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.fail("通过代码获取内容模板，发生异常！" + e.getMessage());
        }
        return ResultData.success(convertToDto(template));
    }

    /**
     * 通过参数获取指定模板的内容
     *
     * @param params 参数
     * @return 模板的内容
     */
    @Override
    public ResultData<String> getContent(@Valid ContentParams params) {
        // 获取模板
        ContentTemplate template = service.findByCode(params.getTemplateCode());
        if (Objects.isNull(template)) {
            // 内容模板代码【{0}】不存在！
            return ResultDataUtil.fail("00020", params.getTemplateCode());
        }
        String content = contentBuilder.getContent(template, params.getParams());
        return ResultData.success(content);
    }

    /**
     * 获取所有业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<ContentTemplateDto>> findAll() {
        List<ContentTemplateDto> data;
        try {
            List<ContentTemplate> entities = getService().findAll();
            data = entities.stream().map(this::convertToDtoWithoutContent).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error("获取所有业务实体异常！", e);
            // 获取所有业务实体异常！{0}
            return ResultData.fail(ContextUtil.getMessage("core_service_00006", e.getMessage()));
        }
        return ResultData.success(data);
    }

    /**
     * 获取所有未冻结的业务实体
     *
     * @return 业务实体清单
     */
    @Override
    public ResultData<List<ContentTemplateDto>> findAllUnfrozen() {
        return ResultData.success(convertToDtos(service.findAllUnfrozen()));
    }

    /**
     * 将数据实体转换成DTO（不含内容属性）
     *
     * @param entity 业务实体
     * @return DTO
     */
    private ContentTemplateDto convertToDtoWithoutContent(ContentTemplate entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return templateModelMapper.map(entity, ContentTemplateDto.class);
    }
}

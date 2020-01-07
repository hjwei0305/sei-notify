package com.changhong.sei.notify.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.service.DefaultFindAllService;
import com.changhong.sei.notify.api.ContentTemplateService;
import com.changhong.sei.notify.dto.ContentTemplateDto;
import com.changhong.sei.notify.entity.ContentTemplate;
import com.changhong.sei.notify.manager.ContentTemplateManager;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <strong>实现功能:</strong>
 * <p>内容模板服务实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2019-12-23 16:01
 */
@Service
@Api(value = "ContentTemplateService", tags = "内容模板API接口")
public class ContentTemplateServiceImpl
        implements DefaultBaseEntityService<ContentTemplate, ContentTemplateDto>,
        DefaultFindAllService<ContentTemplate, ContentTemplateDto>,
        ContentTemplateService {
    @Autowired
    private ContentTemplateManager manager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseEntityManager<ContentTemplate> getManager() {
        return manager;
    }

    @Override
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    /**
     * 获取数据实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<ContentTemplate> getEntityClass() {
        return ContentTemplate.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<ContentTemplateDto> getDtoClass() {
        return ContentTemplateDto.class;
    }

    /**
     * 检查输入的DTO参数是否有效
     *
     * @param dto 数据传输对象
     * @return 检查结果
     */
    @Override
    public ResultData checkDto(ContentTemplateDto dto) {
        if (Objects.isNull(dto)) {
            return ResultData.fail("输入的内容模板为空，禁止保存！");
        }
        if (StringUtils.isBlank(dto.getCode())) {
            return ResultData.fail("输入的内容模板代码为空，禁止保存！");
        }
        if (StringUtils.isBlank(dto.getName())) {
            return ResultData.fail("输入的内容模板名称为空，禁止保存！");
        }
        if (StringUtils.isBlank(dto.getContent())) {
            return ResultData.fail("输入模板的内容为空，禁止保存！");
        }
        return DefaultBaseEntityService.super.checkDto(dto);
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
            template = manager.findByCode(code);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.fail("通过代码获取内容模板，发生异常！" + e.getMessage());
        }
        return ResultData.success(convertToDto(template));
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
            List<ContentTemplate> entities = getManager().findAll();
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
     * 将数据实体转换成DTO（不含内容属性）
     *
     * @param entity 业务实体
     * @return DTO
     */
    private ContentTemplateDto convertToDtoWithoutContent(ContentTemplate entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        // 自定义规则
        ModelMapper custModelMapper = new ModelMapper();
        PropertyMap<ContentTemplate, ContentTemplateDto> propertyMap = new PropertyMap<ContentTemplate, ContentTemplateDto>() {

            /**
             * Called by ModelMapper to configure mappings as defined in the PropertyMap.
             */
            @Override
            protected void configure() {
                skip(destination.getContent());
            }
        };
        custModelMapper.addMappings(propertyMap);
        return custModelMapper.map(entity, ContentTemplateDto.class);
    }
}

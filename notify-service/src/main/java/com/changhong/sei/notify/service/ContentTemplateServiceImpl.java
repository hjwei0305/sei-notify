package com.changhong.sei.notify.service;

import com.changhong.sei.core.cache.CacheUtil;
import com.changhong.sei.notify.api.ContentTemplateService;
import com.changhong.sei.notify.dto.ResultData;
import com.changhong.sei.notify.dto.ContentTemplateDto;
import com.changhong.sei.notify.entity.ContentTemplate;
import com.changhong.sei.notify.manager.ContentTemplateManager;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
public class ContentTemplateServiceImpl implements ContentTemplateService {
    @Autowired
    private ContentTemplateManager manager;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CacheUtil<String,Object> cacheUtil;

    /**
     * 获取所有数据
     *
     * @return 应用模块清单
     */
    @Override
    @Cacheable(cacheNames = {"ContentTemplate"})
    public ResultData<List<ContentTemplateDto>> findAll() {
        List<ContentTemplate> templates = manager.findAll();
        // 转换为DTO
        // ModelMapper listMapper = new ModelMapper();
        // List<ContentTemplateDto> data = listMapper.map(templates, new TypeToken<List<ContentTemplateDto>>(){}.getType());
        List<ContentTemplateDto> data = templates.stream().map(this::convertToDtoWithoutContent).collect(Collectors.toList());
        return ResultData.success(data);
    }

    /**
     * 保存内容模板
     *
     * @param contentTemplateDto 内容模板DTO
     * @return 操作结果
     */
    @Override
    @CacheEvict(cacheNames = {"ContentTemplate"})
    public ResultData<ContentTemplateDto> save(ContentTemplateDto contentTemplateDto) {
        if (Objects.isNull(contentTemplateDto)){
            return ResultData.fail("输入的内容模板为空，禁止保存！");
        }
        if (StringUtils.isBlank(contentTemplateDto.getCode())){
            return ResultData.fail("输入的内容模板代码为空，禁止保存！");
        }
        if (StringUtils.isBlank(contentTemplateDto.getName())){
            return ResultData.fail("输入的内容模板名称为空，禁止保存！");
        }
        if (StringUtils.isBlank(contentTemplateDto.getContent())){
            return ResultData.fail("输入模板的内容为空，禁止保存！");
        }
        ContentTemplate template;
        try {
            template = manager.save(convertToEntity(contentTemplateDto));
            cacheUtil.set("ContentTemplate:"+contentTemplateDto.getCode(),convertToEntity(contentTemplateDto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.fail("保存内容模板发生异常！"+e.getMessage());
        }
        return ResultData.success(convertToDto(template));
    }

    /**
     * 通过Id获取内容模板
     *
     * @param id Id标识
     * @return 内容模板
     */
    @Override
    @Cacheable(cacheNames = {"ContentTemplate"})
    public ResultData<ContentTemplateDto> findOne(String id) {
        ContentTemplate template;
        try {
            template = manager.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.fail("通过Id获取内容模板，发生异常！"+e.getMessage());
        }
        return ResultData.success(convertToDto(template));
    }

    /**
     * 通过代码获取内容模板
     *
     * @param code 代码
     * @return 内容模板
     */
    @Override
    @Cacheable(cacheNames = {"ContentTemplate"})
    public ResultData<ContentTemplateDto> findByCode(String code) {
        ContentTemplate template;
        try {
            template = manager.findByCode(code);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.fail("通过代码获取内容模板，发生异常！"+e.getMessage());
        }
        return ResultData.success(convertToDto(template));
    }

    /**
     * 将数据实体转换成DTO（不含内容属性）
     * @param entity 业务实体
     * @return DTO
     */
    private ContentTemplateDto convertToDtoWithoutContent(ContentTemplate entity){
        if (Objects.isNull(entity)){
            return null;
        }
        // 自定义规则
        ModelMapper custModelMapper = new ModelMapper();
        PropertyMap<ContentTemplate,ContentTemplateDto> propertyMap = new PropertyMap<ContentTemplate,ContentTemplateDto>() {

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

    /**
     * 将数据实体转换成DTO
     * @param entity 业务实体
     * @return DTO
     */
    private ContentTemplateDto convertToDto(ContentTemplate entity){
        if (Objects.isNull(entity)){
            return null;
        }
        return modelMapper.map(entity, ContentTemplateDto.class);
    }

    /**
     * 将DTO转换成数据实体
     * @param dto 业务实体
     * @return 数据实体
     */
    private ContentTemplate convertToEntity(ContentTemplateDto dto){
        if (Objects.isNull(dto)){
            return null;
        }
        return modelMapper.map(dto, ContentTemplate.class);
    }
}

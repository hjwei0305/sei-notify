package com.changhong.sei.notify.service;

import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.LogUtil;
import com.changhong.sei.core.manager.BaseEntityManager;
import com.changhong.sei.core.manager.bo.OperateResult;
import com.changhong.sei.core.service.DefaultBaseEntityService;
import com.changhong.sei.core.service.DefaultFindByPageService;
import com.changhong.sei.core.utils.ResultDataUtil;
import com.changhong.sei.notify.api.BulletinService;
import com.changhong.sei.notify.dto.BulletinDto;
import com.changhong.sei.notify.entity.Bulletin;
import com.changhong.sei.notify.entity.compose.BulletinCompose;
import com.changhong.sei.notify.manager.BulletinManager;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <strong>实现功能:</strong>
 * <p>维护消息通告的服务实现</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2020-01-15 15:03
 */
@Service
@Api(value = "BulletinService", tags = "维护消息通告的API服务")
public class BulletinServiceImpl implements DefaultBaseEntityService<Bulletin, BulletinDto>
        , DefaultFindByPageService<Bulletin, BulletinDto>
        , BulletinService {
    @Autowired
    private BulletinManager manager;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public BaseEntityManager<Bulletin> getManager() {
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
    public Class<Bulletin> getEntityClass() {
        return Bulletin.class;
    }

    /**
     * 获取传输实体的类型
     *
     * @return 类型Class
     */
    @Override
    public Class<BulletinDto> getDtoClass() {
        return BulletinDto.class;
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
            result = manager.saveBulletin(bulletin, bulletinDto.getContent());
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
            result = manager.releaseBulletin(ids);
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
            result = manager.cancelBulletin(ids);
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
            result = manager.deleteBulletin(ids);
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
            compose = manager.getBulletin(id);
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
}

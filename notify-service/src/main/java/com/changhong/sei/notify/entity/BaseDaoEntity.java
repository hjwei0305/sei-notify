package com.changhong.sei.notify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Persistable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

/**
 * <strong>实现功能:</strong>
 * <p>数据实体基类</p>
 *
 * @author 王锦光 wangj
 * @version 1.0.1 2017-10-31 14:54
 */
//@MappedSuperclass
//public abstract class BaseDaoEntity implements Serializable, Persistable<String> {
//    /**
//     * Id标识
//     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idGenerator")
//    @GenericGenerator(name = "idGenerator", strategy = "uuid2",
//            parameters = {@Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
//    private String id;
//
//    /**
//     * Returns if the {@code Persistable} is new or was persisted already.
//     *
//     * @return if the object is new
//     */
//    @Override
//    @JsonIgnore
//    public boolean isNew() {
//        return Objects.isNull(id) || id.isEmpty();
//    }
//
//    @Override
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//}

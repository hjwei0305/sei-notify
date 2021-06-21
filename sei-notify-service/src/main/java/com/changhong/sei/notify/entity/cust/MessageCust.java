package com.changhong.sei.notify.entity.cust;

import com.changhong.sei.core.entity.BaseEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-06-21 15:16
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public class MessageCust extends BaseEntity {

}

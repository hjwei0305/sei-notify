package com.changhong.sei.notify.apitest.basic;

import io.swagger.annotations.ApiModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 实现功能: 公司DTO
 *
 * @author 王锦光 wangjg
 * @version 2020-01-26 16:17
 */
public class CorporationDto {
    /**
     * 代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 租户代码
     */
    private String tenantCode;

    /**
     * ERP公司代码
     */
    private String erpCode;

    /**
     * 排序
     */
    private Integer rank = 0;

    /**
     * 冻结标志
     */
    private Boolean frozen = Boolean.FALSE;

    /**
     * 本位币货币代码
     */
    private String baseCurrencyCode;

    /**
     * 本位币货币名称
     */
    private String baseCurrencyName;

    /**
     * 默认贸易伙伴代码
     */
    private String defaultTradePartner;

    /**
     * 关联交易贸易伙伴
     */
    private String relatedTradePartner;

    /**
     * 微信用户凭证
     */
    private String weixinAppid;

    /**
     * 微信用户凭证密钥
     */
    private String weixinSecret;

    /**
     * 内部供应商代码
     */
    private String internalSupplier;

    /**
     * 是否为模板公司
     */
    private Boolean templateSign = Boolean.FALSE;

    /**
     * 自定义扩展字段1
     */
    private String udf1;

    /**
     * 自定义扩展字段2
     */
    private String udf2;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getErpCode() {
        return erpCode;
    }

    public void setErpCode(String erpCode) {
        this.erpCode = erpCode;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public String getBaseCurrencyName() {
        return baseCurrencyName;
    }

    public void setBaseCurrencyName(String baseCurrencyName) {
        this.baseCurrencyName = baseCurrencyName;
    }

    public String getDefaultTradePartner() {
        return defaultTradePartner;
    }

    public void setDefaultTradePartner(String defaultTradePartner) {
        this.defaultTradePartner = defaultTradePartner;
    }

    public String getRelatedTradePartner() {
        return relatedTradePartner;
    }

    public void setRelatedTradePartner(String relatedTradePartner) {
        this.relatedTradePartner = relatedTradePartner;
    }

    public String getWeixinAppid() {
        return weixinAppid;
    }

    public void setWeixinAppid(String weixinAppid) {
        this.weixinAppid = weixinAppid;
    }

    public String getWeixinSecret() {
        return weixinSecret;
    }

    public void setWeixinSecret(String weixinSecret) {
        this.weixinSecret = weixinSecret;
    }

    public String getInternalSupplier() {
        return internalSupplier;
    }

    public void setInternalSupplier(String internalSupplier) {
        this.internalSupplier = internalSupplier;
    }

    public Boolean getTemplateSign() {
        return templateSign;
    }

    public void setTemplateSign(Boolean templateSign) {
        this.templateSign = templateSign;
    }

    public String getUdf1() {
        return udf1;
    }

    public void setUdf1(String udf1) {
        this.udf1 = udf1;
    }

    public String getUdf2() {
        return udf2;
    }

    public void setUdf2(String udf2) {
        this.udf2 = udf2;
    }
}

package com.changhong.sei.notify.dto;

import java.io.Serializable;

///**
// * <strong>实现功能:</strong>
// * <p>通用服务处理结果</p>
// *
// * @author 王锦光 wangj
// * @version 1.0.1 2019-12-19 15:42
// */
//public class ResultData<T> implements Serializable {
//    private final static String DEFAULT_SUCCESSFUL_MSG = "处理成功！";
//    /**
//     * 是成功的
//     */
//    private Boolean successful;
//    /**
//     * 返回信息
//     */
//    private String message;
//    /**
//     * 返回的数据
//     */
//    private T data;
//
//    /**
//     * 私有构造函数
//     */
//    private ResultData() {
//        this.successful = Boolean.FALSE;
//        this.message = "";
//        this.data = null;
//    }
//
//    /**
//     * 私有构造函数
//     */
//    private ResultData(Boolean successful, String message, T data) {
//        this.successful = successful;
//        this.message = message;
//        this.data = data;
//    }
//
//    public Boolean getSuccessful() {
//        return successful;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    /**
//     * 处理成功
//     * @param data 返回的数据对象
//     * @param <T> 数据对象类型
//     * @return 处理结果
//     */
//    public static <T> ResultData<T> success(T data){
//        return new ResultData<>(Boolean.TRUE, DEFAULT_SUCCESSFUL_MSG, data);
//    }
//
//    /**
//     * 处理成功
//     * @param message 成功的消息
//     * @param data 返回的数据对象
//     * @param <T> 数据对象类型
//     * @return 处理结果
//     */
//    public static <T> ResultData<T> success(String message, T data){
//        return new ResultData<>(Boolean.TRUE, message, data);
//    }
//
//    /**
//     * 处理失败
//     * @param message 失败的消息
//     * @return 处理结果
//     */
//    public static <T> ResultData<T> fail(String message){
//        return new ResultData<>(Boolean.FALSE, message, null);
//    }
//}

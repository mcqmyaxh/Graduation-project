package com.example.patient.util.exp;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用返回对象
 *
 * @author harry
 * @公众号 Harry技术
 */
@Data
@NoArgsConstructor
public class ResultData<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "响应状态码", example = "200",requiredMode = Schema.RequiredMode.REQUIRED)
    private long code;

    @Schema(description = "响应消息")
    private String message;

    @Schema(description = "响应数据")
    private T data;

    protected ResultData(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ResultData<T> success(T data) {
        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }
    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ResultData<T> success(int code,T data) {
        return new ResultData<>(code, ResultCode.SUCCESS.getMessage(), data);
    }


    /**.
     * 成功返回结果
     */
    public static <T> ResultData<T> success() {
        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> ResultData<T> success(T data, String message) {
        return new ResultData<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> ResultData<T> failed(ResultCode errorCode) {
        return new ResultData<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> ResultData<T> failed(String message) {
        return new ResultData<>(ResultCode.FAILED.getCode(), message, null);
    }
    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> ResultData<T> failed(long code,String message) {
        return new ResultData<>(code, message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> ResultData<T> failed(long code, String message,String msgDetail) {
        System.out.println("错误码: " + code + ", 错误信息: " + message + ", 错误详情: " + msgDetail);
        return (ResultData<T>) new ResultData<>(code, message, msgDetail);
    }

    /**
     * 失败返回结果
     */
    public static <T> ResultData<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> ResultData<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> ResultData<T> validateFailed(String message) {
        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> ResultData<T> unauthorized(T data) {
        return new ResultData<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> ResultData<T> forbidden(T data) {
        System.out.println("data"+data);
        return new ResultData<>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

}


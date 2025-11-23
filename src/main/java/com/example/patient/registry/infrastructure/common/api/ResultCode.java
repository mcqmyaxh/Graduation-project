package com.example.patient.registry.infrastructure.common.api;

/**
 * 枚举了一些常用API操作码
 *
 * @author harry
 * @公众号 Harry技术
 */
public enum ResultCode implements IErrorCode {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),

    VALIDATE_FAILED(400, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已失效"),
    FORBIDDEN(403, "没有相关权限111"),
    NOT_FOUND(404, "请求资源不存在或请求路径错误"),
    ;


    private final long code;

    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}


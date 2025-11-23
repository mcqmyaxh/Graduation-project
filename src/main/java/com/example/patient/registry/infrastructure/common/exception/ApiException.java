package com.example.patient.registry.infrastructure.common.exception;

import com.example.patient.registry.infrastructure.common.api.IErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 自定义异常
 *
 * @author harry
 * @公众号 Harry技术
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private String msg;

    private long code = 500;

    public ApiException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ApiException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public ApiException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ApiException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public ApiException(IErrorCode exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMessage();
    }

}


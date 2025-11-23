package com.example.patient.registry.infrastructure.common.annotation;

import com.example.patient.registry.infrastructure.common.enums.BusinessType;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author harry
 * @公众号 Harry技术
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 模块
     */
    String title() default "";

    /**
     * 功能
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

}


package com.example.patient.registry.infrastructure.common.annotation;

import java.lang.annotation.*;

/**
 * 数据权限注解
 *
 * @author harry
 * @公众号 Harry技术
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    String deptAlias() default "";

    String deptIdColumnName() default "dept_id";

    String userAlias() default "";

    String userIdColumnName() default "user_id";
}


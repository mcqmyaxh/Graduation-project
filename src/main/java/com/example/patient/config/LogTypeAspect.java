package com.example.patient.config;

import com.example.patient.util.exp.ResultData;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LogTypeAspect {

    // 拦截所有 controller 包下的方法
    @Pointcut("execution(* com.example.patient.*.controller..*(..))")
    public void controllerLog() {}
    @Pointcut("execution(* com.example.patient.registry.infrastructure.common.exception.ApiExceptionHandler.handleApiException(..))")
    public void exceptionLog() {}


//    @Before("controllerLog()||exceptionLog()")
    @Before("controllerLog()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String uri = request.getRequestURI();
            String method = request.getMethod();

            // 设置 MDC 用于 Loki 标签
            MDC.put("log_file_type", getLogType(joinPoint));
            System.out.println("MDC: " + MDC.get("log_file_type"));

            // 可选：记录入口日志
             log.info("IN uri={} method={}", uri, method);
        }
    }

    @AfterReturning(pointcut = "controllerLog()||exceptionLog()", returning = "result")
//@AfterReturning(pointcut = "controllerLog()", returning = "result")
    public void afterControllerMethod(JoinPoint joinPoint, Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String uri = request.getRequestURI();
            String method = request.getMethod();

            long code = extractCodeFromResult(result);  // 从返回值中提取 code

            long startTime = System.currentTimeMillis(); // 可选：记录耗时
            // 如果你记录了开始时间，可以计算耗时（略）

            // 打印结构化日志供 Loki 解析
            log.info("OUT uri={} method={} code={}", uri, method, code);

        }

        MDC.clear(); // 清除 MDC
    }

    /**
     * 根据类名决定 logType
     */
    private String getLogType(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        if (className.contains("H5Controller")) {
            return "H5ControllerLog";
        } else if (className.contains("testController")) {
            return "testControllerLog";
        } else if (className.contains("PlatformController")) {
            return "PlatformControllerLog";
        } else {
            return "business";
        }
    }

    /**
     * 从返回值中提取 code（根据你的 Result 类结构修改）
     */
    private long extractCodeFromResult(Object result) {
        if (result instanceof ResultData ) {
            ResultData <?> res = (ResultData <?>) result;
            System.out.println("result: " + result);
            return res.getCode();
        }
        // 默认成功
        return 200;
    }
}
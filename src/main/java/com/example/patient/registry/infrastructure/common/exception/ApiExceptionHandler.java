package com.example.patient.registry.infrastructure.common.exception;


import com.example.patient.registry.infrastructure.common.api.ResultCode;
import com.example.patient.util.exp.ResultData;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
/**
 * 异常处理器
 *
 * @author harry
 * @公众号 Harry技术
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    // 替换为你的项目包路径（如：com.yourcompany）
    private static final String APPLICATION_PACKAGE = "com.example";

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public <T> ResultData<T> noHandlerFoundException(NoHandlerFoundException e) {
        log.error("请求路径错误 抛出:", e);
        return ResultData.failed(ResultCode.NOT_FOUND.getMessage());

    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public <T> ResultData<T> handleAuthorizationDeniedException(AccessDeniedException e) {
        log.error("权限不足 抛出:", e);
        return ResultData.failed(ResultCode.FORBIDDEN.getMessage());
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ApiException.class)
    public <T> ResultData<T> handleApiException(ApiException e) {
        log.error("自定义ApiException 抛出:", e);
        String mathName=getExceptionMethodName(e);
        System.out.println(mathName);
        return ResultData.failed(e.getCode(), e.getMsg(), mathName);
    }

//    @ExceptionHandler(Exception.class)
//    public <T> ResultData<T> handleException(Exception e) {
//        log.error("进入的是这个方法");
//        log.error(e.getMessage(), e);
//        String mathName=getExceptionMethodName(ex);
//        System.out.println(mathName);
//        return ResultData.failed(599,e.getMessage());
//    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("校验失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = sb.toString();
        String mathName=getExceptionMethodName(ex);
        System.out.println(mathName);
        return ResultData.failed(555,msg,mathName);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultData handleConstraintViolationException(ConstraintViolationException ex) {
        String mathName=getExceptionMethodName(ex);
        System.out.println(mathName);
        return ResultData.failed(555,ex.getMessage(),mathName);
    }
    private String getExceptionMethodName(Throwable e) {
//        Integer Start
        // 1. 优先检查原始异常（处理包装异常如RuntimeException）
        Throwable rootCause = e;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }

        // 2. 遍历堆栈，找到第一个属于项目包路径的方法
        for (StackTraceElement element : rootCause.getStackTrace()) {
            String className = element.getClassName();
            // 过滤框架包（Spring、JDK、Tomcat等）
            if (className.startsWith(APPLICATION_PACKAGE) &&
                    !className.contains("$") && // 排除Lambda表达式
                    !className.contains("EnhancerBySpringCGLIB")) { // 排除Spring代理类

                return className + "." + element.getMethodName() + "()";
            }
        }

        // 3. 未找到业务方法时返回未知
        return "UnknownMethod";
    }
}

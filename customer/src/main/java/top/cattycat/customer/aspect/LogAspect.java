package top.cattycat.customer.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.cattycat.common.pojo.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;

/**
 * 设置跨域响应头切面
 *
 * @author 王金义
 * @date 2021/01/05
 */
@Aspect
@Component

public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* top.cattycat.customer.controller.Controller.*(..))")
    public void all() {
    }

    @Around("all()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object[] args = joinPoint.getArgs();
        final ResponseResult proceed = (ResponseResult) joinPoint.proceed();
        logger.info("方法名: {}", joinPoint.getSignature().getName());
        logger.info("处理结果: {}", proceed.getMessage());
        return proceed;
    }
}

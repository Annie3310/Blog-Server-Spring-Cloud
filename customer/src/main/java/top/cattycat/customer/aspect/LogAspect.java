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

    @Pointcut("execution(* top.cattycat.customer.controller.Controller.search(..))")
    public void search() {}

    @Around("all()")
    public Object setCrossOriginResponseHeader(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object[] args = joinPoint.getArgs();
        final ResponseResult proceed = (ResponseResult) joinPoint.proceed();
        logger.info("方法名: {}", joinPoint.getSignature().getName());
        logger.info("处理结果: {}", proceed.getMessage());
        return proceed;
    }

//    @Before("search()")
    public void searchLimit() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        final HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        final String id = request.getSession().getId();
        //使用 Redis 分布式锁限制搜索次数 (需要买 Redis)
    }
}

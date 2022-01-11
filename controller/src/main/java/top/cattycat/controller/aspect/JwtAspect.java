package top.cattycat.controller.aspect;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.auth.AUTH;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.cattycat.common.enums.ResponseEnum;
import top.cattycat.common.exception.BlogException;

import javax.servlet.http.HttpServletRequest;

/**
 * verify token aspect
 * @author 王金义
 * @date 2022/1/10
 */
@Aspect
@Component
@Order(1)
public class JwtAspect {
    private final StringRedisTemplate stringRedisTemplate;

    public JwtAspect(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Pointcut("execution(* top.cattycat.controller.controller.BlogController.search(..))")
    void search() {}

    /**
     * Restrict searches to logged-in users
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("search()")
    public Object verify(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取request
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            throw new BlogException(ResponseEnum.AUTHORIZATION_FAILED);
        } else {
            return joinPoint.proceed();
        }
    }
}

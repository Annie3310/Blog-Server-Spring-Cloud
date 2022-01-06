package top.cattycat.controller.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.cattycat.common.enums.ResponseEnum;
import top.cattycat.common.exception.BlogException;
import top.cattycat.common.pojo.response.ResponseResult;
import top.cattycat.common.pojo.vo.SearchVO;
import top.cattycat.controller.config.BlogConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 搜索次数限制，暂时使用 Session 来作为用户标识
 * @author 王金义
 * @date 2022/1/6
 */
@Aspect
@Component
public class SearchLimitAspect {
    private static final Logger logger = LoggerFactory.getLogger(SearchLimitAspect.class);

    private final StringRedisTemplate redisTemplate;
    private final BlogConfig blogConfig;
    private final Long DEFAULT_SEARCH_LIMIT = 5L;

    public SearchLimitAspect(StringRedisTemplate redisTemplate, BlogConfig blogConfig) {
        this.redisTemplate = redisTemplate;
        this.blogConfig = blogConfig;
    }

    @Pointcut("execution(* top.cattycat.controller.controller.BlogController.search(..))")
    public void search(){}

    @Around("search()")
    public ResponseResult<List<SearchVO>> searchLimit(ProceedingJoinPoint joinPoint) {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        final HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        final String ip = this.getIP(request);
        logger.info("ip: {}", ip);

        // nacos 配置文件中关于搜索限制的配置
        final BlogConfig.SearchLimit searchLimit = this.blogConfig.getSearchLimit();

        if (Objects.isNull(searchLimit)) {
            logger.warn("配置文件 searchLimit 取值为 null");
            throw new BlogException(ResponseEnum.SERVER_ERROR);
        }
        ValueOperations<String, String> redisValueOps = this.redisTemplate.opsForValue();
        final String searchAmount = redisValueOps.get(ip);
        // 没有该 key 的情况
        if (Objects.isNull(searchAmount)) {
            redisValueOps.set(ip, "1", 1, TimeUnit.MINUTES);
            return this.doSearch(joinPoint);
        }
        Long limit = this.blogConfig.getSearchLimit().getLimit();
        if (Objects.isNull(limit)) {
            limit = this.DEFAULT_SEARCH_LIMIT;
        }
        final long longSearchAmount = Long.parseLong(searchAmount);
        // 搜索小于等于 5 次
        if (longSearchAmount <= limit) {
            redisValueOps.increment(ip);
            return this.doSearch(joinPoint);
        } else {
            // 大于 5 次
            logger.warn("用户 {} 搜索次数过多", ip);
            throw new BlogException(ResponseEnum.TOO_MANY_REQUEST);
        }
    }

    private ResponseResult<List<SearchVO>> doSearch(ProceedingJoinPoint joinPoint) {
        ResponseResult<List<SearchVO>> proceed = null;
        try {
            proceed = (ResponseResult<List<SearchVO>>) joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }

    public String getIP(HttpServletRequest request){
        String ip=request.getHeader("x-forwarded-for");
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("Proxy-Client-IP");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("X-Real-IP");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getRemoteAddr();
        }
        return ip;
    }
}

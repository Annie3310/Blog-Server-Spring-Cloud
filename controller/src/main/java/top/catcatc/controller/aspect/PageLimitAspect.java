package top.catcatc.controller.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.catcatc.common.pojo.request.PageParam;

import java.util.Arrays;
import java.util.Objects;

/**
 * 自动分页 limit 切面
 * @author 王金义
 * @date 2021/11/15
 */
@Aspect
@Component
public class PageLimitAspect {
    @Value("${blog.page.limit}")
    private Integer limit;

    @Pointcut("args(top.catcatc.common.pojo.request.PageParam, ..)")
    public void pageParam() {}
    @Pointcut("execution(* top.catcatc.controller.controller.BlogController.*(..))")
    public void method() {}
    @Pointcut("pageParam() && method()")
    public void point() {}

    @Before("point()")
    public void setLimit(JoinPoint joinPoint) {
        final Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(o -> {
            // 参数类型为 PageParam
            if (o instanceof PageParam) {
                // 传入的 limit 为 null
                if (Objects.isNull(((PageParam) o).getLimit())) {
                    // 配置文件中的 limit 不为 null
                    if (Objects.nonNull(this.limit)) {
                        ((PageParam) o).setLimit(this.limit);
                    } else {
                        // 如果为 null, 则强行传入 20
                        ((PageParam) o).setLimit(20);
                    }
                }
            }
        });
    }

}

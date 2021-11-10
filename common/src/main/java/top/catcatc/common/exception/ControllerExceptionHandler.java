package top.catcatc.common.exception;

import top.catcatc.common.enums.ResponseEnum;
import top.catcatc.common.pojo.response.PublicResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * 异常捕获器
 *
 * @author 王金义
 * @date 2021/8/30
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(BlogException.class)
    public PublicResponse handleBlogException(BlogException blogException) {
        return PublicResponse.exception(blogException);
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public PublicResponse handleHttpClientErrorException() {
        return PublicResponse.error(ResponseEnum.TOO_MANY_REQUEST);
    }
}

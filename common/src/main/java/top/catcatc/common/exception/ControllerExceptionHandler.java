package top.catcatc.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import top.catcatc.common.assertion.Assertion;
import top.catcatc.common.enums.ResponseEnum;
import top.catcatc.common.pojo.response.PublicResponse;

/**
 * 异常捕获器
 *
 * @author 王金义
 * @date 2021/8/30
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(Assertion.class);
    @ExceptionHandler(BlogException.class)
    public PublicResponse handleBlogException(BlogException e) {
        e.printStackTrace();
        return PublicResponse.exception(e);
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public PublicResponse handleHttpClientErrorException(HttpClientErrorException e) {
        logger.warn(e.getMessage());
        return PublicResponse.error(ResponseEnum.TOO_MANY_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public PublicResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.warn(e.getAllErrors().get(0).getDefaultMessage());
        return PublicResponse.error(e.getAllErrors().get(0).getDefaultMessage());
    }
}

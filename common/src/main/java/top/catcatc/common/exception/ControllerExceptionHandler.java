package top.catcatc.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import top.catcatc.common.enums.ResponseEnum;
import top.catcatc.common.pojo.response.ResponseFactory;
import top.catcatc.common.pojo.response.ResponseResult;

import java.util.Arrays;

/**
 * 异常捕获器
 *
 * @author 王金义
 * @date 2021/8/30
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    @ExceptionHandler(BlogException.class)
    public ResponseResult<BlogException> handleBlogException(BlogException e) {
        logger.warn(e.getResponseEnum().getMessage());
        logger.warn(Arrays.toString(e.getStackTrace()));
        return new ResponseFactory().exception(e);
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseResult handleHttpClientErrorException(HttpClientErrorException e) {
        logger.warn(e.getMessage());
        logger.warn(Arrays.toString(e.getStackTrace()));
        return new ResponseFactory().error(ResponseEnum.TOO_MANY_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.warn(e.getAllErrors().get(0).getDefaultMessage());
        logger.warn(Arrays.toString(e.getStackTrace()));
        return new ResponseFactory().error(e.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseResult handleIllegalArgumentException(IllegalArgumentException e) {
        final String message = e.getMessage();
        logger.warn(message);
        logger.warn(Arrays.toString(e.getStackTrace()));
        return new ResponseFactory().error(message);
    }
}

package top.cattycat.common.pojo.response;

import top.cattycat.common.enums.ResponseEnum;
import top.cattycat.common.exception.BlogException;

/**
 * @author 王金义
 * @date 2021/11/17
 */
public class ResponseFactory<T> {
    public ResponseResult<T> success(T o) {
        return new ResponseResult<T>()
                .setCode(ResponseEnum.SUCCESS.getCode())
                .setMessage(ResponseEnum.SUCCESS.getMessage())
                .setResult(o);
    }

    public ResponseResult success() {
        return new ResponseResult()
                .setCode(ResponseEnum.SUCCESS.getCode())
                .setMessage(ResponseEnum.SUCCESS.getMessage());
    }

    public ResponseResult exception(BlogException e) {
        return new ResponseResult()
                .setCode(e.getResponseEnum().getCode())
                .setMessage(e.getResponseEnum().getMessage());
    }

    public ResponseResult error(ResponseEnum error) {
        return new ResponseResult()
                .setCode(error.getCode())
                .setMessage(error.getMessage());
    }

    public ResponseResult error(String message) {
        return new ResponseResult()
                .setCode("B0001")
                .setMessage(message);
    }
}

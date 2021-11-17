package top.catcatc.common.pojo.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 通用返回体
 *
 * @author 王金义
 * @date 2021/8/30
 */
@Data
@Accessors(chain = true)
public class ResponseResult<T> {
    private String code;
    private String message;
    private T result;
}

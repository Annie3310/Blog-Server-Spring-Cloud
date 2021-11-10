package top.catcatc.common.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.catcatc.common.enums.ResponseEnum;
import top.catcatc.common.exception.BlogException;

/**
 * 通用返回体
 *
 * @author 王金义
 * @date 2021/8/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicResponse {
    private String code;
    private String message;
    private Object result;

    public static PublicResponse success(Object o) {
        final PublicResponse success = success();
        success.setResult(o);
        return success;
    }

    public static PublicResponse success() {
        return PublicResponse.builder()
                .code(ResponseEnum.SUCCESS.getCode())
                .message(ResponseEnum.SUCCESS.getMessage())
                .build();
    }
    public static PublicResponse exception(BlogException blogException) {
        return PublicResponse.builder()
                .code(blogException.getResponseEnum().getCode())
                .message(blogException.getResponseEnum().getMessage())
                .build();
    }
    public static PublicResponse error(ResponseEnum errorBody) {
        return PublicResponse.builder()
                .code(errorBody.getCode())
                .message(errorBody.getMessage())
                .build();
    }
}

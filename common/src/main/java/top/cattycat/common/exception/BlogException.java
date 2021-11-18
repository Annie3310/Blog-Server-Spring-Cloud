package top.cattycat.common.exception;

import lombok.*;
import top.cattycat.common.enums.ResponseEnum;

/**
 * 自定义异常体
 *
 * @author 王金义
 * @date 2021/8/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogException extends RuntimeException{
    private ResponseEnum responseEnum;
}

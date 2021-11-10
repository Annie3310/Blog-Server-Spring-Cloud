package top.catcatc.common.pojo.blog.dto;
import lombok.*;

/**
 * @author 王金义
 * @date 2021/9/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class LabelDTO {
    private Long id;
    private String name;
    private String color;
    private String description;
}

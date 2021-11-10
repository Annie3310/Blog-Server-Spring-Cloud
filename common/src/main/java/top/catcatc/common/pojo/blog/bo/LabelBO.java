package top.catcatc.common.pojo.blog.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class LabelBO {
    @JsonProperty("l_id")
    @EqualsAndHashCode.Exclude
    private Long lId;
    private Long id;
    @JsonProperty("node_id")
    private String nodeId;
    private String url;
    private String name;
    private String color;
    private String description;
}

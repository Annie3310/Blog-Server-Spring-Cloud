package top.catcatc.common.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王金义
 * @date 2021/9/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabelWebHookRequestBody {
    private Long id;
    @JsonProperty("node_id")
    private String nodeId;
    private String url;
    private String name;
    private String color;
    private String description;
}

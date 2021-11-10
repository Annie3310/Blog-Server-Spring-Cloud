package top.catcatc.common.pojo.blog.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王金义
 * @date 2021/9/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabelsForBlogsBO {
    @JsonProperty("lfb_id")
    private Long lfbId;
    @JsonProperty("l_id")
    private Long lId;
    @JsonProperty("b_number")
    private String bNumber;
}

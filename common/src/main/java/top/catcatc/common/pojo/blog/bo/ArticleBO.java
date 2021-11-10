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
public class ArticleBO {
    @JsonProperty("a_id")
    private Long aId;
    private String body;
    private String toc;
    @JsonProperty("b_id")
    private String bNumber;
}

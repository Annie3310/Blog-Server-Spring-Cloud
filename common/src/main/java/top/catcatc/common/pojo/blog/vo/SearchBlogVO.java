package top.catcatc.common.pojo.blog.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 王金义
 * @date 2021/10/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchBlogVO {
    private String number;
    private String title;

    private String body;

    private String toc;

    private String state;

    private List<LabelVO> labels;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;
}

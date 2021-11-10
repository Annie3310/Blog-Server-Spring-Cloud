package top.catcatc.common.pojo.blog.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 王金义
 * @date 2021/9/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogBO {
    @JsonProperty("b_id")
    private Long bId;
    private String number;
    private String title;
    private String state;

    private List<LabelBO> labels;

    private Date createdAt;

    private Date updatedAt;
}

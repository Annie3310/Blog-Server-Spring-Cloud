package top.catcatc.common.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.catcatc.common.pojo.blog.bo.LabelBO;
import top.catcatc.common.pojo.blog.component.User;

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
public class IssueWebHookRequestBody extends PageParam{
    private String number;
    private String title;
    private User user;
    private List<LabelBO> labels;
    private String state;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    private String body;
    private String toc;
}

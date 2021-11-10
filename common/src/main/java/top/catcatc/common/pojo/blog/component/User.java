package top.catcatc.common.pojo.blog.component;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王金义
 * @date 2021/8/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String login;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("avatar_url")
    private String avatarUrl;
}

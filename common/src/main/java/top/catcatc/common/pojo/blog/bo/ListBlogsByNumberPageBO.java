package top.catcatc.common.pojo.blog.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通过标签获取文章列表请求参数
 *
 * @author 王金义
 * @date 2021/9/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListBlogsByNumberPageBO {
    private List<String> numbers;
    private Integer page;
    private Integer perPage;
}

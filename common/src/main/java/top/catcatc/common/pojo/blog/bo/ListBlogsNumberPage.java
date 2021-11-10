package top.catcatc.common.pojo.blog.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章列表页码参数
 *
 * @author 王金义
 * @date 2021/9/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListBlogsNumberPage {
    private Integer page;
    private Integer perPage;
}

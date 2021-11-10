package top.catcatc.common.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王金义
 * @date 2021/10/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogSearchRequest extends PageParam{
    private String keyword;
    private Integer page;
    private Integer perPage;
}

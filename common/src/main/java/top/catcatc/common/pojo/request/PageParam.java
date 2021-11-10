package top.catcatc.common.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 王金义
 * @date 2021/11/9
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageParam {
    private Integer page;
    private Integer limit;
}

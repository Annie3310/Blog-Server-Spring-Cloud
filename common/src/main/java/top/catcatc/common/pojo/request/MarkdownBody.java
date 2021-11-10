package top.catcatc.common.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王金义
 * @date 2021-09-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarkdownBody {
    String text;
}

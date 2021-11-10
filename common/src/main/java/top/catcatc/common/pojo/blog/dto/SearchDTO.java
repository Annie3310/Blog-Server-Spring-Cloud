package top.catcatc.common.pojo.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.catcatc.common.pojo.blog.vo.SearchBlogVO;

import java.util.List;

/**
 * @author 王金义
 * @date 2021/10/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDTO {
    @JsonProperty("total_count")
    Integer totalCount;
    @JsonProperty("incomplete_results")
    Boolean incompleteResults;
    List<SearchBlogVO> items;
}

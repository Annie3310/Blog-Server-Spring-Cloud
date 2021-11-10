package top.catcatc.common.pojo.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 王金义
 * @date 2021/9/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogDTO {
    private String number;
    private String title;

    private String body;

    private String toc;

    private String state;

    private List<LabelDTO> labels;

    private Date createdAt;

    private Date updatedAt;

    private String cover;
}

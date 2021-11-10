package top.catcatc.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 王金义
 * @date 2021/11/9
 */
@Data
@TableName("blog")
public class Blog {
    @JsonProperty("b_id")
    private Long bId;

    private String number;
    private String title;
    private String state;

    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;

    private String cover;
}

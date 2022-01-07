package top.cattycat.common.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 王金义
 * @date 2022/1/7
 */
@Data
@TableName("user")
public class User {
    private Long id;
    private Long gId;
    private Date registrationTime;
    private String nickname;
    private Date lastLoginTime;
    private String avatar;
}

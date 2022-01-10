package top.cattycat.mapper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.cattycat.common.pojo.entity.User;

/**
 * @author 王金义
 * @date 2022/1/10
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}

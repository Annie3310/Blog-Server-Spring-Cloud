package top.catcatc.mapper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.catcatc.common.pojo.entity.Blog;

/**
 * 查询操作
 *
 * @author 王金义
 * @date 2021/9/3
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

}

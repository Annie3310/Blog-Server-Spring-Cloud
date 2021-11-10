package top.catcatc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.catcatc.common.pojo.blog.bo.LabelBO;
import top.catcatc.common.pojo.blog.bo.ListBlogsByNumberPageBO;
import top.catcatc.common.pojo.blog.bo.ListBlogsNumberPage;
import top.catcatc.common.pojo.blog.dto.BlogDTO;
import top.catcatc.common.pojo.blog.dto.LabelDTO;
import top.catcatc.common.pojo.blog.dto.ListLabelsByNumberDTO;
import top.catcatc.common.pojo.blog.vo.LabelVO;
import top.catcatc.common.pojo.entity.Blog;
import top.catcatc.common.pojo.request.SetCoverRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 查询操作
 *
 * @author 王金义
 * @date 2021/9/3
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

}

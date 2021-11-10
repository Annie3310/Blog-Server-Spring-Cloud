package top.catcatc.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.catcatc.blog.mapper.BlogMapper;
import top.catcatc.blog.common.service.BlogService;
import top.catcatc.common.pojo.entity.Blog;

/**
 * @author 王金义
 * @date 2021/11/9
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
}

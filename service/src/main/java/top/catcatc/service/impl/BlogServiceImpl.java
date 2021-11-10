package top.catcatc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.catcatc.mapper.mapper.BlogMapper;
import top.catcatc.service.BlogService;
import top.catcatc.common.pojo.entity.Blog;

/**
 * @author 王金义
 * @date 2021/11/9
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
}

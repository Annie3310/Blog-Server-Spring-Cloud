package top.catcatc.blog.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.catcatc.blog.common.service.ArticleService;
import top.catcatc.blog.mapper.ArticleMapper;
import top.catcatc.common.pojo.entity.Article;

/**
 * @author 王金义
 * @date 2021/11/9
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}

package top.catcatc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.catcatc.blog.common.service.impl.ArticleServiceImpl;
import top.catcatc.blog.common.service.impl.BlogServiceImpl;
import top.catcatc.blog.common.service.impl.LabelServiceImpl;
import top.catcatc.blog.common.service.impl.LabelsForArticlesServiceImpl;
import top.catcatc.blog.service.RequestService;
import top.catcatc.common.assertion.Assertion;
import top.catcatc.common.enums.ResponseEnum;
import top.catcatc.common.exception.BlogException;
import top.catcatc.common.pojo.Converter;
import top.catcatc.common.pojo.blog.dto.LabelsForArticlesDTO;
import top.catcatc.common.pojo.blog.vo.BlogVO;
import top.catcatc.common.pojo.blog.vo.LabelVO;
import top.catcatc.common.pojo.entity.Article;
import top.catcatc.common.pojo.entity.Blog;
import top.catcatc.common.pojo.entity.Label;
import top.catcatc.common.pojo.entity.LabelsForArticles;
import top.catcatc.common.pojo.request.BlogSearchRequest;
import top.catcatc.common.pojo.request.PageParam;
import top.catcatc.common.pojo.request.SetCoverRequest;
import top.catcatc.common.pojo.response.PublicResponse;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 王金义
 * @date 2021/11/9
 */
@Service
public class RequestServiceImpl implements RequestService {
    @Value("${blog.page.limit}")
    private Integer limit;

    final private String BLOG_STATE_OPEN = "open";

    final private BlogServiceImpl blogService;
    final private ArticleServiceImpl articleService;
    final private LabelServiceImpl labelService;
    final private LabelsForArticlesServiceImpl labelsForArticlesService;

    public RequestServiceImpl(BlogServiceImpl blogService, ArticleServiceImpl articleService, LabelServiceImpl labelService, LabelsForArticlesServiceImpl labelsForArticlesService) {
        this.blogService = blogService;
        this.articleService = articleService;
        this.labelService = labelService;
        this.labelsForArticlesService = labelsForArticlesService;
    }

    public List<Blog> test() {
        return this.blogService.list();
    }

    @Override
    public PublicResponse listBlogs(PageParam page) {
        final PageParam pageParam = Optional.ofNullable(page).orElseGet(() -> new PageParam(1, this.limit));
        final Optional<List<Blog>> optionalBlogs = Optional.ofNullable(this.listBlogsMapper(new Page<>(pageParam.getPage(), pageParam.getLimit())));
        // 获取博客
        final List<Blog> blogs = optionalBlogs.orElseThrow(() -> new BlogException(ResponseEnum.NO_BLOG));
        // 获取标签博客映射关系
        final Map<String, List<Long>> lfaMap = this.listLfaMapper(null);
        // 获取标签列表
        final Map<Long, Label> labelMap = this.listLabelsMapper();
        // 包装成 VO
        final List<BlogVO> blogVOList = blogs.stream().map(o -> {
            final String number = o.getNumber();
            final BlogVO blogVO = BlogVO.builder()
                    .number(number)
                    .title(o.getTitle())
                    .createdAt(o.getCreatedAt())
                    .cover(o.getCover())
                    .build();
            final List<Long> lfaLIds = lfaMap.get(number);
            if (CollectionUtils.isNotEmpty(lfaLIds)) {
                final List<LabelVO> labelVOS = lfaLIds.stream().map(lid -> {
                    final Label label = labelMap.get(lid);
                    return Converter.labelVO(label);
                }).collect(Collectors.toList());
                blogVO.setLabels(labelVOS);
            }
            return blogVO;
        }).collect(Collectors.toList());
        return PublicResponse.success(blogVOList);
    }

    @Override
    public PublicResponse getBlog(String number) {
        final Optional<Blog> blogOptional = Optional.ofNullable(this.getBlogMapper(number));
        final Blog blog = blogOptional.orElseThrow(() -> new BlogException(ResponseEnum.NO_BLOG));

        final Article article = this.getArticleMapper(number);

        final BlogVO blogVO = BlogVO.builder()
                .number(blog.getNumber())
                .title(blog.getTitle())
                .state(blog.getState())
                .body(article.getBody())
                .toc(article.getToc())
                .createdAt(blog.getCreatedAt())
                .updatedAt(blog.getUpdatedAt())
                .cover(blog.getCover())
                .build();
        final LabelsForArticlesDTO lfa = this.getLfaMapper(number);

        if (Objects.nonNull(lfa)) {
            final List<Label> labels = this.getLabelsMapper(lfa.getLId());
            final List<LabelVO> labelVOS = labels.stream().map(Converter::labelVO).collect(Collectors.toList());
            final Optional<List<LabelVO>> optionalLabelVOS = Optional.of(labelVOS);
            optionalLabelVOS.ifPresent(blogVO::setLabels);
        }
        return PublicResponse.success(blogVO);
    }

    @Override
    public PublicResponse listLabels() {
        final Map<Long, Label> labelMap = this.listLabelsMapper();
        Assertion.isEmpty(labelMap, ResponseEnum.NO_LABELS);

        final List<LabelVO> labelVOS = labelMap.values().stream().map(Converter::labelVO).collect(Collectors.toList());
        return PublicResponse.success(labelVOS);
    }

    @Override
    public PublicResponse listLabelsForBlog(String number) {
        final LabelsForArticlesDTO lfa = this.getLfaMapper(number);
        if (Objects.nonNull(lfa)) {
            final List<Label> labels = this.getLabelsMapper(lfa.getLId());
            final List<LabelVO> labelVOList = labels.stream().map(Converter::labelVO).collect(Collectors.toList());
            return PublicResponse.success(labelVOList);
        }
        return PublicResponse.success();
    }

    @Override
    public PublicResponse listBlogsByLabel(Long id, PageParam page) {
        final List<String> numbers = this.listNumbersByLabelId(id);
        Assertion.isEmpty(numbers, ResponseEnum.NO_BLOGS_IN_THE_LABEL);
        final List<Blog> blogs = this.listBlogsMapper(new Page<>(page.getPage(), page.getLimit()), numbers);

        final Map<String, List<Long>> lfaMap = this.listLfaMapper(numbers);

        List<BlogVO> blogVOList = this.listBlogs(blogs, lfaMap);
        return PublicResponse.success(blogVOList);
    }

    @Override
    public PublicResponse getLabelById(Long id) {
        return null;
    }

    @Override
    public PublicResponse listArchive(Integer page) {
        return null;
    }

    @Override
    public PublicResponse search(BlogSearchRequest request) {
        return null;
    }

    @Override
    public PublicResponse setCover(SetCoverRequest request) {
        return null;
    }

    private List<Blog> listBlogsMapper(Page<Blog> page) {
        final LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .select(Blog::getNumber, Blog::getTitle, Blog::getCreatedAt, Blog::getCover)
                .eq(Blog::getState, BLOG_STATE_OPEN)
                .orderByDesc(Blog::getCreatedAt);
        return this.blogService.page(page, wrapper).getRecords();
    }

    private List<Blog> listBlogsMapper(Page<Blog> page, List<String> numbers) {
        final LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .select(Blog::getNumber, Blog::getTitle, Blog::getCreatedAt, Blog::getCover)
                .in(Blog::getNumber, numbers);
        return this.blogService.page(page, wrapper).getRecords();
    }

    private Map<String, List<Long>> listLfaMapper(List<String> numbers) {
        final List<LabelsForArticlesDTO> labelsForArticlesDTOS = this.labelsForArticlesService.getBaseMapper().listLfa(numbers);
        return labelsForArticlesDTOS.stream().collect(Collectors.toMap(LabelsForArticlesDTO::getBNumber, LabelsForArticlesDTO::getLId, (origin, replacement) -> replacement));
    }

    private Map<Long, Label> listLabelsMapper() {
        final LambdaQueryWrapper<Label> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Label::getId, Label::getName, Label::getColor, Label::getDescription);
        return this.labelService.list(wrapper).stream().collect(Collectors.toMap(Label::getId, Function.identity(), (origin, replacement) -> replacement, LinkedHashMap::new));
    }

    private Blog getBlogMapper(String number) {
        final LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .select(Blog::getNumber, Blog::getTitle, Blog::getCreatedAt, Blog::getUpdatedAt, Blog::getCover, Blog::getState)
                .eq(Blog::getNumber, number);
        return this.blogService.getOne(wrapper);
    }

    private Article getArticleMapper(String number) {
        final LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .select(Article::getBNumber, Article::getBody, Article::getToc)
                .eq(Article::getBNumber, number);
        return this.articleService.getOne(wrapper);
    }

    private LabelsForArticlesDTO getLfaMapper(String number) {
        final List<String> numberWrapper = Collections.singletonList(number);
        final List<LabelsForArticlesDTO> labelsForArticlesDTOS = this.labelsForArticlesService.getBaseMapper().listLfa(numberWrapper);
        if (CollectionUtils.isNotEmpty(labelsForArticlesDTOS)) {
            return labelsForArticlesDTOS.get(0);
        }
        return null;
    }

    private List<Label> getLabelsMapper(List<Long> lIds) {
        final LambdaQueryWrapper<Label> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .select(Label::getId, Label::getName, Label::getColor, Label::getDescription)
                .in(Label::getId, lIds);
        return this.labelService.list(wrapper);
    }

    private LabelVO getLabelMapper(Long id) {
        final LambdaQueryWrapper<Label> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Label::getId, Label::getName, Label::getColor, Label::getDescription).eq(Label::getId, id);
        return Converter.labelVO(this.labelService.getOne(wrapper));
    }

    private List<String> listNumbersByLabelId(Long id) {
        final LambdaQueryWrapper<LabelsForArticles> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(LabelsForArticles::getBNumber).eq(LabelsForArticles::getLId, id);
        final List<LabelsForArticles> labelsForArticlesList = this.labelsForArticlesService.list(wrapper);
        return labelsForArticlesList.stream().map(LabelsForArticles::getBNumber).collect(Collectors.toList());
    }

    private List<BlogVO> listBlogs(List<Blog> blogs, Map<String, List<Long>> lfaMap) {
        final List<BlogVO> blogVOList = blogs.stream().map(o -> {
            final String number = o.getNumber();
            final BlogVO blogVO = BlogVO.builder()
                    .number(number)
                    .title(o.getTitle())
                    .createdAt(o.getCreatedAt())
                    .cover(o.getCover())
                    .build();
            final List<Long> lfaLIds = lfaMap.get(number);
            if (CollectionUtils.isNotEmpty(lfaMap.get(number))) {
                final List<LabelVO> labelVOList = lfaLIds.stream().map(lId -> this.getLabelMapper(lId)).collect(Collectors.toList());
                blogVO.setLabels(labelVOList);
            }
            return blogVO;
        }).collect(Collectors.toList());
        return blogVOList;
    }
}

package top.catcatc.controller.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import top.catcatc.service.impl.ArticleServiceImpl;
import top.catcatc.service.impl.BlogServiceImpl;
import top.catcatc.service.impl.LabelServiceImpl;
import top.catcatc.service.impl.LabelsForArticlesServiceImpl;
import top.catcatc.controller.service.RequestService;
import top.catcatc.common.assertion.Assertion;
import top.catcatc.common.enums.ResponseEnum;
import top.catcatc.common.exception.BlogException;
import top.catcatc.common.pojo.Converter;
import top.catcatc.common.pojo.dto.LabelsForArticlesDTO;
import top.catcatc.common.pojo.dto.SearchDTO;
import top.catcatc.common.pojo.vo.BlogVO;
import top.catcatc.common.pojo.vo.LabelVO;
import top.catcatc.common.pojo.entity.Article;
import top.catcatc.common.pojo.entity.Blog;
import top.catcatc.common.pojo.entity.Label;
import top.catcatc.common.pojo.entity.LabelsForArticles;
import top.catcatc.common.pojo.request.BlogSearchRequest;
import top.catcatc.common.pojo.request.PageParam;
import top.catcatc.common.pojo.request.SetCoverRequest;
import top.catcatc.common.pojo.response.PublicResponse;

import javax.servlet.http.HttpServletRequest;
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
    private Integer LIMIT;

    final private String BLOG_STATE_OPEN = "open";
    final private String BLOG_STATE_CLOSED = "closed";
    final private Integer FIRST_PAGE = 1;

    final private BlogServiceImpl blogService;
    final private ArticleServiceImpl articleService;
    final private LabelServiceImpl labelService;
    final private LabelsForArticlesServiceImpl labelsForArticlesService;
    final private RestTemplate restTemplate;
    final private HttpServletRequest request;

    public RequestServiceImpl(BlogServiceImpl blogService, ArticleServiceImpl articleService, LabelServiceImpl labelService, LabelsForArticlesServiceImpl labelsForArticlesService, RestTemplate restTemplate, HttpServletRequest request) {
        this.blogService = blogService;
        this.articleService = articleService;
        this.labelService = labelService;
        this.labelsForArticlesService = labelsForArticlesService;
        this.restTemplate = restTemplate;
        this.request = request;
    }

    @Override
    public PublicResponse listBlogs(PageParam page) {
        final List<BlogVO> blogVOList = this.listBlogs(page, BLOG_STATE_OPEN);
        return PublicResponse.success(blogVOList);
    }

    @Override
    public PublicResponse getBlog(String number) {
        // 通过 number 获取博客
        final Optional<Blog> blogOptional = Optional.ofNullable(this.getBlogMapper(number));
        final Blog blog = blogOptional.orElseThrow(() -> new BlogException(ResponseEnum.NO_BLOG));

        // 获取博文
        final Article article = this.getArticleMapper(number);

        // 组装
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

        // 如果有标签则加入标签关系
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

        Assert.notEmpty(labelMap, ResponseEnum.NO_LABELS.getMessage());

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

        Assert.notEmpty(numbers, ResponseEnum.NO_BLOGS_IN_THE_LABEL.getMessage());

        final List<Blog> blogs = this.listBlogsMapper(new Page<>(page.getPage(), page.getLimit()), numbers);

        final Map<String, List<Long>> lfaMap = this.listLfaMapper(numbers);

        List<BlogVO> blogVOList = this.listBlogs(blogs, lfaMap);
        return PublicResponse.success(blogVOList);
    }

    @Override
    public PublicResponse getLabelById(Long id) {
        final LabelVO label = this.getLabelMapper(id);
        return PublicResponse.success(label);
    }

    @Override
    public PublicResponse listArchive(PageParam page) {
        final List<BlogVO> blogVOList = this.listBlogs(page, BLOG_STATE_CLOSED);
        return PublicResponse.success(blogVOList);
    }

    @Override
    public PublicResponse search(BlogSearchRequest request) {
        final String url = "https://api.github.com/search/issues?q=repo:Annie3310/blog+author:Annie3310+%s in:title,body&per_page=%d&page=%d&order=asc";
        final String formattedUrl = String.format(url, request.getKeyword(), request.getLimit(), request.getPage());
        final Optional<SearchDTO> optionalResult = Optional.ofNullable(this.restTemplate.getForObject(formattedUrl, SearchDTO.class));
        final SearchDTO result = optionalResult.orElseThrow(() -> new BlogException(ResponseEnum.SEARCH_NO_RESULT));
        final Optional<List<BlogVO>> optionalItems = Optional.ofNullable(result.getItems());
        final List<BlogVO> items = optionalItems.orElseThrow(() -> new BlogException(ResponseEnum.SEARCH_NO_RESULT));
        final List<String> numbersList = items.stream().map(BlogVO::getNumber).collect(Collectors.toList());
        final Map<String, String> covers = this.getCovers(numbersList);
        items.forEach(o -> {
            final List<LabelVO> labelVOS = this.setWellNumber(Converter.setFontColor(o.getLabels()));
            o.setLabels(labelVOS);
            final String cover = covers.get(o.getNumber());
            if (StringUtils.isEmpty(cover)) {
                o.setCover(null);
            } else {
                o.setCover(cover);
            }
        });
        return PublicResponse.success(items);
    }

    @Override
    public PublicResponse setCover(SetCoverRequest request) {
        if (this.setCoverMapper(request)) {
            return PublicResponse.success();
        }
        return PublicResponse.error(ResponseEnum.SET_COVER_FAIL);
    }

    private List<BlogVO> listBlogs(PageParam page, String state) {
        final PageParam pageParam = Optional.ofNullable(page).orElseGet(() -> new PageParam(1, this.LIMIT));
        final Optional<List<Blog>> optionalBlogs = Optional.ofNullable(this.listBlogsMapper(new Page<>(pageParam.getPage(), pageParam.getLimit()), state));
        // 获取博客
        final List<Blog> blogs = optionalBlogs.orElseThrow(() -> new BlogException(ResponseEnum.NO_BLOG));
        if (blogs.isEmpty()) {
            return new ArrayList<>();
        }
        // 获取标签博客映射关系
        final Map<String, List<Long>> lfaMap = this.listLfaMapper(null);

        return this.listBlogs(blogs, lfaMap);
    }

    private List<Blog> listBlogsMapper(Page<Blog> page, String state) {
        final LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .select(Blog::getNumber, Blog::getTitle, Blog::getCreatedAt, Blog::getCover)
                .eq(Blog::getState, state)
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

    /**
     * 使用获取到的博客列表和标签关系组装成列表返回
     *
     * @param blogs  博客列表
     * @param lfaMap 博客标签关联关系
     * @return 组装了标签的博客列表
     */
    private List<BlogVO> listBlogs(List<Blog> blogs, Map<String, List<Long>> lfaMap) {
        return blogs.stream().map(o -> {
            final String number = o.getNumber();
            final BlogVO blogVO = BlogVO.builder()
                    .number(number)
                    .title(o.getTitle())
                    .createdAt(o.getCreatedAt())
                    .cover(o.getCover())
                    .build();
            final List<Long> lfaLIds = lfaMap.get(number);
            if (CollectionUtils.isNotEmpty(lfaMap.get(number))) {
                final List<LabelVO> labelVOList = lfaLIds.stream().map(this::getLabelMapper).collect(Collectors.toList());
                blogVO.setLabels(labelVOList);
            }
            return blogVO;
        }).collect(Collectors.toList());
    }

    /**
     * 设置井号
     */
    private List<LabelVO> setWellNumber(List<LabelVO> labelVOS) {
        for (LabelVO labelVO : labelVOS) {
            labelVO.setColor("#" + labelVO.getColor());
        }
        return labelVOS;
    }

    private Map<String, String> getCovers(List<String> numbers) {
        final LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Blog::getNumber, Blog::getCover).in(Blog::getNumber, numbers);
        final List<Blog> blogListWithCover = this.blogService.list(wrapper);
        return blogListWithCover.stream().collect(Collectors.toMap(Blog::getNumber, v -> Optional.ofNullable(v.getCover()).orElse(""), (origin, replacement) -> replacement));
    }

    private boolean setCoverMapper(SetCoverRequest request) {
        final String number = request.getNumber();
        final String src = request.getSrc();
        final LambdaUpdateWrapper<Blog> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Blog::getCover, src).eq(Blog::getNumber, number);

        return this.blogService.update(wrapper);
    }
}

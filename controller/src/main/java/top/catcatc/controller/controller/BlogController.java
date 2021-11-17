package top.catcatc.controller.controller;

import org.springframework.beans.factory.annotation.Value;
import top.catcatc.common.enums.ResponseEnum;
import top.catcatc.common.pojo.response.ResponseFactory;
import top.catcatc.common.pojo.vo.BlogVO;
import top.catcatc.common.pojo.vo.LabelVO;
import top.catcatc.controller.service.RequestService;
import top.catcatc.controller.service.impl.RequestServiceImpl;
import top.catcatc.common.pojo.request.BlogSearchRequest;
import top.catcatc.common.pojo.request.PageParam;
import top.catcatc.common.pojo.request.SetCoverRequest;
import top.catcatc.common.pojo.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 博客后端接口
 *
 * @author 王金义
 * @date 2021/8/30
 */
@RestController
public class BlogController {
    @Value("${server.port}")
    private Integer port;

    private final RequestService service;

    public BlogController(RequestServiceImpl service) {
        this.service = service;
    }

    @GetMapping("port")
    public Integer port() {
        return this.port;
    }

    @GetMapping("list/blogs")
    public ResponseResult<List<BlogVO>> listBlogs(@Valid PageParam page) {
        final List<BlogVO> result = this.service.listBlogs(page);
        return new ResponseFactory<List<BlogVO>>().success(result);
    }

    @GetMapping("get/blog/{number}")
    public ResponseResult<BlogVO> getBlog(@PathVariable String number) {
        final BlogVO result = this.service.getBlog(number);
        return new ResponseFactory<BlogVO>().success(result);
    }

    @GetMapping("list/labels")
    public ResponseResult<List<LabelVO>> listLabels() {
        final List<LabelVO> result = this.service.listLabels();
        final ResponseFactory<List<LabelVO>> resultFactory = new ResponseFactory<>();
        if (Objects.isNull(result)) {
            return resultFactory.error(ResponseEnum.NO_LABELS);
        }
        return resultFactory.success(result);
    }

    @GetMapping("list/labels/blog/{number}")
    public ResponseResult<List<LabelVO>> getLabelsByBlogNumber(@PathVariable String number) {
        final List<LabelVO> result = this.service.listLabelsForBlog(number);
        return new ResponseFactory<List<LabelVO>>().success(result);
    }

    @GetMapping("list/blogs/label/{id}")
    public ResponseResult<List<BlogVO>> listBlogsByLabelId(@Valid PageParam page, @PathVariable Long id) {
        final List<BlogVO> result = this.service.listBlogsByLabel(id, page);
        final ResponseFactory<List<BlogVO>> resultFactory = new ResponseFactory<>();
        if (Objects.isNull(result)) {
            resultFactory.error(ResponseEnum.NO_BLOGS_IN_THE_LABEL);
        }
        return resultFactory.success(result);
    }

    @GetMapping("get/label/{id}")
    public ResponseResult<LabelVO> getLabel(@PathVariable Long id) {
        final LabelVO result = this.service.getLabelById(id);
        return new ResponseFactory<LabelVO>().success(result);
    }

    @GetMapping("list/archive")
    public ResponseResult<List<BlogVO>> listArchive(@Valid PageParam page) {
        final List<BlogVO> result = this.service.listArchive(page);
        return new ResponseFactory<List<BlogVO>>().success(result);
    }

    @GetMapping("search/blogs")
    public ResponseResult<List<BlogVO>> search(@Valid BlogSearchRequest request) {
        final List<BlogVO> result = this.service.search(request);
        final ResponseFactory<List<BlogVO>> resultFactory = new ResponseFactory<>();
        if (Objects.isNull(request)) {
            return resultFactory.error(ResponseEnum.SEARCH_NO_RESULT);
        }
        return resultFactory.success(result);
    }

    @PutMapping("set/cover")
    public ResponseResult<Boolean> setCover(@Valid @RequestBody SetCoverRequest request) {
        final Boolean result = this.service.setCover(request);
        return new ResponseFactory<Boolean>().success(result);
    }
}

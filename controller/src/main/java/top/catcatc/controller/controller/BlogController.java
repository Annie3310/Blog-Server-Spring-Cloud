package top.catcatc.controller.controller;

import top.catcatc.controller.service.RequestService;
import top.catcatc.controller.service.impl.RequestServiceImpl;
import top.catcatc.common.pojo.request.BlogSearchRequest;
import top.catcatc.common.pojo.request.PageParam;
import top.catcatc.common.pojo.request.SetCoverRequest;
import top.catcatc.common.pojo.response.PublicResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 博客后端接口
 *
 * @author 王金义
 * @date 2021/8/30
 */
@RestController
public class BlogController {
    private final RequestService service;

    public BlogController(RequestServiceImpl service) {
        this.service = service;
    }

    @GetMapping("list/blogs")
    public PublicResponse listBlogs(PageParam page) {
        return this.service.listBlogs(page);
    }

    @GetMapping("get/blog/{number}")
    public PublicResponse getBlog(@PathVariable String number) {
        return this.service.getBlog(number);
    }

    @GetMapping("list/labels")
    public PublicResponse listLabels() {
        return this.service.listLabels();
    }

    @GetMapping("list/labels/blog/{number}")
    public PublicResponse getLabelsByBlogNumber(@PathVariable String number) {
        return this.service.listLabelsForBlog(number);
    }

    @GetMapping("list/blogs/label/{id}")
    public PublicResponse listBlogsByLabelId(@PathVariable Long id, PageParam page) {
        return this.service.listBlogsByLabel(id, page);
    }

    @GetMapping("get/label/{id}")
    public PublicResponse getLabel(@PathVariable Long id) {
        return this.service.getLabelById(id);
    }

    @GetMapping("list/archive")
    public PublicResponse listArchive(PageParam page) {
        return this.service.listArchive(page);
    }

    @GetMapping("search/blogs")
    public PublicResponse search(@Valid BlogSearchRequest request) {
        return this.service.search(request);
    }

    @PutMapping("set/cover")
    public PublicResponse setCover(@Valid @RequestBody SetCoverRequest request) {
        return this.service.setCover(request);
    }
}

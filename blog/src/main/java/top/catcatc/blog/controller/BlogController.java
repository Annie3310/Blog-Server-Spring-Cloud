package top.catcatc.blog.controller;

import top.catcatc.blog.service.RequestService;
import top.catcatc.blog.service.impl.RequestServiceImpl;
import top.catcatc.common.pojo.entity.Blog;
import top.catcatc.common.pojo.request.BlogSearchRequest;
import top.catcatc.common.pojo.request.PageParam;
import top.catcatc.common.pojo.request.SetCoverRequest;
import top.catcatc.common.pojo.response.PublicResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 博客后端接口
 *
 * @author 王金义
 * @date 2021/8/30
 */
@RestController
public class BlogController {
    private final RequestServiceImpl service;

    public BlogController(RequestServiceImpl service) {
        this.service = service;
    }

    @GetMapping("test")
    public List<Blog> test() {
        return this.service.test();
    }

    @GetMapping("list/blogs")
    public PublicResponse listBlogs(@RequestBody PageParam page) {
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
    public PublicResponse getLabels(@PathVariable String number) {
        return this.service.listLabelsForBlog(number);
    }

    @GetMapping("list/blogs/label/{id}")
    public PublicResponse listBlogsByLabel(@PathVariable Long id, @RequestBody PageParam page) {
        return this.service.listBlogsByLabel(id, page);
    }

    @GetMapping("get/label/{id}")
    public PublicResponse getLabel(@PathVariable Long id) {
        return this.service.getLabelById(id);
    }

    @GetMapping("list/archive")
    public PublicResponse listArchive(@RequestParam(defaultValue = "0", required = false) Integer page) {
        return this.service.listArchive(page);
    }

    @GetMapping("search/blogs")
    public PublicResponse search(String keyword, @RequestParam(defaultValue = "1", required = false) Integer page, @RequestParam(defaultValue = "20", required = false) Integer perPage) {
        return this.service.search(
                BlogSearchRequest.builder()
                        .keyword(keyword)
                        .page(page)
                        .perPage(perPage)
                        .build());
    }

    @GetMapping("set/cover/{number}/{src}")
    public PublicResponse setCover(@PathVariable String number, @PathVariable String src) {
        SetCoverRequest request = SetCoverRequest
                .builder()
                .number(number)
                .src(src)
                .build();
        return this.service.setCover(request);
    }

    @PutMapping("set/cover")
    public PublicResponse setCover(@RequestBody SetCoverRequest request) {
        return this.service.setCover(request);
    }
}

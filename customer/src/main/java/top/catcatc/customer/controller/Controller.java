package top.catcatc.customer.controller;

import org.springframework.web.bind.annotation.*;
import top.catcatc.common.pojo.request.BlogSearchRequest;
import top.catcatc.common.pojo.request.PageParam;
import top.catcatc.common.pojo.request.SetCoverRequest;
import top.catcatc.common.pojo.response.PublicResponse;
import top.catcatc.customer.feigon.Customer;

import javax.validation.Valid;

/**
 * @author 王金义
 * @date 2021/11/16
 */
@RestController
public class Controller {
    final private Customer customer;

    public Controller(Customer customer) {
        this.customer = customer;
    }

    @GetMapping("port")
    public Integer port() {
        return this.customer.port();
    }

    @GetMapping("list/blogs")
    public PublicResponse listBlogs(@Valid PageParam page) {
        return this.customer.listBlogs(page.getPage(), page.getLimit());
    }

    @GetMapping("get/blog/{number}")
    public PublicResponse getBlog(@PathVariable String number) {
        return this.customer.getBlog(number);
    }

    @GetMapping("list/labels")
    public PublicResponse listLabels() {
        return this.customer.listLabels();
    }

    @GetMapping("list/labels/blog/{number}")
    public PublicResponse getLabelsByBlogNumber(@PathVariable String number) {
        return this.customer.getLabelsByBlogNumber(number);
    }

    @GetMapping("list/blogs/label/{id}")
    public PublicResponse listBlogsByLabelId(@Valid PageParam page, @PathVariable Long id) {
        return this.customer.listBlogsByLabelId(page.getPage(), page.getLimit(), id);
    }

    @GetMapping("get/label/{id}")
    public PublicResponse getLabel(@PathVariable Long id) {
        return this.customer.getLabel(id);
    }

    @GetMapping("list/archive")
    public PublicResponse listArchive(@Valid PageParam page) {
        return this.listArchive(page);
    }

    @GetMapping("search/blogs")
    public PublicResponse search(@Valid BlogSearchRequest request) {
        return this.customer.search(request.getKeyword(), request.getPage(), request.getLimit());
    }

    @PutMapping("set/cover")
    public PublicResponse setCover(@Valid @RequestBody SetCoverRequest request) {
        return this.setCover(request);
    }
}

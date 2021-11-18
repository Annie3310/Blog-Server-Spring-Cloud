package top.cattycat.customer.controller;

import org.springframework.web.bind.annotation.*;
import top.cattycat.common.pojo.request.BlogSearchRequest;
import top.cattycat.common.pojo.request.PageParam;
import top.cattycat.common.pojo.request.SetCoverRequest;
import top.cattycat.common.pojo.response.ResponseResult;
import top.cattycat.common.pojo.vo.BlogVO;
import top.cattycat.common.pojo.vo.LabelVO;
import top.cattycat.customer.feigon.Customer;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseResult<List<BlogVO>> listBlogs(@Valid PageParam page) {
        return this.customer.listBlogs(page.getPage(), page.getLimit());
    }

    @GetMapping("get/blog/{number}")
    public ResponseResult<BlogVO> getBlog(@PathVariable String number) {
        return this.customer.getBlog(number);
    }

    @GetMapping("list/labels")
    public ResponseResult<List<LabelVO>> listLabels() {
        return this.customer.listLabels();
    }

    @GetMapping("list/labels/blog/{number}")
    public ResponseResult<List<LabelVO>> getLabelsByBlogNumber(@PathVariable String number) {
        return this.customer.getLabelsByBlogNumber(number);
    }

    @GetMapping("list/blogs/label/{id}")
    public ResponseResult<List<BlogVO>> listBlogsByLabelId(@Valid PageParam page, @PathVariable Long id) {
        return this.customer.listBlogsByLabelId(page.getPage(), page.getLimit(), id);
    }

    @GetMapping("get/label/{id}")
    public ResponseResult<LabelVO> getLabel(@PathVariable Long id) {
        return this.customer.getLabel(id);
    }

    @GetMapping("list/archive")
    public ResponseResult<List<BlogVO>> listArchive(@Valid PageParam page) {
        return this.customer.listArchive(page.getPage(), page.getLimit());
    }

    @GetMapping("search/blogs")
    public ResponseResult<List<BlogVO>> search(@Valid BlogSearchRequest request) {
        return this.customer.search(request.getKeyword(), request.getPage(), request.getLimit());
    }

    @PutMapping("set/cover")
    public ResponseResult<Boolean> setCover(@Valid @RequestBody SetCoverRequest request) {
        return this.customer.setCover(request);
    }
}

package top.cattycat.customer.feigon;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.cattycat.common.pojo.request.SetCoverRequest;
import top.cattycat.common.pojo.response.ResponseResult;
import top.cattycat.common.pojo.vo.BlogVO;
import top.cattycat.common.pojo.vo.LabelVO;

import java.util.List;


/**
 * @author 王金义
 * @date 2021/11/16
 */
@FeignClient("blog-server")
public interface Customer {
    @GetMapping("port")
    Integer port();

    @GetMapping("list/blogs")
    ResponseResult<List<BlogVO>> listBlogs(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit);

    @GetMapping("get/blog/{number}")
    ResponseResult<BlogVO> getBlog(@PathVariable("number") String number);

    @GetMapping("list/labels")
    ResponseResult<List<LabelVO>> listLabels();

    @GetMapping("list/labels/blog/{number}")
    ResponseResult<List<LabelVO>> getLabelsByBlogNumber(@PathVariable("number") String number);

    @GetMapping("list/blogs/label/{id}")
    ResponseResult<List<BlogVO>> listBlogsByLabelId(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, @PathVariable("id") Long id);

    @GetMapping("get/label/{id}")
    ResponseResult<LabelVO> getLabel(@PathVariable("id") Long id);

    @GetMapping("list/archive")
    ResponseResult<List<BlogVO>> listArchive(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit);

    @GetMapping("search/blogs")
    ResponseResult<List<BlogVO>> search(@RequestParam("keyword") String keyword, @RequestParam("page") Integer page, @RequestParam("limit") Integer limit);

    @PutMapping("set/cover")
    ResponseResult<Boolean> setCover(@RequestBody SetCoverRequest request);
}
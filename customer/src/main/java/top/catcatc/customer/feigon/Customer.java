package top.catcatc.customer.feigon;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.catcatc.common.pojo.request.SetCoverRequest;
import top.catcatc.common.pojo.response.PublicResponse;


/**
 * @author 王金义
 * @date 2021/11/16
 */
@FeignClient("blog-server")
public interface Customer {
    @GetMapping("port")
    Integer port();

    @GetMapping("list/blogs")
    PublicResponse listBlogs(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit);

    @GetMapping("get/blog/{number}")
    PublicResponse getBlog(@PathVariable("number") String number);

    @GetMapping("list/labels")
    PublicResponse listLabels();

    @GetMapping("list/labels/blog/{number}")
    PublicResponse getLabelsByBlogNumber(@PathVariable("number") String number);

    @GetMapping("list/blogs/label/{id}")
    PublicResponse listBlogsByLabelId(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, @PathVariable("id") Long id);

    @GetMapping("get/label/{id}")
    PublicResponse getLabel(@PathVariable("id") Long id);

    @GetMapping("list/archive")
    PublicResponse listArchive(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit);

    @GetMapping("search/blogs")
    PublicResponse search(@RequestParam("keyword") String keyword, @RequestParam("page") Integer page, @RequestParam("limit") Integer limit);

    @PutMapping("set/cover")
    PublicResponse setCover(@RequestBody SetCoverRequest request);
}
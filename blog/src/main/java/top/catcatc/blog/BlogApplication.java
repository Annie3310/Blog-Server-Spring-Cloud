package top.catcatc.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 王金义
 * @date 2021/11/9
 */
@SpringBootApplication
@MapperScan("top.catcatc.blog.mapper")
@EnableTransactionManagement(proxyTargetClass = true)
@EntityScan
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}

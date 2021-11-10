package top.catcatc.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 王金义
 * @date 2021/11/9
 */
@SpringBootApplication(scanBasePackages = "top.catcatc")
@MapperScan("top.catcatc.mapper.mapper")
@EnableTransactionManagement(proxyTargetClass = true)
@EntityScan(basePackages = "top.catcatc.common")
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}

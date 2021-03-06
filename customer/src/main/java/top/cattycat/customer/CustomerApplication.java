package top.cattycat.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ηιδΉ
 * @date 2021/11/16
 */
@SpringBootApplication
@EnableFeignClients
@EntityScan(basePackages = "top.cattycat.common")
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}

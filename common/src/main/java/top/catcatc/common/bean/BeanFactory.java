package top.catcatc.common.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author 王金义
 * @date 2021/10/8
 */
@Configuration
public class BeanFactory {
    @Value("${github.token}")
    private String token;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.defaultHeader("accept", "application/vnd.github.v3+json");
        restTemplateBuilder.defaultHeader("Authorization", token);
        return restTemplateBuilder.build();
    }
}

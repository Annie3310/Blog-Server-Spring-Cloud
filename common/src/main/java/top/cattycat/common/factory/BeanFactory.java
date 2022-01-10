package top.cattycat.common.factory;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import top.cattycat.common.config.GitHubConfig;

/**
 * @author 王金义
 * @date 2021/10/8
 */
@Configuration
public class BeanFactory {
    final private GitHubConfig gitHubConfig;

    public BeanFactory(GitHubConfig gitHubConfig) {
        this.gitHubConfig = gitHubConfig;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.defaultHeader("accept", "application/vnd.github.v3+json");
        restTemplateBuilder.defaultHeader("Authorization", this.gitHubConfig.getToken());
        return restTemplateBuilder.build();
    }

    /**
     * 默认的 restTemplate
     * @return 默认的 restTemplate
     */
    @Bean("defaultRestTemplate")
    public RestTemplate defaultRestTemplate() {
        return new RestTemplate();
    }

    /**
     * MyBatis-Plus 分页插件
     * @return 插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
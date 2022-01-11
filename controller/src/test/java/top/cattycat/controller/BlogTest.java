package top.cattycat.controller;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import top.cattycat.common.pojo.oauth.github.response.GitHubUserInfoResponse;
import top.cattycat.common.util.JwtUtils;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @author 王金义
 * @date 2022/1/6
 */
@SpringBootTest
public class BlogTest {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("defaultRestTemplate")
    RestTemplate restTemplate;

    @Test
    void jwtTest() {
        /*final GitHubUserInfoResponse user = new GitHubUserInfoResponse().setLogin("Annie3310").setId(63697041L).setAvatarUrl("https://avatars.githubusercontent.com/u/63697041?v=4");
        String token = JwtUtils.getToken(user);
        System.out.println(token);

        Claims claims = JwtUtils.parseToken(token);
        System.out.println(claims);*/

        System.out.println(JwtUtils.verify("eyJhbGciOiJIUzI1NiJ9.ewogICJzdWIiOiAiQW5uaWUzMzEwIiwKICAiaWQiOiA2MzY5NzA0MSwKICAibG9naW4iOiAiQW5uaWUzMzEwIiwKICAiZXhwIjogMTY0MjQxMDM5MSwKICAiaWF0IjogMTY0MTgwNTU5MCwKICAianRpIjogIjFjZGQzYWJjLTIwOGItNDkwMC04MTA4LTlhN2NjZDFkNTNmNyIKfQ.bXp2zLVmNunn-mLu_q1_vMjBq0QtDwgzP7h6tTwozHC"));
    }

    @Test
    void redisTest() {
        final ValueOperations ops = this.redisTemplate.opsForValue();
        ops.set("f58ce964b5778b4c2ab892cb668edf74cf1a", "1");
//        System.out.println(ops.get("f58ce964b5778b4c2ab892cb668edf74cf1a"));
        System.out.println(ops.get("f58ce964b5778b4c2ab892cb668edf74cf1a"));
    }

    @Test
    void testProxyIp() {

        String url = "https://www.google.com";

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(
                new Proxy(
                        Proxy.Type.HTTP,
                        new InetSocketAddress("127.0.0.1", 4780)  //设置代理服务
                )
        );
        restTemplate.setRequestFactory(requestFactory);
        //发送请求
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);  //打印响应结果
    }
}

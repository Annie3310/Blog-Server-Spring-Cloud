package top.cattycat.controller;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.cattycat.common.pojo.oauth.github.response.GitHubUserInfoResponse;
import top.cattycat.common.util.JwtUtils;

/**
 * @author 王金义
 * @date 2022/1/6
 */
@SpringBootTest
public class BlogTest {
    @Test
    void jwtTest() {
        /*final GitHubUserInfoResponse user = new GitHubUserInfoResponse().setLogin("Annie3310").setId(63697041L).setAvatarUrl("https://avatars.githubusercontent.com/u/63697041?v=4");
        String token = JwtUtils.getToken(user);
        System.out.println(token);

        Claims claims = JwtUtils.parseToken(token);
        System.out.println(claims);*/

        System.out.println(JwtUtils.verify("eyJhbGciOiJIUzI1NiJ9.ewogICJzdWIiOiAiQW5uaWUzMzEwIiwKICAiaWQiOiA2MzY5NzA0MSwKICAibG9naW4iOiAiQW5uaWUzMzEwIiwKICAiZXhwIjogMTY0MjQxMDM5MSwKICAiaWF0IjogMTY0MTgwNTU5MCwKICAianRpIjogIjFjZGQzYWJjLTIwOGItNDkwMC04MTA4LTlhN2NjZDFkNTNmNyIKfQ.bXp2zLVmNunn-mLu_q1_vMjBq0QtDwgzP7h6tTwozHC"));
    }
}

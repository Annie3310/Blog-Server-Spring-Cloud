package top.cattycat.controller.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.cattycat.common.enums.ResponseEnum;
import top.cattycat.common.pojo.oauth.github.response.GitHubAuthorizationResponse;
import top.cattycat.common.pojo.oauth.github.response.GitHubGetAccessTokenResponse;
import top.cattycat.common.pojo.oauth.github.response.GitHubUserInfoResponse;
import top.cattycat.common.pojo.response.ResponseFactory;
import top.cattycat.common.pojo.response.ResponseResult;
import top.cattycat.common.util.JwtUtils;
import top.cattycat.service.UserService;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 王金义
 * @date 2022/1/7
 */
@RestController
@RequestMapping("login/oauth/github")
public class Login {
    private final UserService userService;
    private final StringRedisTemplate stringRedisTemplate;

    private final static String REDIS_KEY_TEMPLATE = "user-access-token-%s";

    public Login(UserService userService, StringRedisTemplate stringRedisTemplate) {
        this.userService = userService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @GetMapping("/authorize")
    public ResponseResult<String> authorize(GitHubAuthorizationResponse response) {
        final GitHubGetAccessTokenResponse accessTokenResponse = this.userService.getAccessToken(response);
        final String accessToken = accessTokenResponse.getAccessToken();
        final GitHubUserInfoResponse userInformation = this.userService.getUserInformation(accessToken);
        final Long id = userInformation.getId();
        final ResponseFactory<String> resultFactory = new ResponseFactory<>();

        if (Objects.isNull(id)) {
            return resultFactory.error(ResponseEnum.CONNECT_TO_GITHUB_FAILED);
        }
        // Whether user has registered
        if (this.userService.isRegistered(id)) {
            this.userService.updateLastLoginTime(id);
            this.setAccessTokenInRedis(userInformation, accessToken);
            return resultFactory.success(JwtUtils.getToken(userInformation));
        } else {
            // It will register if user has not registered
            if (this.userService.register(userInformation)) {
                this.setAccessTokenInRedis(userInformation, accessToken);
                return resultFactory.success(JwtUtils.getToken(userInformation));
            } else {
                return resultFactory.error(ResponseEnum.REGISTER_FAILED);
            }
        }
    }

    /**
     * Set user's access token in redis
     * @param userInformation
     * @param accessToken
     */
    private void setAccessTokenInRedis(GitHubUserInfoResponse userInformation, String accessToken) {
        final String key = String.format(REDIS_KEY_TEMPLATE, userInformation.getId());
        this.stringRedisTemplate.opsForValue().set(key, accessToken, 7, TimeUnit.DAYS);
    }
}

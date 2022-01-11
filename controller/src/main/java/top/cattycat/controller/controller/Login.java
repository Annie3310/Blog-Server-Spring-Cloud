package top.cattycat.controller.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
    private final RedisTemplate stringRedisTemplate;

    private final static String USER_ACCESS_TOKEN_TEMPLATE = "user-access-token-%s";
    private final static String USER_JWT_TOKEN_TEMPLATE = "user-jwt-token-%s";

    public Login(UserService userService, RedisTemplate redisTemplate) {
        this.userService = userService;
        this.stringRedisTemplate = redisTemplate;
    }

    @GetMapping("/authorize")
    public ResponseResult<String> authorize(GitHubAuthorizationResponse response) {
        final String state = response.getState();
        final GitHubGetAccessTokenResponse accessTokenResponse = this.userService.getAccessToken(response);
        final String accessToken = accessTokenResponse.getAccessToken();
        final GitHubUserInfoResponse userInformation = this.userService.getUserInformation(accessToken);
        final Long id = userInformation.getId();
        final ResponseFactory<String> resultFactory = new ResponseFactory<>();

        if (Objects.isNull(id)) {
            return resultFactory.error(ResponseEnum.CONNECT_TO_GITHUB_FAILED);
        }
        // Whether user has registered
        final String jwtToken = JwtUtils.getToken(userInformation);
        if (this.userService.isRegistered(id)) {
            this.userService.updateLastLoginTime(id);
            this.setAccessTokenInRedis(userInformation, accessToken, state, jwtToken);
            return resultFactory.success(jwtToken);
        } else {
            // It will register if user has not registered
            if (this.userService.register(userInformation)) {
                this.setAccessTokenInRedis(userInformation, accessToken, state, jwtToken);
                return resultFactory.success(jwtToken);
            } else {
                return resultFactory.error(ResponseEnum.REGISTER_FAILED);
            }
        }
    }

    @PostMapping("/validate")
    public ResponseResult<String> validate(@RequestBody String state) {
        final ValueOperations<String, String> opsForValue = this.stringRedisTemplate.opsForValue();
        String jwtToken = opsForValue.get(state);
        final ResponseFactory<String> resultFactory = new ResponseFactory<>();
        if (StringUtils.isEmpty(jwtToken)) {
            return resultFactory.error(ResponseEnum.LOGIN_IS_NOT_COMPLETE);
        } else {
            return resultFactory.success(jwtToken);
        }
    }
    /**
     * Set user's access token and jwt token into redis
     * @param userInformation
     * @param accessToken
     */
    private void setAccessTokenInRedis(GitHubUserInfoResponse userInformation, String accessToken, String state, String jwtToken) {
        final String key = String.format(USER_ACCESS_TOKEN_TEMPLATE, userInformation.getId());
        final ValueOperations<String, String> opsForValue = this.stringRedisTemplate.opsForValue();
        opsForValue.set(key, accessToken, 7, TimeUnit.DAYS);
        opsForValue.set(state, jwtToken);
    }
}

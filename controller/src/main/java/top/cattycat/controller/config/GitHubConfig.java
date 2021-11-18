package top.cattycat.controller.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 王金义
 * @date 2021/11/18
 */
@ConfigurationProperties("github")
public class GitHubConfig {
    private String secret;
    private String token;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

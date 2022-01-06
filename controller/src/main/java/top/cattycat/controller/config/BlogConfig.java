package top.cattycat.controller.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * @author 王金义
 * @date 2021/11/18
 */
@ConfigurationProperties("blog")
public class BlogConfig {
    private Set<String> username;
    private Page page;
    private SearchLimit searchLimit;

    public SearchLimit getSearchLimit() {
        return searchLimit;
    }

    public void setSearchLimit(SearchLimit searchLimit) {
        this.searchLimit = searchLimit;
    }

    public Set<String> getUsername() {
        return username;
    }

    public void setUsername(Set<String> username) {
        this.username = username;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public static class Page{
        private Integer limit;

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }
    }

    public static class SearchLimit {
        private Long limit;

        public Long getLimit() {
            return limit;
        }

        public void setLimit(Long limit) {
            this.limit = limit;
        }
    }
}

package top.catcatc.common.utils;

import top.catcatc.common.pojo.Converter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

/**
 * @author 王金义
 * @date 2021/9/17
 */
@Component
public class HtmlUtils {
    private final String TOC_TITLE = "<span class=\"toc-title\">目录</span>";
    private final String TITLE_ANCHOR = "anchor";
    private final int TITLE_AMOUNT = 6;

    private final RestTemplate restTemplate;

    public HtmlUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Markdown to html
     *
     * @param md markdown
     * @return html
     */
    public String toHtml(String md) {
        String html = restTemplate.postForObject("https://api.github.com/markdown", Converter.markdownBody(md), String.class);
        return setIdToTitleTag(html);
    }

    /**
     * 生成 Vue 前端使用的目录
     *
     * @param html 原文
     * @return 目录
     */
    public String getTableOfContents(String html) {
        StringBuilder stringBuilder = new StringBuilder();
        Document parse = Jsoup.parse(html);
        Elements anchor = parse.body().getElementsByClass(TITLE_ANCHOR);
        Iterator<Element> iterator = anchor.iterator();
        stringBuilder.append(TOC_TITLE);
        while (iterator.hasNext()) {
            if (!stringBuilder.toString().isEmpty()) {
                stringBuilder.append("\n");
            }
            Element target = iterator.next().parent();
            String wrapTitle = wrapTitle(target.tagName(), target.text());
            stringBuilder.append(wrapTitle);
        }
        return stringBuilder.toString();
    }

    /**
     * 给目录标签包裹一层 a 标签
     *
     * @param titleLevel 目录等级
     * @param text       标签 text
     * @return 包裹后的标签
     */
    private String wrapTitle(String titleLevel, String text) {
        String replaced = this.replacePlaceAndToLowerCase(text);
        // <a class="toc-h2" anchor="#user-content-使用-redis-做分布式锁"><li>xxx xxx</li></a>
        return "<a class=\"toc-" + titleLevel + "\" anchor=\"#" + replaced + "\"><li>" + text + "</li></a>";
    }

    /**
     * 给标题标签加入值为 text 的 id
     *
     * @param html 标题标签
     * @return 加入 id 后的标签
     */
    private String setIdToTitleTag(String html) {
        Document parse = Jsoup.parse(html);
        HashMap<String, Iterator<Element>> iteratorMap = new HashMap<>(6);
        for (int i = 1; i <= TITLE_AMOUNT; i++) {
            Elements titleTagElements = parse.getElementsByTag("h" + i);
            Iterator<Element> iterator = titleTagElements.iterator();
            iteratorMap.put("h" + i, iterator);
        }
        Set<String> keySet = iteratorMap.keySet();
        for (String s : keySet) {
            Iterator<Element> titleTagElements = iteratorMap.get(s);
            while (titleTagElements.hasNext()) {
                Element next = titleTagElements.next();
                next.attr("id", this.replacePlaceAndToLowerCase(next.text()));
            }
        }
        return parse.body().html();
    }

    /**
     * 将空格替换成 ”-“, 并全部转换为小写
     *
     * @param origin 原谅
     * @return 处理后的文本
     */
    private String replacePlaceAndToLowerCase(String origin) {
        return origin.replace(" ", "-").toLowerCase(Locale.ROOT);
    }
}

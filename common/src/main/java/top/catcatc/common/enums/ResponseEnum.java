package top.catcatc.common.enums;

/**
 * 错误返回信息
 *
 * @author 王金义
 * @date 2021/8/30
 */
public enum ResponseEnum {
    SUCCESS("00000", "Success"),

    VALUE_IS_NULL("B0001", "传入值为 null"),

    VALUES_ARE_NOT_EQUALS("B0001", "两个传入对象不是同一个对象"),
    /**
     * User signature checksum failure
     */
    SIGNATURE_CHECKSUM_FAILURE("A0340", "请求签名未通过验证"),
    /**
     * The action is not one of opened / edited / deleted, do not need to handle
     */
    DO_NOT_HAVE_TO_HANDLE("00000", "无需处理该活动"),
    USER_IS_NOT_OWNER("B0001","该用户不是仓库所有者"),

    SEARCH_NO_RESULT("B0001", "没有搜索到结果"),

    BLOG_INSERT_FAIL("B0001","新增博客失败"),
    BLOG_UPDATE_FAIL("B0001","更新博客失败"),
    BLOG_DELETE_FAIL("B0001","删除博客失败"),
    ARTICLE_INSERT_FAIL("B0001","新增文章失败"),
    ARTICLE_UPDATE_FAIL("B0001","更新文章失败"),
    ARTICLE_DELETE_FAIL("B0001", "删除文章失败"),
    LABEL_INSERT_FAIL("B0001", "新增标签失败"),
    LABEL_UPDATE_FAIL("B0001", "更新标签失败"),
    LABEL_DELETE_FAIL("B0001", "删除标签失败"),
    LFB_INSERT_FAIL("B0001", "新增文章标签关系失败"),
    LFB_DELETE_FAIL("B0001", "删除文章标签关系失败"),
    BLOG_CLOSE_FAIL("B0001", "关闭博客失败"),
    BLOG_REOPEN_FAIL("B0001", "重新打开博客失败"),

    NO_BLOG("B0001", "没有博客"),
    NO_LABEL("B0001", "该 id 下没有标签"),
    NO_ARTICLES("B0001", "文章为空"),
    NO_LABELS("B0001", "该仓库没有标签"),
    NO_BLOGS_IN_THE_LABEL("B0001", "该博客没有标签"),

    NO_PASSABLE_USERNAME_CONFIGURED("B0001", "未配置可通过验证的用户名"),

    TOO_MANY_REQUEST("A0501","请求过于频繁, 请稍后再试"),

    SET_COVER_FAIL("B0001", "设置封面失败");


    String code;
    String message;

    ResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

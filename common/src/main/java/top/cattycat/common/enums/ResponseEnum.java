package top.cattycat.common.enums;

/**
 * 错误返回信息
 *
 * @author 王金义
 * @date 2021/8/30
 */
public enum ResponseEnum {
    SUCCESS("00000", "Success"),
    SERVER_ERROR("B0001", "服务端错误"),

    SEARCH_NO_RESULT("B0001", "没有搜索到结果"),
    NO_BLOG("B0001", "没有博客"),
    NO_LABELS("B0001", "该仓库没有标签"),
    NO_BLOGS_IN_THE_LABEL("B0001", "该博客没有标签"),

    TOO_MANY_REQUEST("A0501","请求过于频繁, 请稍后再试"),
    HTTP_REQUEST_EXCEPTION("B0001", "HTTP 请求异常"),

    REGISTER_FAILED("B0001", "注册失败"),
    CONNECT_TO_GITHUB_FAILED("B0001", "连接到 GitHub 服务器时失败"),
    AUTHORIZATION_FAILED("B0001", "认证失败"),

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

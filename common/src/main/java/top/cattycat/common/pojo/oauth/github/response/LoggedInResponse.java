package top.cattycat.common.pojo.oauth.github.response;

import lombok.Data;
import lombok.experimental.Accessors;
import top.cattycat.common.pojo.vo.UserVO;

/**
 * @author ηιδΉ
 * @date 2022/1/12
 */
@Data
@Accessors(chain = true)
public class LoggedInResponse {
    private String accessToken;
    private UserVO userInfo;
}

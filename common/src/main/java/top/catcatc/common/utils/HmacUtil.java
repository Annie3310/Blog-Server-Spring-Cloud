package top.catcatc.common.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * 使用 HMAC 16 进制算出 SHA256 值
 * {@link} https://blog.csdn.net/sfb749277979/article/details/107508389
 * @author MikeTeas
 * @date 2021/9/3
 */
@Component
public class HmacUtil {
    private static final String MAC_NAME = "HmacSHA256";
    private static final String ENCODING = "utf-8";

    public static String HmacSHA1Encrypt(String secret,String body) throws Exception {
        Mac sha256_HMAC = Mac.getInstance(MAC_NAME);
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(ENCODING), MAC_NAME);
        sha256_HMAC.init(secretKey);
        byte[] hash = sha256_HMAC.doFinal(body.getBytes(StandardCharsets.UTF_8));
        return byte2Hex(hash);
    }

    /**
     * 将 byte 转为 16 进制
     *
     * @param bytes 字节码
     * @return 转换结果
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }

}

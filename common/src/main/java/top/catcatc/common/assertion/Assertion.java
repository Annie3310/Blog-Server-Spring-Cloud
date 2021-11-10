package top.catcatc.common.assertion;

import top.catcatc.common.enums.ResponseEnum;
import top.catcatc.common.exception.BlogException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author 王金义
 */
public class Assertion {
    private static final Logger logger = LoggerFactory.getLogger(Assertion.class);

    public static void isNull(Object o, ResponseEnum response) {
        if (o == null) {
            logger.warn(getClassNameAndMethodName(response.getMessage()));
            throw new BlogException(response);
        }
    }

    public static void isNull(Object o) {
        if (o == null) {
            logger.warn(getClassNameAndMethodName(ResponseEnum.VALUE_IS_NULL.getMessage()));
            throw new BlogException(ResponseEnum.VALUE_IS_NULL);
        }
    }

    public static void equals(Object o1, Object o2, ResponseEnum response) {
        if (!Objects.equals(o1, o2)) {
            logger.warn(getClassNameAndMethodName(response.getMessage()));
            throw new BlogException(response);
        }
    }

    public static void equals(Object o1, Object o2) {
        if (!Objects.equals(o1, o2)) {
            logger.warn(getClassNameAndMethodName(ResponseEnum.VALUES_ARE_NOT_EQUALS.getMessage()));
            throw new BlogException(ResponseEnum.VALUES_ARE_NOT_EQUALS);
        }
    }

    public static void isEmpty(Collection o, ResponseEnum response) {
        if (o.isEmpty()) {
            logger.warn(getClassNameAndMethodName(response.getMessage()));
            throw new BlogException(response);
        }
    }

    public static void isEmpty(Map o, ResponseEnum response) {
        if (o.isEmpty()) {
            logger.warn(getClassNameAndMethodName(response.getMessage()));
            throw new BlogException(response);
        }
    }

    private static String getClassNameAndMethodName(String message) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        StackTraceElement stackTraceElement = stackTrace[2];
        return stackTraceElement.getClassName() + ": " + stackTraceElement.getMethodName() + "(): " + message;
    }
}

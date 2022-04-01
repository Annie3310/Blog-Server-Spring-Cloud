package top.cattycat.common.util;

import java.lang.reflect.Field;

/**
 * @author 王金义
 * @date 2022/3/28
 */
public class BeanUtils {
    public static boolean isAllFieldsNull(Object obj){
        Class<?> aClass = obj.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field item : declaredFields) {
            item.setAccessible(true);
            try {
                if (item.get(obj) != null) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static void test() {

    }
}

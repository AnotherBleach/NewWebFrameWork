package cn.edu.bupt.util;
import java.lang.reflect.Field;
public class ReflectionUtil {

    public static <T> T genBean(Class<T> cls) {

        T b = null;
        try {
            b = cls.newInstance();
        } catch (InstantiationException e) {
            System.out.println("实例化类对象出错!");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return b;

    }

    public static void setField(Object object,Field field,Object value)
    {
        System.out.println(object.getClass()+" "+field.getType()+" "+value.getClass());
        field.setAccessible(true);
        try {
            field.set(object,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}

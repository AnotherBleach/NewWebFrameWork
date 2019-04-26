package cn.edu.bupt.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanUtil {
    private static final Map<Class<?>, Object> BEAN_CONTAINER = new HashMap<Class<?>, Object>();


    public static Map<Class<?>, Object> getBeanContainer() {
        return BEAN_CONTAINER;
    }



    public <T> T getBean(Class<T> cls) {

        if (cls == null) return null;
        if (!BEAN_CONTAINER.containsKey(cls)) return null;
        return (T) BEAN_CONTAINER.get(cls);
    }

    public boolean setBean(Class<?> cls, Object object) {

        if (cls == null) return false;
        BEAN_CONTAINER.put(cls, object);
        return true;

    }

    static {

        Set<Class<?>> classSet = ClassParser.getBeanClassSet();
        if (!CollectionUtil.setIsEmpty(classSet)) {
            for (Class<?> cls : classSet) {

                try {
                    BEAN_CONTAINER.put(cls, cls.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}

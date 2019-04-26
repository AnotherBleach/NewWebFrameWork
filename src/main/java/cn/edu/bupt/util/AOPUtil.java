package cn.edu.bupt.util;

import cn.edu.bupt.annotation.Aspect;
import cn.edu.bupt.aop.Proxy;
import cn.edu.bupt.aop.ProxyExecutor;

import java.lang.annotation.Annotation;
import java.util.*;

public class AOPUtil {

    /**
     * 获取所有被Aspect注解标记的类
     *
     * @return
     */
    public static Set<Class<?>> getAllAspectClassSet() {

        return ClassParser.getAspectClassSet();
    }

    public static Map<Class<?>, List<Proxy>> getRelationBetweenClassAndProxy() {

        Set<Class<?>> aspectClassSet = getAllAspectClassSet();
        Set<Class<?>> allClassSet = ClassParser.getClasses();
        Map<Class<?>, List<Proxy>> result = new HashMap<>();
        for (Class<?> cls : aspectClassSet) {   // aspect class is also a proxy class

            Aspect aspect = cls.getAnnotation(Aspect.class);
            Class<? extends Annotation> annotation = aspect.value();
            for (Class<?> suspect : allClassSet) {

                if (suspect.isAnnotationPresent(annotation)) {
                    List<Proxy> temp = result.get(suspect);
                    if (temp == null)
                        temp = new ArrayList<>();
                    temp.add((Proxy) BeanUtil.getBeanContainer().get(cls));
                    result.put(suspect, temp);

                }


            }


        }
        return result;


    }

    public static Object genObjectFromList(Class<?> cls, List<Proxy> proxyList) {
        System.out.println(cls.getName()+" 有 "+proxyList.size()+"个代理");
        Object result = null;
        ProxyExecutor proxyExecutor = new ProxyExecutor();
        proxyExecutor.setProxyList(proxyList);
        result = proxyExecutor.getProxyObject(cls);
        return result;
    }

    public static Map<Class<?>, Object> generateProxyObject() {
        Map<Class<?>, Object> result = new HashMap<>();
        Map<Class<?>, List<Proxy>> target = getRelationBetweenClassAndProxy();
        for (Map.Entry<Class<?>, List<Proxy>> entry : target.entrySet()) {

            Object obj = genObjectFromList(entry.getKey(), entry.getValue());
            result.put(entry.getKey(), obj);
        }
        return result;
    }

    static {

        //使用代理对象替换实际对象
        Map<Class<?>, Object> map = generateProxyObject();
        Map<Class<?>, Object> BEAN_MAP = BeanUtil.getBeanContainer();
        for (Map.Entry<Class<?>, Object> entry : map.entrySet()) {
            System.out.println("之前 "+entry.getKey().getName()+"<->"+BEAN_MAP.get(entry.getKey()).getClass().getName());
            BEAN_MAP.put(entry.getKey(), entry.getValue());
            System.out.println("之后"+entry.getKey().getName()+"<->"+BEAN_MAP.get(entry.getKey()).getClass().getName());

        }

    }

}

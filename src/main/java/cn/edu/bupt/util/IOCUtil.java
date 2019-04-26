package cn.edu.bupt.util;

import cn.edu.bupt.annotation.Autowired;
import cn.edu.bupt.rpc.RPCClient;

import java.util.Map;
import java.lang.reflect.Field;

public class IOCUtil {

    static {

        Map<Class<?>, Object> bean_container = BeanUtil.getBeanContainer();
        if (!CollectionUtil.mapIsEmpty(bean_container)) {

            for (Map.Entry<Class<?>, Object> entry : bean_container.entrySet()) {

                Class<?> cls = entry.getKey();
                Object object = entry.getValue();
                Field[] fields = cls.getDeclaredFields();
                for (Field field : fields) {

                    if (field.isAnnotationPresent(Autowired.class)) {
                        Class<?> type = field.getType();
                        System.out.println(type.getName() + " is interface = " + ClassParser.isInterface(type));
                        if (ClassParser.isInterface(type)) {


                            Autowired autowired = field.getAnnotation(Autowired.class);
                            System.out.println("0、" + autowired.annotationType());
                            Class<?> impl = autowired.implClass();
                            System.out.println("1、" + impl.getName());
                            Object value = null;
                            if (impl.equals(RPCClient.class)) {

                                System.out.println("here");
                                    value = new RPCClient().getProxyObject(type);

                            }


                            else value = bean_container.get(impl);

                            System.out.println("2、" + value.getClass());
                            ReflectionUtil.setField(object, field, value);

                        } else {
                            Object value = bean_container.get(type);
                            ReflectionUtil.setField(object, field, value);
                        }
                    }

                }

            }


        }
    }
}

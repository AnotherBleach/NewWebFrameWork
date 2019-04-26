package cn.edu.bupt.util;

import cn.edu.bupt.annotation.RequestMapping;
import cn.edu.bupt.bean.Handler;
import cn.edu.bupt.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HandlerMappingUtil {
    // request
    private static Map<Request, Handler> requestHandlerMap = new HashMap<>();

    public static Map<Request, Handler> getRequestHandlerMap() {
        return requestHandlerMap;
    }

    public static Handler getHandler(Request request) {
        if (requestHandlerMap.containsKey(request))
            return requestHandlerMap.get(request);
        return null;

    }

   static {

        Set<Class<?>> controllerClassSet = ClassParser.getControllerClassSet();
        for (Class<?> cls : controllerClassSet) {

            Method[] methods = cls.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {

                methods[i].setAccessible(true);
                if (methods[i].isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = methods[i].getAnnotation(RequestMapping.class);
                    String method = requestMapping.method();
                    String path = requestMapping.path();
                    Request request = new Request();
                    request.setPath(path);
                    request.setMethod(method);
                    Handler handler = new Handler();
                    handler.setArgs(null);
                    handler.setObject(BeanUtil.getBeanContainer().get(cls));
                    handler.setMethod(methods[i]);
                    requestHandlerMap.put(request, handler);

                }


            }


        }

    }
}

package cn.edu.bupt.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Handler {
    private Object object;
    private Method method;
    private Args args;

    public Object invoke() {
        Object result = null;
        try {
            result = method.invoke(object, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Object getObject() {
        return object;
    }

    public Method getMethod() {
        return method;
    }

    public Args getArgs() {
        return args;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setArgs(Args args) {
        this.args = args;
    }
}

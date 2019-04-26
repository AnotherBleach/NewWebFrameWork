package cn.edu.bupt.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ProxyExecutor implements MethodInterceptor {
    private List<Proxy> proxyList;
    private int index;

    public ProxyExecutor() {
        proxyList = new ArrayList<>();
        index = 0;
    }

    public ProxyExecutor(List<Proxy> proxyList, int index) {
        this.proxyList = proxyList;
        this.index = index;
    }

    public void setProxyList(List<Proxy> proxyList) {
        this.proxyList = proxyList;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Proxy> getProxyList() {
        return proxyList;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object result = null;
        Proxy current = proxyList.get(index);
        current.before();
        index++;
        if (index < proxyList.size()) {

            result = intercept(obj, method, args, proxy); //继续递归执行
        } else {

            index = 0;
            result = proxy.invokeSuper(obj, args);//否则调用proxy.invokeSuper

        }
        current.after();
        return result;

    }

    public Object getProxyObject(Class<?> cls) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(this);
        return enhancer.create();

    }

}

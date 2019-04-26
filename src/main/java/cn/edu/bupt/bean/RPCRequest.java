package cn.edu.bupt.bean;

import java.io.Serializable;

public class RPCRequest implements Serializable {

    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] parameterType;
    private Object[] paramterValue;

    public String getRequestId() {
        return requestId;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParameterType() {
        return parameterType;
    }

    public Object[] getParamterValue() {
        return paramterValue;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParameterType(Class<?>[] parameterType) {
        this.parameterType = parameterType;
    }

    public void setParamterValue(Object[] paramterValue) {
        this.paramterValue = paramterValue;
    }
}

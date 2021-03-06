package cn.edu.bupt.bean;

import java.io.Serializable;

public class RPCResponse implements Serializable {
    private String requestId;
    private Throwable error;
    private Object result;

    public String getRequestId() {
        return requestId;
    }

    public Throwable getError() {
        return error;
    }

    public Object getResult() {
        return result;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}

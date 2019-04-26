package cn.edu.bupt.bean;

import java.util.Objects;

public class Request {

    private String method;
    private String path;

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(method, request.method) &&
                Objects.equals(path, request.path);
    }

    @Override
    public int hashCode() {

        return Objects.hash(method, path);
    }
}

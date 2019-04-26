package cn.edu.bupt.util;

import com.google.gson.Gson;

public class JSONUtil {

    public static String toJson(Object object) {
        return object != null ? new Gson().toJson(object) : "";
    }

    public static <T> T fromJson(String json, Class<T> cls) {
        if (StringUtil.isEmpty(json)) return null;
        return new Gson().fromJson(json, cls);


    }
}

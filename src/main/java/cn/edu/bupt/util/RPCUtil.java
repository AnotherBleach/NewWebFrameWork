package cn.edu.bupt.util;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class RPCUtil {
    private static Map<String, Object> RPCMap = new HashMap<>();

    public static Map<String, Object> getRPCMap() {
        return RPCMap;
    }

    public static void setRPCMap(Map<String, Object> RPCMap) {
        RPCUtil.RPCMap = RPCMap;
    }
}

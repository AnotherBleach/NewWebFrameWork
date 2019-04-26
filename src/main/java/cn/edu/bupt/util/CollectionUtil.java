package cn.edu.bupt.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.SetUtils;

import java.util.Map;
import java.util.Set;

public class CollectionUtil {

    public static boolean setIsEmpty(Set<?> set) {
        if (set == null) return true;
        return CollectionUtils.isEmpty(set);

    }

    public static boolean mapIsEmpty(Map<?, ?> map) {
        if (map == null) return true;
        return MapUtils.isEmpty(map);

    }

}

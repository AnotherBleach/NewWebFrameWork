package cn.edu.bupt.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
    public static boolean  isEmpty(String str)
    {
        if(str == null)return true;
        return StringUtils.isEmpty(str.trim());

    }
}

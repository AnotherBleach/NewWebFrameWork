package cn.edu.bupt;

import cn.edu.bupt.util.*;

public class Initializer {

    public static void init() {

        Class<?>[] classes = new Class<?>[]{

                ClassParser.class,
                BeanUtil.class,
                AOPUtil.class,
                IOCUtil.class,
                HandlerMappingUtil.class

        };
        for (Class cls : classes) {
            try {
                Class.forName(cls.getName());

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}

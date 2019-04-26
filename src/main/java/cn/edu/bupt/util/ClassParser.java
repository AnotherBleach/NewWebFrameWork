package cn.edu.bupt.util;

import cn.edu.bupt.annotation.*;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ClassParser {


    private static Set<Class<?>> classes = new HashSet<Class<?>>();

    public static Set<Class<?>> getClasses() {
        return classes;
    }

    public static void initClassSet(String basePackage) throws IOException {
        System.out.println("！！！basep = "+basePackage);
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(basePackage.replace(".", "/"));
        while (urls.hasMoreElements()) {

            URL url = urls.nextElement();
            if (url.getProtocol().equals("file")) { //文件夹或普通文件

                handle(url.getPath(), basePackage);

            } else if (url.getProtocol().equals("jar")) {

                //暂不考虑
            }

        }


    }

    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : classes) {

            if (cls.isAnnotationPresent(Controller.class))
                classSet.add(cls);
        }
        return classSet;

    }

    public static Set<Class<?>> getServiceClassSet() {

        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : classes) {

            if (cls.isAnnotationPresent(Service.class))
                classSet.add(cls);
        }
        return classSet;
    }

    public static Set<Class<?>> getComponentClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : classes) {

            if (cls.isAnnotationPresent(Component.class))
                classSet.add(cls);
        }
        return classSet;
    }

    public static Set<Class<?>> getAspectClassSet() {

        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : classes) {

            if (cls.isAnnotationPresent(Aspect.class))
                classSet.add(cls);
        }
        return classSet;

    }

    public static boolean isInterface(Class<?> cls) {

        return cls.isInterface();

    }


    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        Set<Class<?>> controllerClassSet = getControllerClassSet();
        Set<Class<?>> serviceClassSet = getServiceClassSet();
        Set<Class<?>> componentClassSet = getComponentClassSet();
        Set<Class<?>> aspectClassSet = getAspectClassSet();
        classSet.addAll(controllerClassSet);
        classSet.addAll(serviceClassSet);
        classSet.addAll(componentClassSet);
        classSet.addAll(aspectClassSet);
        return classSet;

    }

    public static Set<Class<?>> getRpcClassSet() {

        System.out.println("总类有 " + classes.size());
        Set<Class<?>> classSet = new HashSet<Class<?>>();

        for (Class<?> cls : classes) {
            System.out.println(cls.getName() + " " + cls.isAnnotationPresent(RpcService.class));
            Annotation[] annotations = cls.getAnnotations();
            if (annotations.length == 0) System.out.println("fuck you ");
            for (int i = 0; i < annotations.length; i++)
                System.out.println(annotations[i].getClass().getName());


            if (cls.isAnnotationPresent(RpcService.class))
                classSet.add(cls);
        }
        return classSet;
    }

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }


    public static Set<Class<?>> handle(String pathName, final String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        File[] file = new File(pathName).listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory() || pathname.getName().endsWith(".class");
            }
        });

        System.out.println(file.length+" file length");
        for (int i = 0; i < file.length; i++) {
            System.out.println("file: = "+file[i].getName());
            if (file[i].isFile()) {

                Class<?> cls = getClass(packageName + "." + file[i].getName().substring(0, file[i].getName().lastIndexOf(".")));
                classes.add(cls);
            } else
                handle(pathName + "/" + file[i].getName(), packageName + "." + file[i].getName());

        }


        return classSet;
    }

    public static Class<?> getClass(String className) {
        System.out.println(className + " is loaded!");
        Class<?> cls = null;
        try {
            cls = Class.forName(className, false, getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cls;

    }

    static {

        String basePackage = XMLParser.getConfigBean("another.xml").getBasepackage();
        try {
            initClassSet(basePackage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

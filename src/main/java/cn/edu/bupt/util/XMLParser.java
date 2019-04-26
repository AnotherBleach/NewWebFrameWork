package cn.edu.bupt.util;

import cn.edu.bupt.bean.ConfigBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLParser {

    public static Document getDocument(String path) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            String fileName = Thread.currentThread().getContextClassLoader().getResource(path).getPath();
            document = reader.read(fileName);
        } catch (DocumentException e) {
            document = null;
            e.printStackTrace();
        } finally {
            return document;
        }

    }

    public static ConfigBean getConfigBean(String path) {

        Document document = getDocument(path);
        if (document == null) return null;
        ConfigBean configBean = new ConfigBean();

        Element another = document.getRootElement();
        Element view = another.element("view");
        Element componentScan = another.element("component-scan");
        String basePackage = componentScan.elementText("basepackage");
        String viewPagePath = view.elementText("page");
        String asset = view.elementText("asset");
        configBean.setBasepackage(basePackage);
        configBean.setViewPagePath(viewPagePath);
        configBean.setViewAssetPath(asset);

        return configBean;


    }

    public static String getNodeValue(Element element) {

        String data = "";
        try {

            data = element.getTextTrim();

        } catch (NullPointerException e) {

            System.out.println("getNodeValue error!");
            e.printStackTrace();

        }
        return data;

    }
}

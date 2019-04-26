package cn.edu.bupt.bean;

public class ConfigBean {
    private String basepackage;
    private String viewPagePath;
    private String viewAssetPath;

    public String getBasepackage() {
        return basepackage;
    }

    public String getViewPagePath() {
        return viewPagePath;
    }

    public String getViewAssetPath() {
        return viewAssetPath;
    }

    public void setBasepackage(String basepackage) {
        this.basepackage = basepackage;
    }

    public void setViewPagePath(String viewPagePath) {
        this.viewPagePath = viewPagePath;
    }

    public void setViewAssetPath(String viewAssetPath) {
        this.viewAssetPath = viewAssetPath;
    }
}

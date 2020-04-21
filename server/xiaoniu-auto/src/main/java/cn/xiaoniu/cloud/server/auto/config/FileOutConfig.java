package cn.xiaoniu.cloud.server.auto.config;

import cn.xiaoniu.cloud.server.auto.config.po.TableInfo;


public abstract class FileOutConfig {
    private String templatePath;

    public FileOutConfig() {
    }

    public FileOutConfig(String templatePath) {
        this.templatePath = templatePath;
    }


    public abstract String outputFile(TableInfo paramTableInfo);


    public String getTemplatePath() {
        return this.templatePath;
    }


    public FileOutConfig setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
        return this;
    }
}

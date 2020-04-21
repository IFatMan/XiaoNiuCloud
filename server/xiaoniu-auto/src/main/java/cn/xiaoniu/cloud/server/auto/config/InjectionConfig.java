package cn.xiaoniu.cloud.server.auto.config;

import cn.xiaoniu.cloud.server.auto.config.builder.ConfigBuilder;

import java.util.List;
import java.util.Map;


public abstract class InjectionConfig {
    private ConfigBuilder config;
    private Map<String, Object> map;
    private List<FileOutConfig> fileOutConfigList;

    public abstract void initMap();

    public ConfigBuilder getConfig() {
        return this.config;
    }


    public InjectionConfig setConfig(ConfigBuilder config) {
        this.config = config;
        return this;
    }


    public Map<String, Object> getMap() {
        return this.map;
    }


    public InjectionConfig setMap(Map<String, Object> map) {
        this.map = map;
        return this;
    }


    public List<FileOutConfig> getFileOutConfigList() {
        return this.fileOutConfigList;
    }


    public InjectionConfig setFileOutConfigList(List<FileOutConfig> fileOutConfigList) {
        this.fileOutConfigList = fileOutConfigList;
        return this;
    }
}

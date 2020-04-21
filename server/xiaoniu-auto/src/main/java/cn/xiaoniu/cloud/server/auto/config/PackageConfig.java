package cn.xiaoniu.cloud.server.auto.config;

import com.baomidou.mybatisplus.toolkit.StringUtils;


public class PackageConfig {
    private String parent = "com.baomidou";


    private String moduleName = null;


    private String entity = "entity";


    private String service = "service";


    private String serviceImpl = "service.impl";


    private String mapper = "mapper";


    private String xml = "mapper.xml";

    private String vo = "vo";

    private String controller = "web";

    private boolean outUseParentPackage = true;

    public String getParent() {
        if (StringUtils.isNotEmpty(this.moduleName)) {
            return this.parent + "." + this.moduleName;
        }
        return this.parent;
    }

    public PackageConfig setParent(String parent) {
        this.parent = parent;
        return this;
    }


    public String getModuleName() {
        return this.moduleName;
    }


    public PackageConfig setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }


    public String getEntity() {
        return this.entity;
    }


    public PackageConfig setEntity(String entity) {
        this.entity = entity;
        return this;
    }


    public String getService() {
        return this.service;
    }


    public PackageConfig setService(String service) {
        this.service = service;
        return this;
    }


    public String getServiceImpl() {
        return this.serviceImpl;
    }


    public PackageConfig setServiceImpl(String serviceImpl) {
        this.serviceImpl = serviceImpl;
        return this;
    }


    public String getMapper() {
        return this.mapper;
    }


    public PackageConfig setMapper(String mapper) {
        this.mapper = mapper;
        return this;
    }


    public String getXml() {
        return this.xml;
    }


    public PackageConfig setXml(String xml) {
        this.xml = xml;
        return this;
    }

    public String getController() {
        if (StringUtils.isEmpty(this.controller)) {
            return "web";
        }
        return this.controller;
    }

    public PackageConfig setController(String controller) {
        this.controller = controller;
        return this;
    }


    public String getVo() {
        return this.vo;
    }

    public PackageConfig setVo(String vo) {
        this.vo = vo;
        return this;
    }

    public boolean outUseParentPackage() {
        return outUseParentPackage;
    }

    public PackageConfig setOutUseParentPackage(boolean outUseParentPackage) {
        this.outUseParentPackage = outUseParentPackage;
        return this;
    }
}

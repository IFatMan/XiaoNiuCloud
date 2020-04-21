package cn.xiaoniu.cloud.server.auto.config;


public class TemplateConfig {
    private String entity = "/templates/entity.java";

    private String service = "/templates/service.java";

    private String serviceImpl = "/templates/serviceImpl.java";

    private String mapper = "/templates/mapper.java";

    private String xml = "/templates/mapper.xml";

    private String controller = "/templates/controller.java";

    private String vo = "/templates/vo.java";


    public String getEntity(boolean kotlin) {
        return kotlin ? "/templates/entity.kt" : this.entity;
    }


    public TemplateConfig setEntity(String entity) {
        this.entity = entity;
        return this;
    }


    public String getService() {
        return this.service;
    }


    public TemplateConfig setService(String service) {
        this.service = service;
        return this;
    }


    public String getServiceImpl() {
        return this.serviceImpl;
    }


    public TemplateConfig setServiceImpl(String serviceImpl) {
        this.serviceImpl = serviceImpl;
        return this;
    }


    public String getMapper() {
        return this.mapper;
    }


    public TemplateConfig setMapper(String mapper) {
        this.mapper = mapper;
        return this;
    }


    public String getXml() {
        return this.xml;
    }


    public TemplateConfig setXml(String xml) {
        this.xml = xml;
        return this;
    }


    public String getController() {
        return this.controller;
    }


    public TemplateConfig setController(String controller) {
        this.controller = controller;
        return this;
    }

    public String getVo() {
        return vo;
    }

    public TemplateConfig setVo(String vo) {
        this.vo = vo;
        return this;
    }
}

package cn.xiaoniu.cloud.server.web.swagger;


import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.schema.ModelRef;

import java.util.List;

/**
 * Swagger2 配置信息
 *
 * @author 孔明
 * @date 2020/4/22 14:55
 * @description cn.xiaoniu.cloud.server.web.properties.Swagger2Properties
 */
@ConfigurationProperties(prefix = "swagger2")
public class Swagger2Properties {

    /**
     * 是否开启Swagger2
     */
    private Boolean enable;

    /**
     * Swagger2 扫描路径
     */
    private String basePackage;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档描述
     */
    private String description;

    /**
     * 版本
     */
    private String version;

    /**
     * 联系人
     */
    private String concatName;

    /**
     * 联系邮箱
     */
    private String concatEmail;

    /**
     * 联系网站地址
     */
    private String concatUrl;

    /**
     * 请求头
     */
    private List<Header> header;

    /**
     * 请求头
     */
    public static class Header {

        /**
         * 请求头名称
         */
        private String name;

        /**
         * 请求头描述
         */
        private String description;

        /**
         * 是否必填
         */
        private Boolean required;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getRequired() {
            return required;
        }

        public void setRequired(Boolean required) {
            this.required = required;
        }
    }

    /**
     * 常量
     */
    public interface Constatnt {

        /**
         * Title 默认值
         */
        String TITLE = "System Title";

        /**
         * Description 默认值
         */
        String DESCRIPTION = "System Description";

        /**
         * Version 默认值
         */
        String VERSION = "1.0.0";

        String HEADER = "header";

        ModelRef MODEL_REF = new ModelRef("string");

    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getConcatName() {
        return concatName;
    }

    public void setConcatName(String concatName) {
        this.concatName = concatName;
    }

    public String getConcatEmail() {
        return concatEmail;
    }

    public void setConcatEmail(String concatEmail) {
        this.concatEmail = concatEmail;
    }

    public String getConcatUrl() {
        return concatUrl;
    }

    public void setConcatUrl(String concatUrl) {
        this.concatUrl = concatUrl;
    }

    public List<Header> getHeader() {
        return header;
    }

    public void setHeader(List<Header> header) {
        this.header = header;
    }
}

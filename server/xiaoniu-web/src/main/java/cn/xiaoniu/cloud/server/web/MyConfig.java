package cn.xiaoniu.cloud.server.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 孔明
 * @date 2020/4/22 14:36
 * @description cn.xiaoniu.cloud.server.web.MyConfig
 */
@Configuration
@ConfigurationProperties(prefix = "myapp.prefix")
@EnableConfigurationProperties(MyConfig.class)
public class MyConfig {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

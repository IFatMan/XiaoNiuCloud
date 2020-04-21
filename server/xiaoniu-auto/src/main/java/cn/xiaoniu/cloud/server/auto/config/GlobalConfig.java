package cn.xiaoniu.cloud.server.auto.config;

import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class GlobalConfig {
    private String outputDir = "D://";

    private boolean fileOverride = false;

    private boolean open = true;

    private boolean enableCache = true;

    private String author;

    private boolean kotlin = false;

    private boolean activeRecord = true;

    private boolean baseResultMap = false;

    private boolean baseColumnList = false;

    private String mapperName;

    private String xmlName;

    private String serviceName;

    private String serviceImplName;

    private String controllerName;

    private String voName;

    private IdType idType;

}

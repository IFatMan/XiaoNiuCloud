//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.xiaoniu.cloud.server.auto.engine;

import cn.xiaoniu.cloud.server.auto.config.GlobalConfig;
import cn.xiaoniu.cloud.server.auto.config.TemplateConfig;
import cn.xiaoniu.cloud.server.auto.config.builder.ConfigBuilder;
import cn.xiaoniu.cloud.server.auto.config.po.TableInfo;
import cn.xiaoniu.cloud.server.auto.config.InjectionConfig;
import cn.xiaoniu.cloud.server.auto.config.FileOutConfig;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTemplateEngine {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);
    private ConfigBuilder configBuilder;

    public AbstractTemplateEngine() {
    }

    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }

    public AbstractTemplateEngine batchOutput() {
        try {
            List<TableInfo> tableInfoList = this.getConfigBuilder().getTableInfoList();
            Iterator i$ = tableInfoList.iterator();

            while (i$.hasNext()) {
                TableInfo tableInfo = (TableInfo) i$.next();
                Map<String, Object> objectMap = this.getObjectMap(tableInfo);
                Map<String, String> pathInfo = this.getConfigBuilder().getPathInfo();
                TemplateConfig template = this.getConfigBuilder().getTemplate();
                InjectionConfig injectionConfig = this.getConfigBuilder().getInjectionConfig();
                if (null != injectionConfig) {
                    injectionConfig.initMap();
                    objectMap.put("cfg", injectionConfig.getMap());
                    List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                    if (CollectionUtils.isNotEmpty(focList)) {
                        Iterator iterator = focList.iterator();

                        while (iterator.hasNext()) {
                            FileOutConfig foc = (FileOutConfig) iterator.next();
                            if (this.isCreate(foc.outputFile(tableInfo))) {
                                this.writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                            }
                        }
                    }
                }

                String entityName = tableInfo.getEntityName();
                String controllerFile;
                if (null != entityName && null != pathInfo.get("entity_path")) {
                    controllerFile = String.format((String) pathInfo.get("entity_path") + File.separator + "%s" + this.suffixJavaOrKt(), entityName);
                    if (this.isCreate(controllerFile)) {
                        this.writer(objectMap, this.templateFilePath(template.getEntity(this.getConfigBuilder().getGlobalConfig().isKotlin())), controllerFile);
                    }
                }

                if (null != tableInfo.getMapperName() && null != pathInfo.get("mapper_path")) {
                    controllerFile = String.format((String) pathInfo.get("mapper_path") + File.separator + tableInfo.getMapperName() + this.suffixJavaOrKt(), entityName);
                    if (this.isCreate(controllerFile)) {
                        this.writer(objectMap, this.templateFilePath(template.getMapper()), controllerFile);
                    }
                }

                if (null != tableInfo.getXmlName() && null != pathInfo.get("xml_path")) {
                    controllerFile = String.format((String) pathInfo.get("xml_path") + File.separator + tableInfo.getXmlName() + ".xml", entityName);
                    if (this.isCreate(controllerFile)) {
                        this.writer(objectMap, this.templateFilePath(template.getXml()), controllerFile);
                    }
                }

                if (null != tableInfo.getServiceName() && null != pathInfo.get("service_path")) {
                    controllerFile = String.format((String) pathInfo.get("service_path") + File.separator + tableInfo.getServiceName() + this.suffixJavaOrKt(), entityName);
                    if (this.isCreate(controllerFile)) {
                        this.writer(objectMap, this.templateFilePath(template.getService()), controllerFile);
                    }
                }

                if (null != tableInfo.getServiceImplName() && null != pathInfo.get("serviceimpl_path")) {
                    controllerFile = String.format((String) pathInfo.get("serviceimpl_path") + File.separator + tableInfo.getServiceImplName() + this.suffixJavaOrKt(), entityName);
                    if (this.isCreate(controllerFile)) {
                        this.writer(objectMap, this.templateFilePath(template.getServiceImpl()), controllerFile);
                    }
                }

                if (null != tableInfo.getControllerName() && null != pathInfo.get("controller_path")) {
                    controllerFile = String.format((String) pathInfo.get("controller_path") + File.separator + tableInfo.getControllerName() + this.suffixJavaOrKt(), entityName);
                    if (this.isCreate(controllerFile)) {
                        this.writer(objectMap, this.templateFilePath(template.getController()), controllerFile);
                    }
                }

                if (null != tableInfo.getVoName() && null != pathInfo.get("vo_path")) {
                    controllerFile = String.format((String) pathInfo.get("vo_path") + File.separator + tableInfo.getVoName() + this.suffixJavaOrKt(), entityName);
                    if (this.isCreate(controllerFile)) {
                        this.writer(objectMap, this.templateFilePath(template.getVo()), controllerFile);
                    }
                }
            }
        } catch (Exception var11) {
            logger.error("无法创建文件，请检查配置信息！", var11);
        }

        return this;
    }

    public abstract void writer(Map<String, Object> var1, String var2, String var3) throws Exception;

    public AbstractTemplateEngine mkdirs() {
        Map<String, String> pathInfo = this.getConfigBuilder().getPathInfo();
        Iterator i$ = pathInfo.entrySet().iterator();

        while (i$.hasNext()) {
            Entry<String, String> entry = (Entry) i$.next();
            File dir = new File((String) entry.getValue());
            if (!dir.exists()) {
                boolean result = dir.mkdirs();
                if (result) {
                    logger.debug("创建目录： [" + (String) entry.getValue() + "]");
                }
            }
        }

        return this;
    }

    public void open() {
        if (this.getConfigBuilder().getGlobalConfig().isOpen()) {
            try {
                String osName = System.getProperty("os.name");
                if (osName != null) {
                    if (osName.contains("Mac")) {
                        Runtime.getRuntime().exec("open " + this.getConfigBuilder().getGlobalConfig().getOutputDir());
                    } else if (osName.contains("Windows")) {
                        Runtime.getRuntime().exec("cmd /c start " + this.getConfigBuilder().getGlobalConfig().getOutputDir());
                    } else {
                        logger.debug("文件输出目录:" + this.getConfigBuilder().getGlobalConfig().getOutputDir());
                    }
                }
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

    }

    public Map<String, Object> getObjectMap(TableInfo tableInfo) {
        Map<String, Object> objectMap = new HashMap();
        ConfigBuilder config = this.getConfigBuilder();
        if (config.getStrategyConfig().isControllerMappingHyphenStyle()) {
            objectMap.put("controllerMappingHyphenStyle", config.getStrategyConfig().isControllerMappingHyphenStyle());
            objectMap.put("controllerMappingHyphen", StringUtils.camelToHyphen(tableInfo.getEntityPath()));
        }

        objectMap.put("restControllerStyle", config.getStrategyConfig().isRestControllerStyle());
        objectMap.put("package", config.getPackageInfo());
        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("idType", globalConfig.getIdType() == null ? null : globalConfig.getIdType().toString());
        objectMap.put("logicDeleteFieldName", config.getStrategyConfig().getLogicDeleteFieldName());
        objectMap.put("versionFieldName", config.getStrategyConfig().getVersionFieldName());
        objectMap.put("activeRecord", globalConfig.isActiveRecord());
        objectMap.put("kotlin", globalConfig.isKotlin());
        objectMap.put("date", (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
        objectMap.put("table", tableInfo);
        objectMap.put("enableCache", globalConfig.isEnableCache());
        objectMap.put("baseResultMap", globalConfig.isBaseResultMap());
        objectMap.put("baseColumnList", globalConfig.isBaseColumnList());
        objectMap.put("entity", tableInfo.getEntityName());
        objectMap.put("entityColumnConstant", config.getStrategyConfig().isEntityColumnConstant());
        objectMap.put("entityBuilderModel", config.getStrategyConfig().isEntityBuilderModel());
        objectMap.put("entityLombokModel", config.getStrategyConfig().isEntityLombokModel());
        objectMap.put("entityBooleanColumnRemoveIsPrefix", config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix());
        objectMap.put("superEntityClass", this.getSuperClassName(config.getSuperEntityClass()));
        objectMap.put("superMapperClassPackage", config.getSuperMapperClass());
        objectMap.put("superMapperClass", this.getSuperClassName(config.getSuperMapperClass()));
        objectMap.put("superServiceClassPackage", config.getSuperServiceClass());
        objectMap.put("superServiceClass", this.getSuperClassName(config.getSuperServiceClass()));
        objectMap.put("superServiceImplClassPackage", config.getSuperServiceImplClass());
        objectMap.put("superServiceImplClass", this.getSuperClassName(config.getSuperServiceImplClass()));
        objectMap.put("superControllerClassPackage", config.getSuperControllerClass());
        objectMap.put("superControllerClass", this.getSuperClassName(config.getSuperControllerClass()));
        return objectMap;
    }

    private String getSuperClassName(String classPath) {
        return StringUtils.isEmpty(classPath) ? null : classPath.substring(classPath.lastIndexOf(".") + 1);
    }

    public abstract String templateFilePath(String var1);

    protected boolean isCreate(String filePath) {
        File file = new File(filePath);
        boolean exist = file.exists();
        if (!exist) {
            this.mkDir(file.getParentFile());
        }

        return !exist || this.getConfigBuilder().getGlobalConfig().isFileOverride();
    }

    protected void mkDir(File file) {
        if (null != file) {
            if (file.getParentFile().exists()) {
                file.mkdir();
            } else {
                this.mkDir(file.getParentFile());
                file.mkdir();
            }
        }

    }

    protected String suffixJavaOrKt() {
        return this.getConfigBuilder().getGlobalConfig().isKotlin() ? ".kt" : ".java";
    }

    public ConfigBuilder getConfigBuilder() {
        return this.configBuilder;
    }

    public AbstractTemplateEngine setConfigBuilder(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }
}

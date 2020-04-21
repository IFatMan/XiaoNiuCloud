//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.xiaoniu.cloud.server.auto.config.builder;

import cn.xiaoniu.cloud.server.auto.config.GlobalConfig;
import cn.xiaoniu.cloud.server.auto.config.PackageConfig;
import cn.xiaoniu.cloud.server.auto.config.TemplateConfig;
import cn.xiaoniu.cloud.server.auto.config.po.TableField;
import cn.xiaoniu.cloud.server.auto.config.po.TableFill;
import cn.xiaoniu.cloud.server.auto.config.po.TableInfo;
import cn.xiaoniu.cloud.server.auto.config.rules.DbType;
import cn.xiaoniu.cloud.server.auto.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import cn.xiaoniu.cloud.server.auto.config.InjectionConfig;
import cn.xiaoniu.cloud.server.auto.config.DataSourceConfig;
import cn.xiaoniu.cloud.server.auto.config.IDbQuery;
import cn.xiaoniu.cloud.server.auto.config.StrategyConfig;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigBuilder {
    private final TemplateConfig template;
    private final DataSourceConfig dataSourceConfig;
    private Connection connection;
    private IDbQuery dbQuery;
    private String superEntityClass;
    private String superMapperClass;
    private String superServiceClass;
    private String superServiceImplClass;
    private String superControllerClass;
    private List<TableInfo> tableInfoList;
    private Map<String, String> packageInfo;
    private Map<String, String> pathInfo;
    private StrategyConfig strategyConfig;
    private GlobalConfig globalConfig;
    private InjectionConfig injectionConfig;

    public ConfigBuilder(PackageConfig packageConfig, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, TemplateConfig template, GlobalConfig globalConfig) {
        if (null == globalConfig) {
            this.globalConfig = new GlobalConfig();
        } else {
            this.globalConfig = globalConfig;
        }

        if (null == template) {
            this.template = new TemplateConfig();
        } else {
            this.template = template;
        }

        if (null == packageConfig) {
            this.handlerPackage(this.template, this.globalConfig.getOutputDir(), new PackageConfig());
        } else {
            this.handlerPackage(this.template, this.globalConfig.getOutputDir(), packageConfig);
        }

        this.dataSourceConfig = dataSourceConfig;
        this.handlerDataSource(dataSourceConfig);
        if (null == strategyConfig) {
            this.strategyConfig = new StrategyConfig();
        } else {
            this.strategyConfig = strategyConfig;
        }

        this.handlerStrategy(this.strategyConfig);
    }

    public Map<String, String> getPackageInfo() {
        return this.packageInfo;
    }

    public Map<String, String> getPathInfo() {
        return this.pathInfo;
    }

    public String getSuperEntityClass() {
        return this.superEntityClass;
    }

    public String getSuperMapperClass() {
        return this.superMapperClass;
    }

    public String getSuperServiceClass() {
        return this.superServiceClass;
    }

    public String getSuperServiceImplClass() {
        return this.superServiceImplClass;
    }

    public String getSuperControllerClass() {
        return this.superControllerClass;
    }

    public List<TableInfo> getTableInfoList() {
        return this.tableInfoList;
    }

    public ConfigBuilder setTableInfoList(List<TableInfo> tableInfoList) {
        this.tableInfoList = tableInfoList;
        return this;
    }

    public TemplateConfig getTemplate() {
        return this.template == null ? new TemplateConfig() : this.template;
    }

    private void handlerPackage(TemplateConfig template, String outputDir, PackageConfig config) {
        this.packageInfo = new HashMap(8);
        this.packageInfo.put("ModuleName", config.getModuleName());
        this.packageInfo.put("Entity", this.joinPackage(config.getParent(), config.getEntity()));
        this.packageInfo.put("Mapper", this.joinPackage(config.getParent(), config.getMapper()));
        this.packageInfo.put("Xml", this.joinPackage(config.getParent(), config.getXml()));
        this.packageInfo.put("Service", this.joinPackage(config.getParent(), config.getService()));
        this.packageInfo.put("ServiceImpl", this.joinPackage(config.getParent(), config.getServiceImpl()));
        this.packageInfo.put("Controller", this.joinPackage(config.getParent(), config.getController()));
        this.packageInfo.put("Vo", this.joinPackage(config.getParent(), config.getVo()));
        this.packageInfo.put("Parent", this.joinPackage(config.getParent(), config.getParent()));
        this.pathInfo = new HashMap(6);
        if (config.outUseParentPackage()) {
            if (StringUtils.isNotEmpty(template.getEntity(this.getGlobalConfig().isKotlin()))) {
                this.pathInfo.put("entity_path", this.joinPath(outputDir, (String) this.packageInfo.get("Entity")));
            }

            if (StringUtils.isNotEmpty(template.getMapper())) {
                this.pathInfo.put("mapper_path", this.joinPath(outputDir, (String) this.packageInfo.get("Mapper")));
            }

            if (StringUtils.isNotEmpty(template.getXml())) {
                this.pathInfo.put("xml_path", this.joinPath(outputDir, (String) this.packageInfo.get("Xml")));
            }

            if (StringUtils.isNotEmpty(template.getService())) {
                this.pathInfo.put("service_path", this.joinPath(outputDir, (String) this.packageInfo.get("Service")));
            }

            if (StringUtils.isNotEmpty(template.getServiceImpl())) {
                this.pathInfo.put("serviceimpl_path", this.joinPath(outputDir, (String) this.packageInfo.get("ServiceImpl")));
            }

            if (StringUtils.isNotEmpty(template.getController())) {
                this.pathInfo.put("controller_path", this.joinPath(outputDir, (String) this.packageInfo.get("Controller")));
            }

            if (StringUtils.isNotEmpty(template.getVo())) {
                this.pathInfo.put("vo_path", this.joinPath(outputDir, (String) this.packageInfo.get("Vo")));
            }
        } else {
            if (StringUtils.isNotEmpty(template.getEntity(this.getGlobalConfig().isKotlin()))) {
                this.pathInfo.put("entity_path", this.joinPath(outputDir, "entity"));
            }

            if (StringUtils.isNotEmpty(template.getMapper())) {
                this.pathInfo.put("mapper_path", this.joinPath(outputDir, "mapper"));
            }

            if (StringUtils.isNotEmpty(template.getXml())) {
                this.pathInfo.put("xml_path", this.joinPath(outputDir, "xml"));
            }

            if (StringUtils.isNotEmpty(template.getService())) {
                this.pathInfo.put("service_path", this.joinPath(outputDir, "service"));
            }

            if (StringUtils.isNotEmpty(template.getServiceImpl())) {
                this.pathInfo.put("serviceimpl_path", this.joinPath(outputDir, "serviceimpl"));
            }

            if (StringUtils.isNotEmpty(template.getController())) {
                this.pathInfo.put("controller_path", this.joinPath(outputDir, "controller"));
            }

            if (StringUtils.isNotEmpty(template.getVo())) {
                this.pathInfo.put("vo_path", this.joinPath(outputDir, "vo"));
            }
        }

    }

    private void handlerDataSource(DataSourceConfig config) {
        this.connection = config.getConn();
        this.dbQuery = config.getDbQuery();
    }

    private void handlerStrategy(StrategyConfig config) {
        this.processTypes(config);
        this.tableInfoList = this.getTablesInfo(config);
    }

    private void processTypes(StrategyConfig config) {
        if (StringUtils.isEmpty(config.getSuperServiceClass())) {
            this.superServiceClass = "com.baomidou.mybatisplus.service.IService";
        } else {
            this.superServiceClass = config.getSuperServiceClass();
        }

        if (StringUtils.isEmpty(config.getSuperServiceImplClass())) {
            this.superServiceImplClass = "com.baomidou.mybatisplus.service.impl.ServiceImpl";
        } else {
            this.superServiceImplClass = config.getSuperServiceImplClass();
        }

        if (StringUtils.isEmpty(config.getSuperMapperClass())) {
            this.superMapperClass = "com.baomidou.mybatisplus.mapper.BaseMapper";
        } else {
            this.superMapperClass = config.getSuperMapperClass();
        }

        this.superEntityClass = config.getSuperEntityClass();
        this.superControllerClass = config.getSuperControllerClass();
    }

    private List<TableInfo> processTable(List<TableInfo> tableList, NamingStrategy strategy, StrategyConfig config) {
        String[] tablePrefix = config.getTablePrefix();
        String[] fieldPrefix = config.getFieldPrefix();

        TableInfo tableInfo;
        for (Iterator i$ = tableList.iterator(); i$.hasNext(); this.checkTableIdTableFieldAnnotation(config, tableInfo, fieldPrefix)) {
            tableInfo = (TableInfo) i$.next();
            tableInfo.setEntityName(this.strategyConfig, NamingStrategy.capitalFirst(this.processName(tableInfo.getName(), strategy, tablePrefix)));
            if (StringUtils.isNotEmpty(this.globalConfig.getMapperName())) {
                tableInfo.setMapperName(String.format(this.globalConfig.getMapperName(), tableInfo.getEntityName()));
            } else {
                tableInfo.setMapperName(tableInfo.getEntityName() + "Mapper");
            }

            if (StringUtils.isNotEmpty(this.globalConfig.getXmlName())) {
                tableInfo.setXmlName(String.format(this.globalConfig.getXmlName(), tableInfo.getEntityName()));
            } else {
                tableInfo.setXmlName(tableInfo.getEntityName() + "Mapper");
            }

            if (StringUtils.isNotEmpty(this.globalConfig.getServiceName())) {
                tableInfo.setServiceName(String.format(this.globalConfig.getServiceName(), tableInfo.getEntityName()));
            } else {
                tableInfo.setServiceName("I" + tableInfo.getEntityName() + "Service");
            }

            if (StringUtils.isNotEmpty(this.globalConfig.getServiceImplName())) {
                tableInfo.setServiceImplName(String.format(this.globalConfig.getServiceImplName(), tableInfo.getEntityName()));
            } else {
                tableInfo.setServiceImplName(tableInfo.getEntityName() + "ServiceImpl");
            }

            if (StringUtils.isNotEmpty(this.globalConfig.getControllerName())) {
                tableInfo.setControllerName(String.format(this.globalConfig.getControllerName(), tableInfo.getEntityName()));
            } else {
                tableInfo.setControllerName(tableInfo.getEntityName() + "Controller");
            }

            if (StringUtils.isNotEmpty(this.globalConfig.getVoName())) {
                tableInfo.setVoName(String.format(this.globalConfig.getVoName(), tableInfo.getEntityName()));
            } else {
                tableInfo.setVoName(tableInfo.getEntityName() + "VO");
            }
        }

        return tableList;
    }

    private void checkTableIdTableFieldAnnotation(StrategyConfig config, TableInfo tableInfo, String[] fieldPrefix) {
        boolean importTableFieldAnnotaion = false;
        boolean importTableIdAnnotaion = false;
        Iterator i$;
        TableField tf;
        if (config.isEntityTableFieldAnnotationEnable()) {
            for (i$ = tableInfo.getFields().iterator(); i$.hasNext(); importTableIdAnnotaion = true) {
                tf = (TableField) i$.next();
                tf.setConvert(true);
                importTableFieldAnnotaion = true;
            }
        } else if (fieldPrefix != null && fieldPrefix.length != 0) {
            i$ = tableInfo.getFields().iterator();

            while (i$.hasNext()) {
                tf = (TableField) i$.next();
                if (NamingStrategy.isPrefixContained(tf.getName(), fieldPrefix)) {
                    if (tf.isKeyFlag()) {
                        importTableIdAnnotaion = true;
                    }

                    tf.setConvert(true);
                    importTableFieldAnnotaion = true;
                }
            }
        }

        if (importTableFieldAnnotaion) {
            tableInfo.getImportPackages().add(com.baomidou.mybatisplus.annotations.TableField.class.getCanonicalName());
        }

        if (importTableIdAnnotaion) {
            tableInfo.getImportPackages().add(TableId.class.getCanonicalName());
        }

        if (this.globalConfig.getIdType() != null) {
            if (!importTableIdAnnotaion) {
                tableInfo.getImportPackages().add(TableId.class.getCanonicalName());
            }

            tableInfo.getImportPackages().add(IdType.class.getCanonicalName());
        }

    }

    private List<TableInfo> getTablesInfo(StrategyConfig config) {
        boolean isInclude = null != config.getInclude() && config.getInclude().length > 0;
        boolean isExclude = null != config.getExclude() && config.getExclude().length > 0;
        if (isInclude && isExclude) {
            throw new RuntimeException("<strategy> 标签中 <include> 与 <exclude> 只能配置一项！");
        } else {
            List<TableInfo> tableList = new ArrayList();
            List<TableInfo> includeTableList = new ArrayList();
            List<TableInfo> excludeTableList = new ArrayList();
            Set<String> notExistTables = new HashSet();
            PreparedStatement preparedStatement = null;

            try {
                String tablesSql = this.dbQuery.tablesSql();
                if (DbType.POSTGRE_SQL == this.dbQuery.dbType()) {
                    tablesSql = String.format(tablesSql, this.dataSourceConfig.getSchemaname());
                } else if (DbType.ORACLE == this.dbQuery.dbType()) {
                    StringBuilder sb;
                    String[] arr$;
                    int len$;
                    int i$;
                    String tbname;
                    if (isInclude) {
                        sb = new StringBuilder(tablesSql);
                        sb.append(" WHERE ").append(this.dbQuery.tableName()).append(" IN (");
                        arr$ = config.getInclude();
                        len$ = arr$.length;

                        for (i$ = 0; i$ < len$; ++i$) {
                            tbname = arr$[i$];
                            sb.append("'").append(tbname.toUpperCase()).append("',");
                        }

                        sb.replace(sb.length() - 1, sb.length(), ")");
                        tablesSql = sb.toString();
                    } else if (isExclude) {
                        sb = new StringBuilder(tablesSql);
                        sb.append(" WHERE ").append(this.dbQuery.tableName()).append(" NOT IN (");
                        arr$ = config.getExclude();
                        len$ = arr$.length;

                        for (i$ = 0; i$ < len$; ++i$) {
                            tbname = arr$[i$];
                            sb.append("'").append(tbname.toUpperCase()).append("',");
                        }

                        sb.replace(sb.length() - 1, sb.length(), ")");
                        tablesSql = sb.toString();
                    }
                }

                preparedStatement = this.connection.prepareStatement(tablesSql);
                ResultSet results = preparedStatement.executeQuery();

                label345:
                while (true) {
                    String tableName;
                    String tableComment;
                    label329:
                    do {
                        while (results.next()) {
                            tableName = results.getString(this.dbQuery.tableName());
                            if (StringUtils.isNotEmpty(tableName)) {
                                tableComment = results.getString(this.dbQuery.tableComment());
                                continue label329;
                            }

                            System.err.println("当前数据库为空！！！");
                        }

                        Iterator i$ = tableList.iterator();

                        TableInfo ti;
                        while (i$.hasNext()) {
                            ti = (TableInfo) i$.next();
                            notExistTables.remove(ti.getName());
                        }

                        if (notExistTables.size() > 0) {
                            System.err.println("表 " + notExistTables + " 在数据库中不存在！！！");
                        }

                        if (isExclude) {
                            tableList.removeAll(excludeTableList);
                            includeTableList = tableList;
                        }

                        if (!isInclude && !isExclude) {
                            includeTableList = tableList;
                        }

                        i$ = includeTableList.iterator();

                        while (i$.hasNext()) {
                            ti = (TableInfo) i$.next();
                            this.convertTableFields(ti, config.getColumnNaming());
                        }
                        break label345;
                    } while (config.isSkipView() && "VIEW".equals(tableComment));

                    TableInfo tableInfo = new TableInfo();
                    tableInfo.setName(tableName);
                    tableInfo.setComment(tableComment);
                    int len$;
                    int i$;
                    String excludeTab;
                    String[] arr$;
                    if (isInclude) {
                        arr$ = config.getInclude();
                        len$ = arr$.length;

                        for (i$ = 0; i$ < len$; ++i$) {
                            excludeTab = arr$[i$];
                            if (excludeTab.equalsIgnoreCase(tableName)) {
                                includeTableList.add(tableInfo);
                            } else {
                                notExistTables.add(excludeTab);
                            }
                        }
                    } else if (isExclude) {
                        arr$ = config.getExclude();
                        len$ = arr$.length;

                        for (i$ = 0; i$ < len$; ++i$) {
                            excludeTab = arr$[i$];
                            if (excludeTab.equalsIgnoreCase(tableName)) {
                                excludeTableList.add(tableInfo);
                            } else {
                                notExistTables.add(excludeTab);
                            }
                        }
                    }

                    tableList.add(tableInfo);
                }
            } catch (SQLException var26) {
                var26.printStackTrace();
            } finally {
                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }

                    if (this.connection != null) {
                        this.connection.close();
                    }
                } catch (SQLException var25) {
                    var25.printStackTrace();
                }

            }

            return this.processTable(includeTableList, config.getNaming(), config);
        }
    }

    private TableInfo convertTableFields(TableInfo tableInfo, NamingStrategy strategy) {
        boolean haveId = false;
        List<TableField> fieldList = new ArrayList();
        ArrayList commonFieldList = new ArrayList();

        try {
            String tableFieldsSql = this.dbQuery.tableFieldsSql();
            if (DbType.POSTGRE_SQL == this.dbQuery.dbType()) {
                tableFieldsSql = String.format(tableFieldsSql, this.dataSourceConfig.getSchemaname(), tableInfo.getName());
            } else {
                tableFieldsSql = String.format(tableFieldsSql, tableInfo.getName());
            }

            PreparedStatement preparedStatement = this.connection.prepareStatement(tableFieldsSql);
            ResultSet results = preparedStatement.executeQuery();

            label79:
            while (true) {
                while (true) {
                    if (!results.next()) {
                        break label79;
                    }

                    TableField field = new TableField();
                    String key = results.getString(this.dbQuery.fieldKey());
                    boolean isId = StringUtils.isNotEmpty(key) && key.toUpperCase().equals("PRI");
                    if (isId && !haveId) {
                        field.setKeyFlag(true);
                        if (this.dbQuery.isKeyIdentity(results)) {
                            field.setKeyIdentityFlag(true);
                        }

                        haveId = true;
                    } else {
                        field.setKeyFlag(false);
                    }

                    String[] fcs = this.dbQuery.fieldCustom();
                    if (null != fcs) {
                        Map<String, Object> customMap = new HashMap();
                        String[] arr$ = fcs;
                        int len$ = fcs.length;

                        for (int i$ = 0; i$ < len$; ++i$) {
                            String fc = arr$[i$];
                            customMap.put(fc, results.getObject(fc));
                        }

                        field.setCustomMap(customMap);
                    }

                    field.setName(results.getString(this.dbQuery.fieldName()));
                    field.setType(results.getString(this.dbQuery.fieldType()));
                    field.setPropertyName(this.strategyConfig, this.processName(field.getName(), strategy));
                    field.setColumnType(this.dataSourceConfig.getTypeConvert().processTypeConvert(field.getType()));
                    field.setComment(results.getString(this.dbQuery.fieldComment()));
                    if (this.strategyConfig.includeSuperEntityColumns(field.getName())) {
                        commonFieldList.add(field);
                    } else {
                        List<TableFill> tableFillList = this.getStrategyConfig().getTableFillList();
                        if (null != tableFillList) {
                            Iterator i$ = tableFillList.iterator();

                            while (i$.hasNext()) {
                                TableFill tableFill = (TableFill) i$.next();
                                if (tableFill.getFieldName().equals(field.getName())) {
                                    field.setFill(tableFill.getFieldFill().name());
                                    break;
                                }
                            }
                        }

                        fieldList.add(field);
                    }
                }
            }
        } catch (SQLException var18) {
            System.err.println("SQL Exception：" + var18.getMessage());
        }

        tableInfo.setFields(fieldList);
        tableInfo.setCommonFields(commonFieldList);
        return tableInfo;
    }

    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isEmpty(parentDir)) {
            parentDir = System.getProperty("java.io.tmpdir");
        }

        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir = parentDir + File.separator;
        }

        packageName = packageName.replaceAll("\\.", "\\" + File.separator);
        return parentDir + packageName;
    }

    private String joinPackage(String parent, String subPackage) {
        return StringUtils.isEmpty(parent) ? subPackage : parent + "." + subPackage;
    }

    private String processName(String name, NamingStrategy strategy) {
        return this.processName(name, strategy, this.strategyConfig.getFieldPrefix());
    }

    private String processName(String name, NamingStrategy strategy, String[] prefix) {
        boolean removePrefix = false;
        if (prefix != null && prefix.length >= 1) {
            removePrefix = true;
        }

        String propertyName;
        if (removePrefix) {
            if (strategy == NamingStrategy.underline_to_camel) {
                propertyName = NamingStrategy.removePrefixAndCamel(name, prefix);
            } else {
                propertyName = NamingStrategy.removePrefix(name, prefix);
            }
        } else if (strategy == NamingStrategy.underline_to_camel) {
            propertyName = NamingStrategy.underlineToCamel(name);
        } else {
            propertyName = name;
        }

        return propertyName;
    }

    public StrategyConfig getStrategyConfig() {
        return this.strategyConfig;
    }

    public ConfigBuilder setStrategyConfig(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
        return this;
    }

    public GlobalConfig getGlobalConfig() {
        return this.globalConfig;
    }

    public ConfigBuilder setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }

    public InjectionConfig getInjectionConfig() {
        return this.injectionConfig;
    }

    public ConfigBuilder setInjectionConfig(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return this;
    }
}

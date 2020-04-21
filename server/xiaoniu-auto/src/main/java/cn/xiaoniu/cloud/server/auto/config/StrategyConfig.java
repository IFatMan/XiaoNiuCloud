package cn.xiaoniu.cloud.server.auto.config;

import cn.xiaoniu.cloud.server.auto.config.po.TableFill;
import cn.xiaoniu.cloud.server.auto.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.util.List;


public class StrategyConfig {
    public static boolean DB_COLUMN_UNDERLINE = false;
    private boolean isCapitalMode = false;
    private boolean skipView = false;
    private NamingStrategy naming = NamingStrategy.nochange;


    private NamingStrategy columnNaming = null;


    private String[] tablePrefix;


    private String[] fieldPrefix;


    private String superEntityClass;


    private String[] superEntityColumns;


    private String superMapperClass = "com.baomidou.mybatisplus.mapper.BaseMapper";


    private String superServiceClass = "com.baomidou.mybatisplus.service.IService";


    private String superServiceImplClass = "com.baomidou.mybatisplus.service.impl.ServiceImpl";


    private String superControllerClass;


    private String[] include = null;


    private String[] exclude = null;


    private boolean entityColumnConstant = false;


    private boolean entityBuilderModel = false;


    private boolean entityLombokModel = false;


    private boolean entityBooleanColumnRemoveIsPrefix = false;


    private boolean restControllerStyle = false;


    private boolean controllerMappingHyphenStyle = false;


    private boolean entityTableFieldAnnotationEnable = false;


    private String versionFieldName;


    private String logicDeleteFieldName;


    private List<TableFill> tableFillList = null;

    public StrategyConfig setDbColumnUnderline(boolean dbColumnUnderline) {
        DB_COLUMN_UNDERLINE = dbColumnUnderline;
        return this;
    }


    public boolean isCapitalModeNaming(String word) {
        return (this.isCapitalMode && StringUtils.isCapitalMode(word));
    }


    public boolean containsTablePrefix(String tableName) {
        if (null != tableName) {
            String[] tps = getTablePrefix();
            if (null != tps) {
                for (String tp : tps) {
                    if (tableName.contains(tp)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean isCapitalMode() {
        return this.isCapitalMode;
    }


    public StrategyConfig setCapitalMode(boolean isCapitalMode) {
        this.isCapitalMode = isCapitalMode;
        return this;
    }


    public boolean isSkipView() {
        return this.skipView;
    }


    public StrategyConfig setSkipView(boolean skipView) {
        this.skipView = skipView;
        return this;
    }


    public NamingStrategy getNaming() {
        return this.naming;
    }


    public StrategyConfig setNaming(NamingStrategy naming) {
        this.naming = naming;
        return this;
    }

    public NamingStrategy getColumnNaming() {
        if (null == this.columnNaming) {
            return this.naming;
        }
        return this.columnNaming;
    }

    public StrategyConfig setColumnNaming(NamingStrategy columnNaming) {
        this.columnNaming = columnNaming;
        return this;
    }


    public String[] getTablePrefix() {
        return this.tablePrefix;
    }


    public StrategyConfig setTablePrefix(String... tablePrefix) {
        this.tablePrefix = tablePrefix;
        return this;
    }


    public String getSuperEntityClass() {
        return this.superEntityClass;
    }


    public StrategyConfig setSuperEntityClass(String superEntityClass) {
        this.superEntityClass = superEntityClass;
        return this;
    }

    public boolean includeSuperEntityColumns(String fieldName) {
        if (null != this.superEntityColumns) {
            for (String column : this.superEntityColumns) {
                if (column.equals(fieldName)) {
                    return true;
                }
            }
        }
        return false;
    }


    public String[] getSuperEntityColumns() {
        return this.superEntityColumns;
    }


    public StrategyConfig setSuperEntityColumns(String... superEntityColumns) {
        this.superEntityColumns = superEntityColumns;
        return this;
    }


    public String getSuperMapperClass() {
        return this.superMapperClass;
    }


    public StrategyConfig setSuperMapperClass(String superMapperClass) {
        this.superMapperClass = superMapperClass;
        return this;
    }


    public String getSuperServiceClass() {
        return this.superServiceClass;
    }


    public StrategyConfig setSuperServiceClass(String superServiceClass) {
        this.superServiceClass = superServiceClass;
        return this;
    }


    public String getSuperServiceImplClass() {
        return this.superServiceImplClass;
    }


    public StrategyConfig setSuperServiceImplClass(String superServiceImplClass) {
        this.superServiceImplClass = superServiceImplClass;
        return this;
    }


    public String getSuperControllerClass() {
        return this.superControllerClass;
    }


    public StrategyConfig setSuperControllerClass(String superControllerClass) {
        this.superControllerClass = superControllerClass;
        return this;
    }


    public String[] getInclude() {
        return this.include;
    }


    public StrategyConfig setInclude(String... include) {
        this.include = include;
        return this;
    }


    public String[] getExclude() {
        return this.exclude;
    }


    public StrategyConfig setExclude(String... exclude) {
        this.exclude = exclude;
        return this;
    }


    public boolean isEntityColumnConstant() {
        return this.entityColumnConstant;
    }


    public StrategyConfig setEntityColumnConstant(boolean entityColumnConstant) {
        this.entityColumnConstant = entityColumnConstant;
        return this;
    }


    public boolean isEntityBuilderModel() {
        return this.entityBuilderModel;
    }


    public StrategyConfig setEntityBuilderModel(boolean entityBuilderModel) {
        this.entityBuilderModel = entityBuilderModel;
        return this;
    }


    public boolean isEntityLombokModel() {
        return this.entityLombokModel;
    }


    public StrategyConfig setEntityLombokModel(boolean entityLombokModel) {
        this.entityLombokModel = entityLombokModel;
        return this;
    }


    public boolean isEntityBooleanColumnRemoveIsPrefix() {
        return this.entityBooleanColumnRemoveIsPrefix;
    }


    public StrategyConfig setEntityBooleanColumnRemoveIsPrefix(boolean entityBooleanColumnRemoveIsPrefix) {
        this.entityBooleanColumnRemoveIsPrefix = entityBooleanColumnRemoveIsPrefix;
        return this;
    }


    public boolean isRestControllerStyle() {
        return this.restControllerStyle;
    }


    public StrategyConfig setRestControllerStyle(boolean restControllerStyle) {
        this.restControllerStyle = restControllerStyle;
        return this;
    }


    public boolean isControllerMappingHyphenStyle() {
        return this.controllerMappingHyphenStyle;
    }


    public StrategyConfig setControllerMappingHyphenStyle(boolean controllerMappingHyphenStyle) {
        this.controllerMappingHyphenStyle = controllerMappingHyphenStyle;
        return this;
    }


    public String getLogicDeleteFieldName() {
        return this.logicDeleteFieldName;
    }


    public StrategyConfig setLogicDeleteFieldName(String logicDeleteFieldName) {
        this.logicDeleteFieldName = logicDeleteFieldName;
        return this;
    }


    public String getVersionFieldName() {
        return this.versionFieldName;
    }


    public StrategyConfig setVersionFieldName(String versionFieldName) {
        this.versionFieldName = versionFieldName;
        return this;
    }


    public List<TableFill> getTableFillList() {
        return this.tableFillList;
    }


    public StrategyConfig setTableFillList(List<TableFill> tableFillList) {
        this.tableFillList = tableFillList;
        return this;
    }


    public String[] getFieldPrefix() {
        return this.fieldPrefix;
    }


    public StrategyConfig setFieldPrefix(String[] fieldPrefix) {
        this.fieldPrefix = fieldPrefix;
        return this;
    }

    public StrategyConfig fieldPrefix(String... fieldPrefixs) {
        setFieldPrefix(fieldPrefixs);
        return this;
    }

    public StrategyConfig entityTableFieldAnnotationEnable(boolean isEnableAnnotation) {
        this.entityTableFieldAnnotationEnable = isEnableAnnotation;
        return this;
    }


    public boolean isEntityTableFieldAnnotationEnable() {
        return this.entityTableFieldAnnotationEnable;
    }
}

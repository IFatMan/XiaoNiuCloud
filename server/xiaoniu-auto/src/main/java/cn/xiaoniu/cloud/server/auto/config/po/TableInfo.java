package cn.xiaoniu.cloud.server.auto.config.po;

import cn.xiaoniu.cloud.server.auto.config.StrategyConfig;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TableInfo {
    private boolean convert;
    private String name;
    private String comment;
    private String entityName;
    private String mapperName;
    private String xmlName;
    private String serviceName;
    private String serviceImplName;
    private String controllerName;
    private String voName;
    private List<TableField> fields;
    private List<TableField> commonFields;
    private List<String> importPackages = new ArrayList();

    private String fieldNames;

    public boolean isConvert() {
        return this.convert;
    }


    protected void setConvert(StrategyConfig strategyConfig) {
        if (strategyConfig.containsTablePrefix(this.name)) {

            this.convert = true;
        } else if (strategyConfig.isCapitalModeNaming(this.name)) {

            this.convert = false;

        } else if (StrategyConfig.DB_COLUMN_UNDERLINE) {

            if (StringUtils.containsUpperCase(this.name)) {
                this.convert = true;
            }
        } else if (!this.entityName.equalsIgnoreCase(this.name)) {
            this.convert = true;
        }
    }


    public void setConvert(boolean convert) {
        this.convert = convert;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getComment() {
        return this.comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getEntityPath() {
        StringBuilder ep = new StringBuilder();
        ep.append(this.entityName.substring(0, 1).toLowerCase());
        ep.append(this.entityName.substring(1));
        return ep.toString();
    }


    public String getEntityName() {
        return this.entityName;
    }


    public void setEntityName(StrategyConfig strategyConfig, String entityName) {
        this.entityName = entityName;
        setConvert(strategyConfig);
    }


    public String getMapperName() {
        return this.mapperName;
    }


    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
    }


    public String getXmlName() {
        return this.xmlName;
    }


    public void setXmlName(String xmlName) {
        this.xmlName = xmlName;
    }


    public String getServiceName() {
        return this.serviceName;
    }


    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    public String getServiceImplName() {
        return this.serviceImplName;
    }


    public void setServiceImplName(String serviceImplName) {
        this.serviceImplName = serviceImplName;
    }


    public String getControllerName() {
        return this.controllerName;
    }


    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getVoName() {
        return voName;
    }

    public void setVoName(String voName) {
        this.voName = voName;
    }

    public List<TableField> getFields() {
        return this.fields;
    }


    public void setFields(List<TableField> fields) {
        if (CollectionUtils.isNotEmpty(fields)) {
            this.fields = fields;

            Set<String> pkgSet = new HashSet<String>();
            for (TableField field : fields) {
                if (null != field.getColumnType() && null != field.getColumnType().getPkg()) {
                    pkgSet.add(field.getColumnType().getPkg());
                }
                if (field.isKeyFlag()) {

                    if (field.isConvert() || field.isKeyIdentityFlag()) {
                        pkgSet.add("com.baomidou.mybatisplus.annotations.TableId");
                    }

                    if (field.isKeyIdentityFlag()) {
                        pkgSet.add("com.baomidou.mybatisplus.enums.IdType");
                    }
                } else if (field.isConvert()) {

                    pkgSet.add("com.baomidou.mybatisplus.annotations.TableField");
                }
                if (null != field.getFill()) {

                    pkgSet.add("com.baomidou.mybatisplus.annotations.TableField");
                    pkgSet.add("com.baomidou.mybatisplus.enums.FieldFill");
                }
            }
            if (!pkgSet.isEmpty()) {
                this.importPackages = new ArrayList(Arrays.asList(pkgSet.toArray(new String[0])));
            }
        }
    }


    public List<TableField> getCommonFields() {
        return this.commonFields;
    }


    public void setCommonFields(List<TableField> commonFields) {
        this.commonFields = commonFields;
    }


    public List<String> getImportPackages() {
        return this.importPackages;
    }


    public void setImportPackages(String pkg) {
        this.importPackages.add(pkg);
    }


    public boolean isLogicDelete(String logicDeletePropertyName) {
        for (TableField tableField : this.fields) {
            if (tableField.getName().equals(logicDeletePropertyName)) {
                return true;
            }
        }
        return false;
    }


    public String getFieldNames() {
        if (StringUtils.isEmpty(this.fieldNames)) {
            StringBuilder names = new StringBuilder();
            for (int i = 0; i < this.fields.size(); i++) {
                TableField fd = (TableField) this.fields.get(i);
                if (i == this.fields.size() - 1) {
                    names.append(fd.getName());
                } else {
                    names.append(fd.getName()).append(", ");
                }
            }
            this.fieldNames = names.toString();
        }
        return this.fieldNames;
    }
}

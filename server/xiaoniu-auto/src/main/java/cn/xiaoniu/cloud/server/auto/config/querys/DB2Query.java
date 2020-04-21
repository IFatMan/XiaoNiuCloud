package cn.xiaoniu.cloud.server.auto.config.querys;

import cn.xiaoniu.cloud.server.auto.config.rules.DbType;


public class DB2Query
        extends AbstractDbQuery {
    public DbType dbType() {
        return DbType.DB2;
    }


    public String tablesSql() {
        return "SELECT * FROM SYSCAT.TABLES where tabschema=current schema";
    }


    public String tableFieldsSql() {
        return "SELECT *  FROM syscat.columns WHERE tabschema=current schema AND tabname='%s'";
    }


    public String tableName() {
        return "TABNAME";
    }


    public String tableComment() {
        return "REMARKS";
    }


    public String fieldName() {
        return "COLNAME";
    }


    public String fieldType() {
        return "TYPENAME";
    }


    public String fieldComment() {
        return "REMARKS";
    }


    public String fieldKey() {
        return "IDENTITY";
    }
}

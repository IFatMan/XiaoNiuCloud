package cn.xiaoniu.cloud.server.auto.config.querys;

import cn.xiaoniu.cloud.server.auto.config.rules.DbType;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MariadbQuery
        extends AbstractDbQuery {
    public DbType dbType() {
        return DbType.MARIADB;
    }


    public String tablesSql() {
        return "show table status";
    }


    public String tableFieldsSql() {
        return "show full fields from `%s`";
    }


    public String tableName() {
        return "NAME";
    }


    public String tableComment() {
        return "COMMENT";
    }


    public String fieldName() {
        return "FIELD";
    }


    public String fieldType() {
        return "TYPE";
    }


    public String fieldComment() {
        return "COMMENT";
    }


    public String fieldKey() {
        return "KEY";
    }


    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return "auto_increment".equals(results.getString("Extra"));
    }
}

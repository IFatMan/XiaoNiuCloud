package cn.xiaoniu.cloud.server.auto.config;

import cn.xiaoniu.cloud.server.auto.config.rules.DbType;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDbQuery {
    DbType dbType();

    String tablesSql();

    String tableFieldsSql();

    String tableName();

    String tableComment();

    String fieldName();

    String fieldType();

    String fieldComment();

    String fieldKey();

    boolean isKeyIdentity(ResultSet paramResultSet) throws SQLException;

    String[] fieldCustom();
}

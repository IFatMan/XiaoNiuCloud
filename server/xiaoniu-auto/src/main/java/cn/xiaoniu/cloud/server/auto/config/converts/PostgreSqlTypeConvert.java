package cn.xiaoniu.cloud.server.auto.config.converts;

import cn.xiaoniu.cloud.server.auto.config.rules.DbColumnType;
import cn.xiaoniu.cloud.server.auto.config.ITypeConvert;


public class PostgreSqlTypeConvert
        implements ITypeConvert {
    public DbColumnType processTypeConvert(String fieldType) {
        String t = fieldType.toLowerCase();
        if (t.contains("char") || t.contains("text"))
            return DbColumnType.STRING;
        if (t.contains("bigint"))
            return DbColumnType.LONG;
        if (t.contains("int"))
            return DbColumnType.INTEGER;
        if (t.contains("date") || t.contains("time") || t.contains("year"))
            return DbColumnType.DATE;
        if (t.contains("text"))
            return DbColumnType.STRING;
        if (t.contains("bit"))
            return DbColumnType.BOOLEAN;
        if (t.contains("decimal"))
            return DbColumnType.BIG_DECIMAL;
        if (t.contains("clob"))
            return DbColumnType.CLOB;
        if (t.contains("blob"))
            return DbColumnType.BYTE_ARRAY;
        if (t.contains("float"))
            return DbColumnType.FLOAT;
        if (t.contains("double"))
            return DbColumnType.DOUBLE;
        if (t.contains("json") || t.contains("enum"))
            return DbColumnType.STRING;
        if (t.contains("boolean")) {
            return DbColumnType.BOOLEAN;
        }
        return DbColumnType.STRING;
    }
}

package cn.xiaoniu.cloud.server.auto.config.converts;

import cn.xiaoniu.cloud.server.auto.config.rules.DbColumnType;
import cn.xiaoniu.cloud.server.auto.config.ITypeConvert;


public class DB2TypeConvert
        implements ITypeConvert {
    public DbColumnType processTypeConvert(String fieldType) {
        String t = fieldType.toLowerCase();
        if (t.contains("char") || t.contains("text"))
            return DbColumnType.STRING;
        if (t.contains("bigint"))
            return DbColumnType.LONG;
        if (t.contains("smallint"))
            return DbColumnType.BASE_SHORT;
        if (t.contains("int"))
            return DbColumnType.INTEGER;
        if (t.contains("date") || t.contains("time") || t.contains("year") || t.contains("timestamp"))
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
            return DbColumnType.BLOB;
        if (t.contains("binary"))
            return DbColumnType.BYTE_ARRAY;
        if (t.contains("float"))
            return DbColumnType.FLOAT;
        if (t.contains("double"))
            return DbColumnType.DOUBLE;
        if (t.contains("json") || t.contains("enum")) {
            return DbColumnType.STRING;
        }
        return DbColumnType.STRING;
    }
}

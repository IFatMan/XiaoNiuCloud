package cn.xiaoniu.cloud.server.auto.config.converts;

import cn.xiaoniu.cloud.server.auto.config.rules.DbColumnType;
import cn.xiaoniu.cloud.server.auto.config.ITypeConvert;


public class SqlServerTypeConvert
        implements ITypeConvert {
    public DbColumnType processTypeConvert(String fieldType) {
        String t = fieldType.toLowerCase();
        if (t.contains("char") || t.contains("text") || t.contains("xml"))
            return DbColumnType.STRING;
        if (t.contains("bigint"))
            return DbColumnType.LONG;
        if (t.contains("int"))
            return DbColumnType.INTEGER;
        if (t.contains("date") || t.contains("time"))
            return DbColumnType.DATE;
        if (t.contains("text"))
            return DbColumnType.STRING;
        if (t.contains("bit"))
            return DbColumnType.BOOLEAN;
        if (t.contains("decimal") || t.contains("numeric"))
            return DbColumnType.DOUBLE;
        if (t.contains("money"))
            return DbColumnType.BIG_DECIMAL;
        if (t.contains("binary") || t.contains("image"))
            return DbColumnType.BYTE_ARRAY;
        if (t.contains("float") || t.contains("real")) {
            return DbColumnType.FLOAT;
        }
        return DbColumnType.STRING;
    }
}

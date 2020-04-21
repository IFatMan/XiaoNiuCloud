package cn.xiaoniu.cloud.server.auto.config.converts;

import cn.xiaoniu.cloud.server.auto.config.rules.DbColumnType;
import cn.xiaoniu.cloud.server.auto.config.ITypeConvert;


public class OracleTypeConvert
        implements ITypeConvert {
    public DbColumnType processTypeConvert(String fieldType) {
        String t = fieldType.toUpperCase();
        if (t.contains("CHAR"))
            return DbColumnType.STRING;
        if (t.contains("DATE") || t.contains("TIMESTAMP"))
            return DbColumnType.DATE;
        if (t.contains("NUMBER")) {
            if (t.matches("NUMBER\\(+\\d\\)"))
                return DbColumnType.INTEGER;
            if (t.matches("NUMBER\\(+\\d{2}+\\)")) {
                return DbColumnType.LONG;
            }
            return DbColumnType.DOUBLE;
        }
        if (t.contains("FLOAT"))
            return DbColumnType.FLOAT;
        if (t.contains("clob"))
            return DbColumnType.CLOB;
        if (t.contains("BLOB"))
            return DbColumnType.OBJECT;
        if (t.contains("binary"))
            return DbColumnType.BYTE_ARRAY;
        if (t.contains("RAW")) {
            return DbColumnType.BYTE_ARRAY;
        }
        return DbColumnType.STRING;
    }
}

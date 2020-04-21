package cn.xiaoniu.cloud.server.auto.config.querys;

import cn.xiaoniu.cloud.server.auto.config.rules.DbType;


public class PostgreSqlQuery
        extends AbstractDbQuery {
    public DbType dbType() {
        return DbType.POSTGRE_SQL;
    }


    public String tablesSql() {
        return "SELECT A.tablename, obj_description(relfilenode, 'pg_class') AS comments FROM pg_tables A, pg_class B WHERE A.schemaname='%s' AND A.tablename = B.relname";
    }


    public String tableFieldsSql() {
        return "SELECT A.attname AS name, format_type(A.atttypid, A.atttypmod) AS type,col_description(A.attrelid, A.attnum) AS comment, (CASE C.contype WHEN 'p' THEN 'PRI' ELSE '' END) AS key FROM pg_attribute A LEFT JOIN pg_constraint C ON A.attnum = C.conkey[1] AND A.attrelid = C.conrelid WHERE  A.attrelid = '%s.%s'::regclass AND A.attnum > 0 AND NOT A.attisdropped ORDER  BY A.attnum";
    }


    public String tableName() {
        return "tablename";
    }


    public String tableComment() {
        return "comments";
    }


    public String fieldName() {
        return "name";
    }


    public String fieldType() {
        return "type";
    }


    public String fieldComment() {
        return "comment";
    }


    public String fieldKey() {
        return "key";
    }
}

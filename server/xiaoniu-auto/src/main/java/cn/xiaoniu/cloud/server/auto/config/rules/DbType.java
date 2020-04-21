package cn.xiaoniu.cloud.server.auto.config.rules;


public enum DbType {
    MYSQL("mysql"),
    MARIADB("mariadb"),
    ORACLE("oracle"),
    DB2("db2"),
    H2("h2"),
    HSQL("hsql"),
    SQLITE("sqlite"),
    SQL_SERVER("sql_server"),
    POSTGRE_SQL("postgre_sql"),
    OTHER("other db");


    private final String value;


    DbType(String value) {
        this.value = value;
    }


    public String getValue() {
        return this.value;
    }
}

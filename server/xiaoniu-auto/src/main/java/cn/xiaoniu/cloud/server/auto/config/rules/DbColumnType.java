package cn.xiaoniu.cloud.server.auto.config.rules;


public enum DbColumnType {
    BASE_INT("int", null),
    BASE_LONG("long", null),
    BASE_CHAR("char", null),
    BASE_BYTE("byte", null),
    BASE_BOOLEAN("boolean", null),
    BASE_SHORT("short", null),
    BASE_FLOAT("float", null),
    BASE_DOUBLE("double", null),


    STRING("String", null),
    LONG("Long", null),
    INTEGER("Integer", null),
    FLOAT("Float", null),
    DOUBLE("Double", null),
    BOOLEAN("Boolean", null),
    BYTE("Byte", null),
    BYTE_ARRAY("byte[]", null),
    CHARACTER("Character", null),
    OBJECT("Object", null),
    DATE("Date", "java.util.Date"),
    TIME("Time", "java.querys.Time"),
    BLOB("Blob", "java.querys.Blob"),
    CLOB("Clob", "java.querys.Clob"),
    TIMESTAMP("Timestamp", "java.querys.Timestamp"),
    BIG_INTEGER("BigInteger", "java.math.BigInteger"),
    BIG_DECIMAL("BigDecimal", "java.math.BigDecimal"),
    LOCAL_DATE("LocalDate", "java.time.LocalDate"),
    LOCAL_TIME("LocalTime", "java.time.LocalTime"),
    LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime");


    private final String type;


    private final String pkg;


    DbColumnType(String type, String pkg) {
        this.type = type;
        this.pkg = pkg;
    }


    public String getType() {
        return this.type;
    }


    public String getPkg() {
        return this.pkg;
    }
}

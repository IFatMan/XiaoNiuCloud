package cn.xiaoniu.cloud.server.auto.config;

import cn.xiaoniu.cloud.server.auto.config.rules.DbType;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import cn.xiaoniu.cloud.server.auto.config.converts.DB2TypeConvert;
import cn.xiaoniu.cloud.server.auto.config.converts.MySqlTypeConvert;
import cn.xiaoniu.cloud.server.auto.config.converts.OracleTypeConvert;
import cn.xiaoniu.cloud.server.auto.config.converts.PostgreSqlTypeConvert;
import cn.xiaoniu.cloud.server.auto.config.converts.SqlServerTypeConvert;
import cn.xiaoniu.cloud.server.auto.config.querys.DB2Query;
import cn.xiaoniu.cloud.server.auto.config.querys.MariadbQuery;
import cn.xiaoniu.cloud.server.auto.config.querys.MySqlQuery;
import cn.xiaoniu.cloud.server.auto.config.querys.OracleQuery;
import cn.xiaoniu.cloud.server.auto.config.querys.PostgreSqlQuery;
import cn.xiaoniu.cloud.server.auto.config.querys.SqlServerQuery;

import java.sql.Connection;
import java.sql.DriverManager;


public class DataSourceConfig {
    private IDbQuery dbQuery;
    private DbType dbType;
    private String schemaname;
    private ITypeConvert typeConvert;
    private String url;
    private String driverName;
    private String username;
    private String password;

    public IDbQuery getDbQuery() {
        if (null == this.dbQuery) {
            switch (getDbType()) {
                case ORACLE:
                    this.dbQuery = new OracleQuery();


                    return this.dbQuery;
                case SQL_SERVER:
                    this.dbQuery = new SqlServerQuery();
                    return this.dbQuery;
                case POSTGRE_SQL:
                    this.dbQuery = new PostgreSqlQuery();
                    return this.dbQuery;
                case DB2:
                    this.dbQuery = new DB2Query();
                    return this.dbQuery;
                case MARIADB:
                    this.dbQuery = new MariadbQuery();
                    return this.dbQuery;
            }
            this.dbQuery = new MySqlQuery();
        }
        return this.dbQuery;
    }

    public DataSourceConfig setDbQuery(IDbQuery dbQuery) {
        this.dbQuery = dbQuery;
        return this;
    }


    public DbType getDbType() {
        if (null == this.dbType) {
            if (this.driverName.contains("mysql")) {
                this.dbType = DbType.MYSQL;
            } else if (this.driverName.contains("oracle")) {
                this.dbType = DbType.ORACLE;
            } else if (this.driverName.contains("postgresql")) {
                this.dbType = DbType.POSTGRE_SQL;
            } else if (this.driverName.contains("db2")) {
                this.dbType = DbType.DB2;
            } else if (this.driverName.contains("mariadb")) {
                this.dbType = DbType.MARIADB;
            } else {
                throw new MybatisPlusException("Unknown type of database!");
            }
        }
        return this.dbType;
    }

    public DataSourceConfig setDbType(DbType dbType) {
        this.dbType = dbType;
        return this;
    }


    public String getSchemaname() {
        return this.schemaname;
    }


    public void setSchemaname(String schemaname) {
        this.schemaname = schemaname;
    }


    public ITypeConvert getTypeConvert() {
        if (null == this.typeConvert) {
            switch (getDbType()) {
                case ORACLE:
                    this.typeConvert = new OracleTypeConvert();


                    return this.typeConvert;
                case SQL_SERVER:
                    this.typeConvert = new SqlServerTypeConvert();
                    return this.typeConvert;
                case POSTGRE_SQL:
                    this.typeConvert = new PostgreSqlTypeConvert();
                    return this.typeConvert;
                case DB2:
                    this.typeConvert = new DB2TypeConvert();
                    return this.typeConvert;
                case MARIADB:
                    this.typeConvert = new MySqlTypeConvert();
                    return this.typeConvert;
            }
            this.typeConvert = new MySqlTypeConvert();
        }
        return this.typeConvert;
    }

    public DataSourceConfig setTypeConvert(ITypeConvert typeConvert) {
        this.typeConvert = typeConvert;
        return this;
    }


    public Connection getConn() {
        Connection conn = null;
        try {
            Class.forName(this.driverName);
            conn = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (ClassNotFoundException | java.sql.SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    public String getUrl() {
        return this.url;
    }


    public DataSourceConfig setUrl(String url) {
        this.url = url;
        return this;
    }


    public String getDriverName() {
        return this.driverName;
    }


    public DataSourceConfig setDriverName(String driverName) {
        this.driverName = driverName;
        return this;
    }


    public String getUsername() {
        return this.username;
    }


    public DataSourceConfig setUsername(String username) {
        this.username = username;
        return this;
    }


    public String getPassword() {
        return this.password;
    }


    public DataSourceConfig setPassword(String password) {
        this.password = password;
        return this;
    }
}

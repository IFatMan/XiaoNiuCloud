package cn.xiaoniu.cloud.server.auto.config.querys;

import cn.xiaoniu.cloud.server.auto.config.IDbQuery;

import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class AbstractDbQuery
        implements IDbQuery {
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return false;
    }


    public String[] fieldCustom() {
        return null;
    }
}

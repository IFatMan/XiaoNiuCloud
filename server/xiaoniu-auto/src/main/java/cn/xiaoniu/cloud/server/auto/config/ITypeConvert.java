package cn.xiaoniu.cloud.server.auto.config;

import cn.xiaoniu.cloud.server.auto.config.rules.DbColumnType;

public interface ITypeConvert {
    DbColumnType processTypeConvert(String paramString);
}

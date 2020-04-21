package cn.xiaoniu.cloud.server.web.dao;

import cn.xiaoniu.cloud.server.util.context.CacheCustomer;

/**
 * @author 孔明
 * @date 2019/12/19 9:47
 * @description cn.xiaoniu.cloud.server.web.dao.LoginCacheDao
 */
public interface LoginCacheDao {

    /**
     * 根据AccessToken获取用户信息
     *
     * @param accessToken AccessToken
     * @return cn.xiaoniu.cloud.server.util.context.CacheCustomer
     * @author 孔明
     * @date 2019-12-19 09:47:46
     */
    CacheCustomer getCacheCustomer(String accessToken);
}

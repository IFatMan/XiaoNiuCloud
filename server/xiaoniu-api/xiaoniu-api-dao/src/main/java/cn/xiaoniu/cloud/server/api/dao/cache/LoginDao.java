package cn.xiaoniu.cloud.server.api.dao.cache;

import cn.xiaoniu.cloud.server.api.common.CacheUser;
import cn.xiaoniu.cloud.server.api.common.RedisSourceName;
import cn.xiaoniu.cloud.server.web.authority.LoginCacheDao;
import cn.xiaoniu.cloud.server.web.redis.RedisDataSourceHolder;
import cn.xiaoniu.cloud.server.web.redis.RedisSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author 孔明
 * @date 2020/4/24 15:56
 * @description cn.xiaoniu.cloud.server.api.controller.LoginDao
 */
@Component
public class LoginDao implements LoginCacheDao<CacheUser> {

    private static final String ACCESS_TOKEN_PREFIX = "ACCESS_TOKEN:";

    @Override
    @RedisSource(RedisSourceName.API)
    public CacheUser getCacheCustomer(String accessToken) {
        return RedisDataSourceHolder.execute(redisUtil -> redisUtil.get(StringUtils.join(ACCESS_TOKEN_PREFIX, accessToken)));
    }

    /**
     * 缓存用户信息
     *
     * @param token
     * @param user
     * @return boolean
     * @author 孔明
     * @date 2020-04-27 12:29:18
     */
    @RedisSource(RedisSourceName.API)
    public boolean saveCacheUser(String token, CacheUser user) {
        return RedisDataSourceHolder.execute(redisUtil -> redisUtil.set(StringUtils.join(ACCESS_TOKEN_PREFIX, token), user));
    }

    /**
     * 删除用户信息
     *
     * @param token
     * @return
     */
    @RedisSource(RedisSourceName.API)
    public boolean delCacheUser(String token) {
        return RedisDataSourceHolder.execute(redisUtil -> redisUtil.del(StringUtils.join(ACCESS_TOKEN_PREFIX, token)));
    }
}

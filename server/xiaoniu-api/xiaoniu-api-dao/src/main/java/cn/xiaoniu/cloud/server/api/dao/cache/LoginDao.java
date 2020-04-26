package cn.xiaoniu.cloud.server.api.dao.cache;

import cn.xiaoniu.cloud.server.util.context.CacheCustomer;
import cn.xiaoniu.cloud.server.web.authority.LoginCacheDao;
import cn.xiaoniu.cloud.server.web.redis.RedisDataSourceHolder;
import cn.xiaoniu.cloud.server.web.redis.RedisSource;
import org.springframework.stereotype.Component;

/**
 * @author 孔明
 * @date 2020/4/24 15:56
 * @description cn.xiaoniu.cloud.server.api.controller.LoginDao
 */
@Component
public class LoginDao implements LoginCacheDao {

    @Override
    @RedisSource("api")
    public CacheCustomer getCacheCustomer(String accessToken) {
        return RedisDataSourceHolder.execute(redisUtil -> redisUtil.get(accessToken));
    }
}

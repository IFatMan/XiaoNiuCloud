package cn.xiaoniu.cloud.server.web.redis;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建不同Redis客户端示例
 *
 * @author 孔明
 * @date 2019/12/16 15:47
 * @description cn.xiaoniu.cloud.server.web.redis.CreateRedisDemo
 */
@UtilityClass
public class CreateRedisDemo {
    private static Map<String, RedisUtil> redisUtilMap = new HashMap<>();

    private static final String REDIS_POOL_KEY_PREFIX = "spring.redis.lettuce.pool";

    private static final String OAUTH_REDIS_KEY_PREFIX = "spring.redis.oauth";

    private static final String OAUTH_REIDS_UTILS = "OAUTH_REIDS_UTILS";

    /**
     * 权限信息存储Redis
     *
     * @return
     */
    public RedisUtil oAuthRedis() {
        if (redisUtilMap.containsKey(OAUTH_REIDS_UTILS)) {
            return redisUtilMap.get(OAUTH_REIDS_UTILS);
        }
        return createUtil(REDIS_POOL_KEY_PREFIX, OAUTH_REDIS_KEY_PREFIX, OAUTH_REIDS_UTILS);
    }

    /**
     * 创建Redis链接
     *
     * @return
     */
    private synchronized RedisUtil createUtil(String poolKeyPrefix, String configKeyPrefix, String mapKey) {
        RedisUtil util = new RedisTemplateUtil().createUtil(poolKeyPrefix, configKeyPrefix);
        redisUtilMap.put(mapKey, util);
        return util;
    }
}

package cn.xiaoniu.cloud.server.web.redis;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis 配置文件
 *
 * @author 孔明
 * @date 2020/4/23 14:17
 * @description cn.xiaoniu.cloud.server.web.redis.XNRedisProperties
 */
@ConfigurationProperties(prefix = "spring.redis")
public class XNRedisProperties {

    /**
     * 数据源
     */
    private Map<String, RedisProperties> sources = new HashMap<>();

    public Map<String, RedisProperties> getSources() {
        return sources;
    }

    public void setSources(Map<String, RedisProperties> sources) {
        this.sources = sources;
    }
}

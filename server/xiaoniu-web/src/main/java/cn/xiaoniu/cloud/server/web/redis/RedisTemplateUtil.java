package cn.xiaoniu.cloud.server.web.redis;

import cn.xiaoniu.cloud.server.web.util.PropertiesUtil;
import cn.xiaoniu.cloud.server.web.util.SpringUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 孔明
 * @date 2019/12/16 15:42
 * @description cn.xiaoniu.cloud.server.web.redis.RedisTemplateUtil
 */
public class RedisTemplateUtil {

    private static GenericObjectPoolConfig genericObjectPoolConfig = null;

    /**
     * 创建一个Util类
     *
     * @param poolConfigPrefix
     * @param redisConfigPrefix
     * @return
     */
    public static RedisUtil createUtil(String poolConfigPrefix, String redisConfigPrefix) {
        if (genericObjectPoolConfig == null) {
            genericObjectPoolConfig = createPoolConfig(poolConfigPrefix);
        }
        RedisStandaloneConfiguration redisStandaloneConfiguration = createRedisStandaloneConfiguration(redisConfigPrefix);
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(genericObjectPoolConfig).build();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfiguration);
        lettuceConnectionFactory.afterPropertiesSet();
        RedisTemplate<String, Object> redisTemplate = createRedisTemplate(lettuceConnectionFactory);
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedis(redisTemplate);
        return redisUtil;
    }

    /**
     * 创建连接池
     *
     * @param prefix
     * @return
     */
    private static GenericObjectPoolConfig createPoolConfig(String prefix) {
        return PropertiesUtil.configeProperties(SpringUtil.getApplicationContext().getEnvironment(), prefix, GenericObjectPoolConfig.class);
    }

    /**
     * 创建Redis配置类
     *
     * @param prefix
     * @return
     */
    private static RedisStandaloneConfiguration createRedisStandaloneConfiguration(String prefix) {
        return PropertiesUtil.configeProperties(SpringUtil.getApplicationContext().getEnvironment(), prefix, RedisStandaloneConfiguration.class);
    }

    /**
     * RedisTemplate 配置
     *
     * @param redisConnectionFactory
     * @param <T>
     * @return
     */
    public static <T> RedisTemplate<String, T> createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}

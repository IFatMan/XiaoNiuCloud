package cn.xiaoniu.cloud.server.web.redis;

import cn.xiaoniu.cloud.server.util.exception.UtilException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 孔明
 * @date 2020/4/23 16:01
 * @description cn.xiaoniu.cloud.server.web.redis.RedisDataSourceHolder
 */
@EnableConfigurationProperties(XNRedisProperties.class)
public class RedisDataSourceHolder implements InitializingBean {

    @Autowired
    private XNRedisProperties xnProperties;

    private static Map<String, RedisUtil> templateMap;

    private static ThreadLocal<RedisUtil> threadLocalUtil = new ThreadLocal<>();

    @Override
    public void afterPropertiesSet() {
        for (Map.Entry<String, RedisProperties> entry : xnProperties.getSources().entrySet()) {
            this.createRedisUtil(entry.getKey(), entry.getValue());
        }
    }

    public static void setLocalThreadUtil(String name) {
        RedisUtil util = templateMap.get(name);
        if (Objects.isNull(util)) {
            throw new UtilException(String.format("没有配置名称为%s的Redis数据源！", name));
        }
        threadLocalUtil.set(util);
    }

    /**
     * 执行
     *
     * @param supplier
     * @return T
     * @author 孔明
     * @date 2020-04-23 17:31:24
     */
    public static <T> T execute(RedisHolder<T> supplier) {
        RedisUtil util = threadLocalUtil.get();
        if (Objects.isNull(util)) {
            throw new UtilException("请配合@RedisSource使用！");
        }
        return supplier.execute(util);
    }

    /**
     * 清除
     */
    public static void clean() {
        threadLocalUtil.remove();
    }

    public static RedisUtil getRedisUtilByName(String name) {
        return templateMap.get(name);
    }

    private synchronized void createRedisUtil(String name, RedisProperties properties) {
        templateMap = new HashMap<>(xnProperties.getSources().size());

        RedisConnectionFactory factory = connectionFactory(properties);
        RedisTemplate<String, Object> template = createRedisTemplate(factory);
        RedisUtil redisUtil = new RedisUtil(template);
        templateMap.put(name, redisUtil);
    }

    /**
     * 配置工厂
     *
     * @param properties
     * @return
     */
    public RedisConnectionFactory connectionFactory(RedisProperties properties) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(properties.getHost());
        redisStandaloneConfiguration.setPort(properties.getPort());
        redisStandaloneConfiguration.setDatabase(properties.getDatabase());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(properties.getPassword()));

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        RedisProperties.Pool pool = properties.getLettuce().getPool();
        poolConfig.setMaxIdle(pool.getMaxIdle());
        poolConfig.setMinIdle(pool.getMinIdle());
        poolConfig.setMaxTotal(pool.getMaxActive());
        if (Objects.nonNull(pool.getMaxWait())) {
            poolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
        }
        if (Objects.nonNull(pool.getTimeBetweenEvictionRuns())) {
            poolConfig.setTimeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRuns().toMillis());
        }

        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfiguration);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    /**
     * RedisTemplate 配置
     *
     * @param redisConnectionFactory
     * @param <T>
     * @return
     */
    public <T> RedisTemplate<String, T> createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
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

    public interface RedisHolder<T> {

        T execute(RedisUtil util);

    }

}

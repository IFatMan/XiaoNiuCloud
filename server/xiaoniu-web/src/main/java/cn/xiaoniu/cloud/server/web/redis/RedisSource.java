package cn.xiaoniu.cloud.server.web.redis;

import java.lang.annotation.*;

/**
 * @author 孔明
 * @date 2020/4/23 18:08
 * @description cn.xiaoniu.cloud.server.web.redis.RedisSource
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisSource {

    /**
     * 数据源名称
     *
     * @return
     */
    String value();

}

package cn.xiaoniu.cloud.server.web.redis;

/**
 * @author 孔明
 * @date 2020/4/23 18:08
 * @description cn.xiaoniu.cloud.server.web.redis.RedisSource
 */
public @interface RedisSource {

    /**
     * 数据源名称
     *
     * @return
     */
    String name() default "";

}

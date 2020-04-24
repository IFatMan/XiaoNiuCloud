package cn.xiaoniu.cloud.server.api.config;

import cn.xiaoniu.cloud.server.web.authority.PermissionAspect;
import cn.xiaoniu.cloud.server.web.cors.CorsConfig;
import cn.xiaoniu.cloud.server.web.db.DruidConfig;
import cn.xiaoniu.cloud.server.web.redis.RedisSourceAspect;
import cn.xiaoniu.cloud.server.web.swagger.Swagger2Config;
import cn.xiaoniu.cloud.server.web.util.SpringUtil;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * @author 孔明
 * @date 2020/4/23 11:52
 * @description cn.xiaoniu.cloud.server.api.config.ImportConfig
 */
@Component
@Import({CorsConfig.class, Swagger2Config.class, SpringUtil.class, DruidConfig.class, RedisSourceAspect.class, PermissionAspect.class})
public class ImportConfig {
}

package cn.xiaoniu.cloud.server.web.cors;

import cn.hutool.core.collection.CollUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Objects;

/**
 * @author 孔明
 * @date 2020/4/23 11:01
 * @description cn.xiaoniu.cloud.server.web.cors.CorsConfig
 */
@EnableConfigurationProperties(CorsProperties.class)
public class CorsConfig {

    @Autowired(required = false)
    private CorsProperties properties;

    @Bean
    public CorsFilter getCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = buildConfig();

        source.registerCorsConfiguration(
                Objects.nonNull(properties) && StringUtils.isNotBlank(properties.getPath())
                        ? properties.getPath() : CorsProperties.Constant.PATH,
                config);

        return new CorsFilter(source);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration config = new CorsConfiguration();

        // 允许cookies跨域
        config.setAllowCredentials(
                Objects.nonNull(properties.getAllowedCredential()) ? properties.getAllowedCredential() : CorsProperties.Constant.ALLOWED_CREDENTIAL);

        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(
                Objects.nonNull(properties.getMaxAge()) ? properties.getMaxAge() : CorsProperties.Constant.MAX_AGE);

        // 允许向该服务器提交请求的URI，*表示全部允许。。这里尽量限制来源域，比如http://xxxx:8080 ,以降低安全风险。。
        config.setAllowedOrigins(
                CollUtil.isNotEmpty(properties.getAllowedOrigin()) ? properties.getAllowedOrigin() : CorsProperties.Constant.ALLOWED_ORIGIN);

        // 允许访问的头信息,*表示全部
        config.setAllowedHeaders(
                CollUtil.isNotEmpty(properties.getAllowedHeader()) ? properties.getAllowedHeader() : CorsProperties.Constant.ALLOWED_HEADER);


        // 允许访问的方法,*表示全部
        config.setAllowedMethods(
                CollUtil.isNotEmpty(properties.getAllowedMethod()) ? properties.getAllowedMethod() : CorsProperties.Constant.ALLOWED_METHOD);

        return config;
    }

}

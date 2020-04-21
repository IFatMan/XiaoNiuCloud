package cn.xiaoniu.cloud.server.web.config;

import cn.xiaoniu.cloud.server.web.dao.LoginCacheDao;
import cn.xiaoniu.cloud.server.web.interceptor.CurrCustomerInterceptor;
import cn.xiaoniu.cloud.server.web.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * @author 孔明
 * @date 2019/12/16 14:16
 * @description cn.xiaoniu.cloud.server.web.config.RegisterInterceptor
 */
@Configuration
public class RegisterInterceptor extends WebMvcConfigurationSupport {

    @Value("${spring.system.key.token:Access-Token}")
    private String tokenKey;

    @Value("${zgm.log.need:true}")
    private boolean needLog;

    @Value("#{'${zgm.log.path:/**}'.split(',')}")
    private String[] logPath;

    @Value("${zgm.context.need:true}")
    private boolean needContext;

    @Value("#{'${zgm.context.path:/**}'.split(',')}")
    private String[] contextPath;

    @Autowired(required = false)
    private LoginCacheDao loginCacheDao;

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    @Bean
    public CurrCustomerInterceptor customerInterceptor() {
        return new CurrCustomerInterceptor(tokenKey, loginCacheDao);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
//        if (needLog) {
//            registry.addInterceptor(logInterceptor()).addPathPatterns(logPath);
//        }
//        if (needContext) {
//            registry.addInterceptor(customerInterceptor()).addPathPatterns(contextPath).excludePathPatterns("/favicon.ico", "/swagger-ui.html", "/v2/api-docs");
//        }
        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
}

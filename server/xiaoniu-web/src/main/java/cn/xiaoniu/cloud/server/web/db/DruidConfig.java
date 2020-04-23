package cn.xiaoniu.cloud.server.web.db;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * @author 孔明
 * @date 2020/4/23 12:47
 * @description cn.xiaoniu.cloud.server.web.db.DruidConfig
 */
@EnableConfigurationProperties(DruidProperties.class)
public class DruidConfig {

    @Autowired
    private DruidProperties properties;

    /**
     * 数据源配置
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    /**
     * 配置监控服务器
     *
     * @return 返回监控注册的servlet对象
     * @author SimpleWu
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean servletRegistrationBean;
        if (StringUtils.isNotBlank(properties.getServletMapping())) {
            servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), properties.getServletMapping());
        } else {
            servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), DruidProperties.Constant.SERVLET_MAPPING);
        }

        Map<String, String> initParams = properties.getServlet();
        if (CollUtil.isEmpty(initParams)) {
            return servletRegistrationBean;
        }

        initParams.forEach((k, v) -> servletRegistrationBean.addInitParameter(k, v));
        return servletRegistrationBean;
    }

    /**
     * 配置服务过滤器
     *
     * @return 返回过滤器配置对象
     */
    @Bean
    public FilterRegistrationBean statFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        if(StringUtils.isNotBlank(properties.getFilterPatterns())) {
            // 添加过滤规则
            filterRegistrationBean.addUrlPatterns(properties.getFilterPatterns());
        }else {
            filterRegistrationBean.addUrlPatterns(DruidProperties.Constant.FILTER_PATTERNS);
        }
        // 忽略过滤格式
        Map<String, String> initParams = properties.getServlet();
        if (CollUtil.isEmpty(initParams)) {
            return filterRegistrationBean;
        }

        initParams.forEach((k, v) -> filterRegistrationBean.addInitParameter(k, v));
        return filterRegistrationBean;
    }
}

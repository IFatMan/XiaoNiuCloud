package cn.xiaoniu.cloud.server.web.db;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author 孔明
 * @date 2020/4/23 12:56
 * @description cn.xiaoniu.cloud.server.web.db.DruidProperties
 */
@ConfigurationProperties(prefix = "spring.datasource.init-param")
public class DruidProperties {

    /**
     * 映射路径
     */
    private String servletMapping;

    /**
     * 监控服务器配置
     */
    private Map<String, String> servlet;

    /**
     * 过滤路径规则
     */
    private String filterPatterns;

    /**
     * 服务过滤器配置
     */
    private Map<String, String> filter;

    public interface Constant {

        String SERVLET_MAPPING = "/druid/*";

        String FILTER_PATTERNS = "/*";
    }

    public String getServletMapping() {
        return servletMapping;
    }

    public void setServletMapping(String servletMapping) {
        this.servletMapping = servletMapping;
    }

    public Map<String, String> getServlet() {
        return servlet;
    }

    public void setServlet(Map<String, String> servlet) {
        this.servlet = servlet;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }

    public String getFilterPatterns() {
        return filterPatterns;
    }

    public void setFilterPatterns(String filterPatterns) {
        this.filterPatterns = filterPatterns;
    }
}

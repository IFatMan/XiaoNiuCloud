package cn.xiaoniu.cloud.server.web.cors;

import cn.xiaoniu.cloud.server.util.constant.CommonConstant;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;
import java.util.Objects;

/**
 * @author 孔明
 * @date 2020/4/23 10:49
 * @description cn.xiaoniu.cloud.server.web.cors.CorsProperties
 */
@ConfigurationProperties(prefix = "cors")
class CorsProperties {

    /**
     * 是否允许Cookies跨域
     */
    private Boolean allowedCredential;

    /**
     * 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
     */
    private Long maxAge;

    /**
     * 路径
     */
    private String path;

    /**
     * 允许向服务器提交请求的URI
     */
    private List<String> allowedOrigin;

    /**
     * 允许访问的头信息
     */
    private List<String> allowedHeader;

    /**
     * 允许提交的Http方法
     */
    private List<String> allowedMethod;


    /**
     * 默认值
     */
    public interface Constant {

        /**
         * '*'
         */
        Boolean ALLOWED_CREDENTIAL = Boolean.TRUE;

        /**
         * 默认30分钟
         */
        Long MAX_AGE = 1800L;

        /**
         *
         */
        String PATH = StringUtils.join(CommonConstant.CHAR_SLASH, CommonConstant.CHAR_ASTERIASK, CommonConstant.CHAR_ASTERIASK);

        /**
         *
         */
        List<String> ALLOWED_ORIGIN = Lists.newArrayList(CommonConstant.CHAR_ASTERIASK);

        /**
         *
         */
        List<String> ALLOWED_HEADER = Lists.newArrayList(CommonConstant.CHAR_ASTERIASK);

        /**
         * GET PUT POST DELETE
         */
        List<String> ALLOWED_METHOD = Lists.newArrayList(HttpMethod.GET.name(), HttpMethod.PUT.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name());

    }

    public Boolean getAllowedCredential() {
        return allowedCredential;
    }

    public void setAllowedCredential(Boolean allowedCredential) {
        this.allowedCredential = allowedCredential;
    }

    public List<String> getAllowedOrigin() {
        return allowedOrigin;
    }

    public void setAllowedOrigin(List<String> allowedOrigin) {
        this.allowedOrigin = allowedOrigin;
    }

    public List<String> getAllowedHeader() {
        return allowedHeader;
    }

    public void setAllowedHeader(List<String> allowedHeader) {
        this.allowedHeader = allowedHeader;
    }

    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    public List<String> getAllowedMethod() {
        return allowedMethod;
    }

    public void setAllowedMethod(List<String> allowedMethod) {
        this.allowedMethod = allowedMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

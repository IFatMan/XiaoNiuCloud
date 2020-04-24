package cn.xiaoniu.cloud.server.web.authority;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 孔明
 * @date 2020/4/24 11:01
 * @description cn.xiaoniu.cloud.server.web.authority.AuthorityProperties
 */
@ConfigurationProperties(prefix = "authority")
public class AuthorityProperties {

    /**
     * 登录令牌在Request Header中的名称
     */
    private String loginTokenName;


    public interface Constatnt {

        /**
         * 登录令牌在Request Header中默认的名称
         */
        String LOGIN_TOKEN_NAME = "AccessToken";

    }

    public String getLoginTokenName() {
        return loginTokenName;
    }

    public void setLoginTokenName(String loginTokenName) {
        this.loginTokenName = loginTokenName;
    }
}

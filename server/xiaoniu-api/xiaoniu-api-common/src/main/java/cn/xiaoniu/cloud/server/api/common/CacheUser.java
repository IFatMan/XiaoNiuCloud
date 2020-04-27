package cn.xiaoniu.cloud.server.api.common;

import cn.xiaoniu.cloud.server.api.model.po.User;
import cn.xiaoniu.cloud.server.util.context.CacheCustomer;

import java.util.List;

/**
 * @author 孔明
 * @date 2020/4/27 10:33
 * @description cn.xiaoniu.cloud.server.api.common.CacheUser
 */
public class CacheUser extends CacheCustomer {

    /**
     * 令牌
     */
    private String token;


    /**
     * 转换
     *
     * @param user
     * @param permissions
     * @return
     */
    public static CacheUser convertFromUser(User user, List<String> permissions) {
        CacheUser cacheUser = new CacheUser();
        cacheUser.setId(user.getId());
        cacheUser.setAccount(user.getAccount());
        cacheUser.setPermissions(permissions);
        return cacheUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

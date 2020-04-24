package cn.xiaoniu.cloud.server.util.context;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 缓存用户信息
 */
@Getter
@Setter
public class CacheCustomer implements Serializable {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 获取账户
     */
    private String account;

    /**
     * 权限编号
     */
    private List<String> permissions;

    public CacheCustomer() {
    }

    public CacheCustomer(Long id, String account) {
        this.id = id;
        this.account = account;
    }

}

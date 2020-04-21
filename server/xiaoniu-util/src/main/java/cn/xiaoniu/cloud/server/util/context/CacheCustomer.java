package cn.xiaoniu.cloud.server.util.context;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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

    public CacheCustomer() {
    }

    public CacheCustomer(Long id, String account) {
        this.id = id;
        this.account = account;
    }

}

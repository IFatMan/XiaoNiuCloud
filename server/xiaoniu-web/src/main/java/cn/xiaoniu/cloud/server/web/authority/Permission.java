package cn.xiaoniu.cloud.server.web.authority;

import java.lang.annotation.*;

/**
 * @author 孔明
 * @date 2020/4/24 11:34
 * @description cn.xiaoniu.cloud.server.web.authority.Permission
 */
@Login
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission{

    /**
     * 当前方法编号
     *
     * @return
     */
    String value();

}

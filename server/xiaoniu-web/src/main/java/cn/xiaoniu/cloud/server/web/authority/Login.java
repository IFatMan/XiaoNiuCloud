package cn.xiaoniu.cloud.server.web.authority;

import java.lang.annotation.*;

/**
 * 标注此注解说明需要登录权限
 *
 * @author 孔明
 * @date 2019/12/19 11:15
 * @description cn.xiaoniu.cloud.server.web.annotation.Login
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
}

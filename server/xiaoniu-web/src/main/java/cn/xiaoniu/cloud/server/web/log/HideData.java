package cn.xiaoniu.cloud.server.web.log;

import java.lang.annotation.*;

/**
 * 隐藏请求参数值
 */
@Documented
@Target(ElementType.PARAMETER)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface HideData {
}

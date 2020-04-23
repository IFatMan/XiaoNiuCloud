package cn.xiaoniu.cloud.server.web.log;

import java.lang.annotation.*;

/**
 * 标注此注解说明需要打印日志
 *
 * @author 孔明
 * @date 2019/12/19 11:15
 * @description cn.xiaoniu.cloud.server.web.annotation.PrintLog
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrintLog {

    /**
     * 方法描述
     */
    String value() default "";

}

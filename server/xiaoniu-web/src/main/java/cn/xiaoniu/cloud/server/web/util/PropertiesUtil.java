package cn.xiaoniu.cloud.server.web.util;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.xiaoniu.cloud.server.util.constant.CommonConstant;
import cn.xiaoniu.cloud.server.util.exception.UtilException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 配置文件工具类
 */
@UtilityClass
public class PropertiesUtil {

    /**
     * @param prefix
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T configeProperties(Environment environment, String prefix, Class<T> clazz) {
        return new ConfigeProperties<T>(environment, prefix, clazz).build();
    }

    /**
     * 将配置文件中的信息加载到JavaBean中
     */
    private class ConfigeProperties<T> {

        private Environment environment;

        /**
         * 配置属性前缀
         */
        private String prefix;

        /**
         * JavaBean Class
         */
        private Class<T> clazz;

        public ConfigeProperties(Environment environment, String prefix, Class<T> clazz) {
            this.environment = environment;
            this.prefix = prefix;
            this.clazz = clazz;

        }

        public T build() {
            try {
                T javaBean = clazz.newInstance();
                Field[] classFields = ReflectUtil.getFieldsDirectly(clazz, true);
                if (classFields == null || classFields.length <= 0) {
                    return javaBean;
                }
                for (Field classField : classFields) {
                    if (Modifier.isFinal(classField.getModifiers())) {
                        continue;
                    }
                    String fieldName = ReUtil.replaceAll(classField.getName(), "([A-Z]+)", "-$1").toLowerCase();

                    String propretyKey = StringUtils.join(this.prefix, CommonConstant.CHAR_DOT, fieldName);
                    Object propertyValue = environment.getProperty(propretyKey, classField.getType());
                    if (propertyValue == null) {
                        continue;
                    }
                    ReflectUtil.setFieldValue(javaBean, classField, propertyValue);
                }
                return javaBean;
            } catch (Exception e) {
                throw new UtilException(e, "将配置文件中的信息加载到JavaBean中");
            }
        }
    }

}

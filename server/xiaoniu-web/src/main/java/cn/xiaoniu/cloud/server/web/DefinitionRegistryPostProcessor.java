package cn.xiaoniu.cloud.server.web;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.web.filter.CorsFilter;

/**
 * 手动注入Bean
 * @author 孔明
 * @date 2019/12/16 14:25
 * @description cn.xiaoniu.cloud.server.web.config.DefinitionRegistryPostProcessor
 */
public class DefinitionRegistryPostProcessor implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerBeanDefinitions(CorsFilter.class, registry);
    }

    /**
     * 注入Bean到IOC容器
     *
     * @param clazz
     * @param registry
     */
    private void registerBeanDefinitions(Class clazz, BeanDefinitionRegistry registry) {
        String className = StrUtil.lowerFirst(clazz.getSimpleName());
        if (!registry.containsBeanDefinition(className)) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            BeanDefinition beanDefinition = builder.getBeanDefinition();
            registry.registerBeanDefinition(className, beanDefinition);
        }
    }
}

package cn.xiaoniu.cloud.server.web.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * 手动注入Bean
 * @author 孔明
 * @date 2019/12/16 14:25
 * @description cn.xiaoniu.cloud.server.web.config.DefinitionRegistryPostProcessor
 */
@Configuration
public class DefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.registerSingleton("corsFilter", Beans.getCorsFilter());
        beanFactory.registerSingleton("filterRegistrationBean", Beans.filterRegistrationBean());
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    }

}

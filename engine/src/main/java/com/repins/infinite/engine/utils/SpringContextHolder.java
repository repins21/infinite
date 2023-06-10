package com.repins.infinite.engine.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware, BeanFactoryPostProcessor {

    private static ApplicationContext applicationContext;
    private static DefaultListableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static DefaultListableBeanFactory getBeanFactory() {
        return beanFactory == null ? getBean(DefaultListableBeanFactory.class) : beanFactory;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
    }

}

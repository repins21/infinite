package com.repins.infinite.engine.loader;

import com.repins.infinite.engine.configuration.ProcessEngineConfiguration;
import com.repins.infinite.engine.constant.PackagePathConstant;
import com.repins.infinite.engine.service.ProcessService;
import com.repins.infinite.engine.utils.SpringContextHolder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

public class DefaultProcessEngineServiceRegister implements ProcessEngineServiceRegister {


    @Override
    public void loadServices(ProcessEngineConfiguration processEngineConfiguration) {
        DefaultListableBeanFactory beanFactory = SpringContextHolder.getBeanFactory();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanFactory);
        scanner.addIncludeFilter(new AnnotationTypeFilter(InfiniteService.class));

        Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(PackagePathConstant.SERVICE_PATH);
        for (BeanDefinition beanDefinition : beanDefinitions) {
            String beanName = beanDefinition.getBeanClassName();
            beanFactory.registerBeanDefinition(beanName, beanDefinition);
        }

        // autoConfiguration when all the services loaded
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Object instance = SpringContextHolder.getBean(beanDefinition.getBeanClassName());
            if (instance instanceof ProcessService) {
                ((ProcessService) instance).autoConfiguration(processEngineConfiguration);
            }
        }

    }

}

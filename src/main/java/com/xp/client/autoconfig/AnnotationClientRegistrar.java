package com.xp.client.autoconfig;

import com.xp.client.annotation.ReactClient;
import com.xp.client.annotation.ReactClientScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 根据指定的包路径进行扫描，如果没有自定包路径，则在当前类所在路径进行扫描
 *
 * @see ReactClientScan 通过注解加载该类
 */
@Slf4j
public class AnnotationClientRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(
            AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Set<String> packages = getPackages(importingClassMetadata);
        ReactClientDefinitionScanner scanner = new ReactClientDefinitionScanner(registry);

        scanner.scan(packages.toArray(new String[]{}));
    }

    private Set<String> getPackages(AnnotationMetadata metadata) {
        AnnotationAttributes attributes =
                AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(ReactClientScan.class.getName()));
        String[] packages = attributes.getStringArray("packages");
        Set<String> packagesToScan = new LinkedHashSet<>(Arrays.asList(packages));
        if (packagesToScan.isEmpty()) {
            packagesToScan.add(ClassUtils.getPackageName(metadata.getClassName()));
        }
        return packagesToScan;
    }

    /**
     * 为client生成一个代理
     */
    private void registerBeans(BeanFactory factory, Set<BeanDefinition> beanDefinitions) {
        for (BeanDefinition bean : beanDefinitions) {
            String className = bean.getBeanClassName();
            if (StringUtils.isEmpty(className)) {
                continue;
            }
            try {
                Class<?> target = Class.forName(className);

            } catch (ClassNotFoundException e) {
                log.warn("class not found: {}", className);
            }
        }
    }

    /**
     * 继承ClassPathBeanDefinitionScanner，添加filter实现对ReactClient的加载
     */
    private static class ReactClientDefinitionScanner extends ClassPathBeanDefinitionScanner {

        public ReactClientDefinitionScanner(BeanDefinitionRegistry registry) {
            super(registry);
            addIncludeFilter(new AnnotationTypeFilter(ReactClient.class));
        }
    }

}

package com.fosuchao.rpc.config;

import com.fosuchao.rpc.annotations.RpcInterface;
import com.fosuchao.rpc.client.proxy.ProxyFactory;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @description:
 * @author: Joker Ye
 * @create: 2020/2/25 14:28
 * @noinspection SpringFacetCodeInspection
 */
@Configuration
public class RpcConfig implements ApplicationContextAware, InitializingBean {
    private ApplicationContext applicationContext;

    public void afterPropertiesSet() throws Exception {
        // 注册Rpc服务
        Reflections reflections = new Reflections("com.fosuchao");
        DefaultListableBeanFactory factory =
                (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        // 获取有RpcInterface注解的服务
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(RpcInterface.class);
        for (Class<?> aClass : set) {
            factory.registerSingleton(aClass.getSimpleName(), ProxyFactory.create(aClass));
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

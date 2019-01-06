package com.insigma.spring;

/**
 * Created by admin on 2018/11/16.
 */
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
public class MyBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // TODO Auto-generated method stub
        System.out.println("BeanPostProcessor.postProcessAfterInitialization ");
        return bean;
    }
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // TODO Auto-generated method stub
        System.out.println("BeanPostProcessor.postProcessBeforeInitialization");
        return bean;
    }
}

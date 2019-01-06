package com.insigma.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.beans.PropertyDescriptor;

/**
 * Created by admin on 2018/11/16.
 */
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // TODO Auto-generated method stub
        System.out.println("InstantiationAwareBeanPostProcessor.postProcessAfterInitialization");
        return bean;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // TODO Auto-generated method stub
        System.out.println("InstantiationAwareBeanPostProcessor.postProcessBeforeInitialization");
        return bean;
    }

    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        // TODO Auto-generated method stub
        System.out.println("InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation");
        return true;
    }

    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        // TODO Auto-generated method stub
        System.out.println("InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation");
        return null;
    }

    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean,
                                                    String beanName) throws BeansException {
        // TODO Auto-generated method stub
        System.out.println("InstantiationAwareBeanPostProcessor.postProcessPropertyValues");
        return pvs;
    }
}
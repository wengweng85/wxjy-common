package com.insigma.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by admin on 2018/11/16.
 */

    public class LifeCycle implements BeanFactoryAware,BeanNameAware,InitializingBean,DisposableBean,ApplicationContextAware {

    private String name;

    public String getName() {
        System.out.println("getName name=" + name);
        return name;
    }

    public void setName(String name) {
        System.out.println("setName name=" + name);
        this.name = name;
    }

    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        System.out.println("InitializingBean.afterPropertiesSet()");
    }

    public void setBeanName(String arg0) {
        // TODO Auto-generated method stub
        System.out.println("BeanNameAware.setBeanName");
    }

    public void setBeanFactory(BeanFactory arg0) throws BeansException {
        // TODO Auto-generated method stub
        System.out.println("BeanFactoryAware.setBeanFactory");
    }

    public void destroy() throws Exception {
        // TODO Auto-generated method stub
        System.out.println("DisposableBean.destroy");
    }

    public void myInit() {
        System.out.println("【init-method】调用<bean>的init-method属性指定的初始化方法");
    }

    public void myDestory() {
        System.out.println("【destroy-method】调用<bean>的destroy-method属性指定的初始化方法");
    }

    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        // TODO Auto-generated method stub
        System.out.println("ApplicationContextAware.setApplicationContext");
    }
}

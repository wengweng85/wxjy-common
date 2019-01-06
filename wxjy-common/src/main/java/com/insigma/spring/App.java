package com.insigma.spring;

import com.insigma.spring.LifeCycle;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by admin on 2018/11/16.
 */
public class App
{
    public static void main( String[] args )
    {
        ApplicationContext context= new FileSystemXmlApplicationContext(new String[]{"src/ApplicationContext.xml"});
        BeanFactory factory=context;
        LifeCycle lifeCycle=factory.getBean("lifeCycle",LifeCycle.class);
        lifeCycle.setName("cuiyw2");
        System.out.println("lifeCycle.name="+lifeCycle.getName());
        ((FileSystemXmlApplicationContext)factory).registerShutdownHook();
         /*service=(IService)factory.getBean("ServiceA");
         service.service("Cuiyw ServiceA");
         service=(IService)factory.getBean("ServiceImpl");
         service.service("Cuiyw ServiceImpl"); */
    }
}

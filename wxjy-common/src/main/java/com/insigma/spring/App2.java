package com.insigma.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by admin on 2018/11/16.
 */
public class App2 {

    @Test
    public void Test(){

        ApplicationContext applicationContext= new FileSystemXmlApplicationContext(new String[]{"src/ApplicationContext.xml"});
        HelloWorld helloWorld1=(HelloWorld)applicationContext.getBean("hello");
        HelloWorld helloWorld2=(HelloWorld)applicationContext.getBean("hello");
        System.out.println(helloWorld1==helloWorld2);

        Car car=(Car) applicationContext.getBean("car");
        car.run();

    }
}

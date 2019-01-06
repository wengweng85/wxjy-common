package com.insigma.spring;

/**
 * Created by admin on 2018/11/16.
 */
public class HelloWorld {
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public HelloWorld(){
        System.out.println("执行构造函数！");
    }
}

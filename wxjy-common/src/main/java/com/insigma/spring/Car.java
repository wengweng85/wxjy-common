package com.insigma.spring;

/**
 * Created by admin on 2018/11/16.
 */
public class Car {

    private int maxSpeed;
    private String brand;
    private double price;

    public Car(){

    }
    //带参构造方法
    public Car(int maxSpeed,String brand, double price){
        this.maxSpeed=maxSpeed;
        this.brand=brand;
        this.price=price;
    }

    //一定要写被注入对象的set方法
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void run(){
        System.out.println("brand:"+brand+",maxSpeed:"+maxSpeed+",price:"+price);
    }
}

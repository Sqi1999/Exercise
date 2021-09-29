package com.shaoqi.exercise.DesignMode.Observer.v4;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/9/28 17:21
 */
public class Main {


    public static void main(String[] args) {
        Child c=new Child();
        c.wakeUp();
    }

}
class Child{
    private boolean cry=false;
    private Dad dad=new Dad();
    private Mum mum=new Mum();
    private Dog dog=new Dog();

    public boolean isCry() {
        return cry;
    }
    public void wakeUp(){
        cry=true;
        dad.feed();
        mum.wang();
        dog.hug();
    }
}
class  Dad{

    public void feed() {
        System.out.println("asdsaf");
    }
}
class Mum{

    public void wang() {
        System.out.println("asdefa");
    }
}
class Dog{

    public void hug() {
        System.out.println("gfdsgfd");
    }
}

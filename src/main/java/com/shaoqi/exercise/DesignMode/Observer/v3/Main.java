package com.shaoqi.exercise.DesignMode.Observer.v3;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/9/28 17:15
 */
public class Main {
    public static void main(String[] args) {
        Child c=new Child();
        c.wakeUp();
    }
}
class Child{
    private boolean cry=false;
    private Dad d;

    public boolean isCry() {
        return cry;
    }
    public void wakeUp(){//调用触发监听
        cry=true;
        d.feed();
    }
}
class Dad{

    public void feed() {
        System.out.println("affafafa");
    }
}

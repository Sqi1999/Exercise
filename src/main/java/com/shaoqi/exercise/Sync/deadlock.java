package com.shaoqi.exercise.Sync;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/10/9 10:37
 */
public class deadlock implements Runnable {

    public static int flag = 1;

    //static 变量是 类对象共享的
    static Object o1 = new Object();
    static Object o2 = new Object();

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "：此时 flag = " + flag);
        if(flag == 1){
            synchronized (o1){
                try {
                    System.out.println("我是" + Thread.currentThread().getName() + "锁住 o1");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + "醒来->准备获取 o2");

                }catch (Exception e){
                    e.printStackTrace();
                }
                synchronized (o2){
                    System.out.println(Thread.currentThread().getName() + "拿到 o2");//第24行
                }
            }
        }
        if(flag == 0){
            synchronized (o2){
                try {
                    System.out.println("我是" + Thread.currentThread().getName() + "锁住 o2");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + "醒来->准备获取 o1");
                }catch (Exception e){
                    e.printStackTrace();
                }
                synchronized (o1){
                    System.out.println(Thread.currentThread().getName() + "拿到 o1");//第38行
                }
            }
        }
    }

    public static  void main(String args[]){

        deadlock t1 = new deadlock();
        deadlock t2 = new deadlock();
        t1.flag = 1;
        new Thread(t1).start();

        //让main线程休眠1秒钟,保证t2开启锁住o2.进入死锁
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.flag = 0;
        new Thread(t2).start();

    }


}

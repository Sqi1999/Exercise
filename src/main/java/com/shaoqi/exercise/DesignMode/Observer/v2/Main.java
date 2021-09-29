package com.shaoqi.exercise.DesignMode.Observer.v2;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/9/28 17:06
 */

    class Child{
        private  boolean cry=false;

        public boolean isCry() {
            return cry;
        }
        public void wakeUp(){
            System.out.println("wiwiwii");
            cry=true;
        }
    }

    public class Main{
        public static void main(String[] args) {
            Child chile=new Child();
            while (!chile.isCry()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println("fasfsddd");
        }
    }


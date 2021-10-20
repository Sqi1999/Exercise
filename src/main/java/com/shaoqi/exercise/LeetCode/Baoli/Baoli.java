package com.shaoqi.exercise.LeetCode.Baoli;/**
 * @author  shaoqi
 * @date  2021/10/15 14:24
 * @version 1.0
 */public class Baoli {
     //暴力
     public static int ways1(int N,int start,int aim,int k){
         return process1(start,k,aim,N);
     }

    /**
     *
     * @param cur 机器人当前来到的位置是Cur
     * @param rest 机器人还有rest步要去走
     * @param aim   最终目标aim
     * @param N     有哪些位置 1--N
     * @return  机器人从cur出发，走过rest步之后，最终停留在aim的方法数是多少
     */
    public static int process1(int cur,int rest,int aim,int N){
        if (rest==0) return cur==aim?1:0;
        if (cur==1) return 1;
        return 1;
    }
}

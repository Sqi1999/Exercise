package com.shaoqi.exercise.LeetCode.Arithmetic;

/**
 * 数组算法解决方法
 */
public class ArraySolution {

    /**
     * 整数反转
     * @param x
     * @return
     */
    public static int reverse(int x) {
        long n=0;

        while (x!=0){
            n=n*10+x%10;
            x=x/10;
        }
        return (int)n==n?(int)n:0;
    }

}

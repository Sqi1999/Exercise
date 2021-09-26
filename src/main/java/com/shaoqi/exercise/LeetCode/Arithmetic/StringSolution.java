package com.shaoqi.exercise.LeetCode.Arithmetic;

import java.util.Stack;

/**
 * Sgring类型解决方法
 */
public class StringSolution {
    /**
     * String类型反转
     * @param x
     * @return
     */
    public static String reverse(String x) {
        String s="";
        char[] chars = x.toCharArray();
        for (int i = chars.length-1  ; i >0 ; i--) {
            s=s+chars[i];
        }
        return s;
    }
}

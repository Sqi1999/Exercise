package com.shaoqi.exercise.Test;

import com.shaoqi.exercise.LeetCode.Arithmetic.ArraySolution;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArraySolutionTest {
    @Before
    public void Before(){
        System.out.println("开始");
    }
    @After
    public void After(){
        System.out.println("结束");
    }

    @Test
    public void reverse(){
        int reverse = ArraySolution.reverse(2325);
        Assert.assertEquals(5232,reverse);
    }
    @Test
    public void maxArea(){
        int [] a={3,6,2,4,8,6,2,1,4,6};
        int i = ArraySolution.maxArea(a);
        System.out.println(i);
    }
}

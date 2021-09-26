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
}

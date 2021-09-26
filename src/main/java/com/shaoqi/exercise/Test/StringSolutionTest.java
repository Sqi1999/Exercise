package com.shaoqi.exercise.Test;

import com.shaoqi.exercise.LeetCode.Arithmetic.StringSolution;
import org.junit.Assert;
import org.junit.Test;

public class StringSolutionTest {

    @Test
    public void Stringreverse(){
        String s = StringSolution.reverse("n你好不好");
        Assert.assertEquals("好不好你",s);


    }
}

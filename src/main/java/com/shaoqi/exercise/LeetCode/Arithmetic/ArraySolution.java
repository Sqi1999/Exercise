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

    /**
     * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点(i,ai) 。
     * 在坐标内画 n 条垂直线，垂直线 i的两个端点分别为(i,ai) 和 (i, 0) 。
     * 找出其中的两条线，使得它们与x轴共同构成的容器可以容纳最多的水。
     * @param height
     * @return
     * 求最大面积 首先要知道底和高  底两个数组位置相减求得底部长度，高度比取两个数小的为高度
     * [3,6,3,9,8,4,2,5]  3~5  (底)7-0   （高）3<5 所以面积为15
     * 同时还要记住 最大的坐标和面积
     */
    public static int maxArea(int[] height) {
        int l=0; int r=height.length-1; int sum=0;
        while(l<r){
            int are=Math.min(height[l],height[r])*(r-l);
            sum=Math.max(sum,are);
            if(height[l]<=height[r]){
                ++l;
            }else{
                --r;
            }
        }
        return sum;
    }

}

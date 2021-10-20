package com.shaoqi.exercise.LeetCode.Linked;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author shaoqi
 * @version 1.0
 * @date 2021/10/9 15:56
 * 判断链表是否有幻
 */
public class Solution {

    class ListNode {
      int val;
      ListNode next;
      ListNode(int x) {
          val = x;
          next = null;
      }

//    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public static void main(String[] args) {
        System.out.println((1+(2-5))>>1);
    }

//    public boolean hasCycle(ListNode head) {
//        if (head==null){
//            return  true;
//        }
//        ListNode a=head;
//        ListNode b=head;
//        while (a.next!=null&&a.next.next!=null){
//            a=a.next.next;
//            b=b.next;
//            if (a==b){
//                return true;
//            }
//        }
//        return false;
//    }

//    public ListNode detectCycle(ListNode head) {
//        if (head==null){
//            return null;
//        }
//        ListNode a=head,b=head;
//        while (a!=null&&a.next!=null&&b!=null){
//            a=a.next.next;
//            b=b.next;
//            if (a==b){
//                break;
//            }
//        }
//        if (a==null&&a.next==null) return  null;
//
//        b=head;
//        while (b!=null&&a!=null){
//            if (a==b){
//                break;
//            }
//            b=b.next;
//            a=a.next;
//        }
//        return a;
//    }


//    public ListNode removeNthFromEnd(ListNode head, int n) {
//        ListNode node=new ListNode(0,head);
//        ListNode a=node,b=head;
//        for (int i=0;i<n-1;i++){
//            a=a.next;
//        }
//        while (a.next!=null){
//            b=b.next;
//            a=a.next;
//        }
//        b.next=b.next.next;
//        return node.next;
//    }


//    public ListNode reverseList(ListNode head) {
//        if (head==null||head.next==null){
//            return head;
//        }
//        ListNode tmp=null;
//        ListNode res=null;
//        if (head!=null){
//           tmp=head;
//           head=tmp.next;
//
//           tmp.next=res;
//           res=tmp;
//        }
//return res;
//    }


//    public ListNode removeElements(ListNode head, int val) {
//        if (head==null){
//            return  head;
//        }
//       head.next=removeElements(head.next,val);
//        return head.val==val?head.next:head;
//    }

    //03
//    public int findRepeatNumber(int[] nums) {
//        Set<Integer> set=new HashSet();
//        for (int n:nums) {
//            if (set.contains(n)) return n;
//            set.add(n);
//        }
//        return -1;
//
//    }

    //04
//    public boolean findNumberIn2DArray(int[][] matrix, int target) {
//        int i=matrix.length-1; int j=0;
//        while (i>=0&&j<matrix[0].length){
//            if (matrix[i][j]>target) i--;
//            else if(matrix[i][j]<target) j++;
//            else  return  true;
//        }
//        return false;
//    }

    //11
//暴力
//    public int minArray(int[] numbers) {
//        int mincount=numbers[0];
//        for (int i = 0; i <numbers.length ; i++) {
//            if (numbers[i]<mincount){
//                mincount=numbers[i];
//            }
//        }
//        return  mincount;
//    }

    //50
//    public char firstUniqChar(String s) {
//        HashMap<Character,Boolean> hashMap=new HashMap<>();
//        char[] chars = s.toCharArray();
//        for (char c:chars) {
//            hashMap.put(c,!hashMap.containsKey(c));
//        }
//        for (char c:chars) {
//            if (hashMap.get(c)) return c;
//        }
//        return ' ';
//    }

    /**
     * 53-1
     */
    //暴力
//    public int search(int[] nums, int target) {
//        int count=0;
//        for (int a:nums) {
//            if (a==target) count++;
//        }
//        return count;
//    }
    /**
     * 53-2
     * mid=(L+(L-R))>>1
     */
//    public int missingNumber(int[] nums) {
//        int i=0;int j=nums.length-1;
//        while (i<=j){
//            int mid=(i+(i-j))>>1;
//            if (nums[mid]==mid) i=mid+1;
//            else j=mid-1;
//        }
//        return i;
//    }

//    public String replaceSpace(String s) {
//        return s.replaceAll(" ","%20");
//    }

//    public ListNode reverseList(ListNode head) {
//if (head==null){
//    return  head;
//}
//ListNode tmp=null;
//ListNode res=null;
//while (head!=null){
//    tmp=head;
//    head.next=tmp;
//
//    tmp.next=res;
//    res=head;
//}
//return res;
//    }

//    public String reverseLeftWords(String s, int n) {
//        return  s.substring(n)+s.substring(0,n);
//    }


    public int fib(int n) {
        if (n==0||n==1) return n;
        return fib(n+1)+fib(n+2);
    }
}

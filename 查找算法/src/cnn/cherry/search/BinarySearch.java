package cnn.cherry.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: DataStructures
 * @description: 二分查找  递归  要查找的数组必须是有序的
 * @author: Mr.Cherry
 * @create: 2019-12-25 18:56
 **/
public class BinarySearch {
    public static void main(String[] args) {
        int arr[] = {1,1,1,2,5,6,7,42,64,75,75,75,84,91,755,755,755};
        List<Integer> list = binarySearchs(arr, 0, arr.length - 1, 77);
        System.out.println("i = " + list);
    }

    /**
     * 二分查找 使用递归
     * @param arr 待查找的数组
     * @param left 左边的索引
     * @param right 右边的索引
     * @param value 要查找的值
     * @return
     */
    public static int binarySearch(int[] arr,int left,int right,int value){
        //分割下标
        int mid = (left + right) / 2;
        //分割值
        int midVal = arr[mid];
        //当left > right说明数组全部查找完成 但是没有要找的值 返回-1
        if(left > right){
            return  -1;
        }else if (midVal > value){
            //当分割值 大于要找的值时 向左递归
            // 右下标是当前mid分割下标-1 因为它已经确定不是可以省略
            return binarySearch(arr,left,mid -1,value);
        }else if (midVal < value){
            //当分割值 小于要找的值时 向右递归
            // 左下标是当前mid分割下标+1 因为它已经确定不是可以省略
            return binarySearch(arr,mid + 1,right,value);
        }else{
            //上面的条件都不满足 说明当前的分割值等于要查找的值 直接返回下标
            return mid;
        }
    }


    public static List<Integer> binarySearchs(int[] arr,int left,int right,int value){
        //分割下标
        int mid = (left + right) / 2;
        //分割值
        int midVal = arr[mid];
        //当left > right说明数组全部查找完成 但是没有要找的值 返回-1
        if(left > right){
            return Arrays.asList();
        }else if (midVal > value){
            //当分割值 大于要找的值时 向左递归
            // 右下标是当前mid分割下标-1 因为它已经确定不是可以省略
            return binarySearchs(arr,left,mid -1,value);
        }else if (midVal < value){
            //当分割值 小于要找的值时 向右递归
            // 左下标是当前mid分割下标+1 因为它已经确定不是可以省略
            return binarySearchs(arr,mid + 1,right,value);
        }else{
            int index = mid;
            List<Integer> result = new ArrayList();
            //在index > 0 的前提下 左查找是否存在相同的值如果存在
            //就把下标存在list中
            while (index > 0 && arr[--index] == midVal){
                result.add(index);
            }
            //将分割值放入list
            result.add(mid);
            //重置index
            index = mid;
            //在index小于arr.length-1 前提下 右查找是否存在相同的值如果存在、
            //就把下标存在list中
            while (index < arr.length-1 && arr[++index] == midVal){
                result.add(index);
            }
            return result;
        }
    }

}

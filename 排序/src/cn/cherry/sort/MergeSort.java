package cn.cherry.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @program: DataStructures
 * @description: 归并排序
 * @author: Mr.Cherry
 * @create: 2019-12-24 19:07
 **/
public class MergeSort {
    public static void main(String[] args) {
        /*int arr[] = {8,4,5,7,1,3,6,2};
        int temp[] = new int[arr.length];
        mergeSort(arr,0,arr.length-1,temp,"第一次主方法");
        System.out.println(Arrays.toString(arr));*/

        //测试速度
        int[] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            //Math.random() 生成一个[0 - 800000] 之间的随机数 Math.random()默认生成0-1之间
            arr[i] = (int)(Math.random() * 800000);
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是：" + format1);
        int temp[] = new int[arr.length];
        mergeSort(arr,0,arr.length-1,temp,"第一次主方法");
        Date date2 = new Date();
        String format2 = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是：" + format2);
        //  System.out.println(Arrays.toString(arr));
    }

    /**
     * 分    八万个数据大约1秒内   8百万个数据大约2秒
     * @param arr 排序的原始数组
     * @param left 开始位置  最左边
     * @param right 结束位置 最右边
     * @param temp 中转数组
     * @param logo 标识是谁调的  用于打印理解
     */
    public static void mergeSort(int[] arr,int left,int right,int[] temp,String logo){
        if(left < right){
            //中间索引
            int mid = (left + right) / 2;
            //System.out.println("mid = " + mid + "   left = " + left + "  right = " + right + "  --------  " + logo);
            //向左递归进行分解
            mergeSort(arr, left, mid, temp,"向左递归分解");
            //向右递归进行分解
            mergeSort(arr, mid + 1, right, temp,"向右递归分解");
           // System.out.println("mid = " + mid + "   left = " + left + "  right = " + right + "  --------  " + logo);
            //治
            merge(arr,left,mid,right,temp,logo);


        }
    }


    /**
     * 治
     * @param arr 排序的原始数组
     * @param left 左边有序序列的初始化索引
     * @param mid 中间索引  左边有序序列的最后一个位置
     * @param right 右边索引  右边有序序列的最后一个索引
     * @param temp 中转数组
     * @param logo 标识是谁调的  用于打印理解
     */
    public static void merge(int[] arr,int left,int mid,int right,int[] temp,String logo){
       // System.out.println("mid = " + mid + "   left = " + left + "  right = " + right + " ============== " + logo);
        //初始化i 左边有序序列的初始索引
        int i = left;
        //初始化j 右边有序序列的初始索引
        int j = mid + 1;
        //指向temp数组的当前索引
        int t = 0;
        /**
         * 一：
         * 先把左右两边(有序)的数据按照规则填充到temp数组
         * 直到左右两边的有序序列 有一边处理完毕为止
         */
        while (i <= mid && j <= right){
            //如果左边的有序序列的当前元素，小于等于右边有序序列的当前元素
            //即，将左边的当前元素 拷贝到temp数组
            if(arr[i] <= arr[j]){
                temp[t] = arr[i];
                ++i;
            }else{
                //反之则是 右边的当前元素小于左边的当前元素
                temp[t] = arr[j];
                ++j;
            }
            ++t;
        }
        /**
         * 二：
         * while循环结束后 终会有一边的有序序列还存在数据
         * 需要把它有序的 填充到temp中
         */
        while (i <= mid){
            temp[t] = arr[i];
            ++i;
            ++t;
        }
        while (j <= right){
            temp[t] = arr[j];
            ++j;
            ++t;
        }
        /**
         * 三：
         * 将temp数组的元素拷贝到arr
         * 注意 并不是每次都拷贝所有
         */
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right){
            arr[tempLeft] = temp[t];
            ++t;
            ++tempLeft;
        }

    }
}

package cn.cherry.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @program: DataStructures
 * @description: 选择排序  选择式排序也属于内部排序法，是从预排序的数据中，按指定的规则选出某一元素，再依规定交换位置后达到排序的目的。
 * 选择排序思想:
    选择排序（select sorting）也是一种简单的排序方法。它的基本思想是：
    第一次从arr[0]~arr[n-1]中选取最小值，与arr[0]交换，
    第二次从arr[1]~arr[n-1]中选取最小值，与arr[1]交换，
    第三次从arr[2]~arr[n-1]中选取最小值，与arr[2]交换，…，
    第i次从arr[i-1]~arr[n-1]中选取最小值，与arr[i-1]交换，…,
    第n-1次从arr[n-2]~arr[n-1]中选取最小值，与arr[n-2]交换，总共通过n-1次，得到一个按排序码从小到大排列的有序序列。
 * @author: Mr.Cherry
 * @create: 2019-12-23 21:27
 **/
public class SelectSort {
    public static void main(String[] args) {
        /*int arr[] = {3,9,-1,10,20,12,99,45,6,8};
        System.out.printf("排序前的数组%s\n", Arrays.toString(arr));
        selectSort(arr);
        System.out.printf("排序后的数组%s\n", Arrays.toString(arr));*/

        //测试速度
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            //Math.random() 生成一个[0 - 800000] 之间的随机数 Math.random()默认生成0-1之间
            arr[i] = (int)(Math.random() * 800000);
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是：" + format1);

        selectSort(arr);
        Date date2 = new Date();
        String format2 = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是：" + format2);

    }

    /**
     * 选择排序 时间复杂度O(n^2)  比冒泡要快
     * @param arr 要排序的数组
     */
    public static void selectSort(int[] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            //初始化假定 一个最小值 和 最小值的下标
            int minIndex = i;
            int min = arr[i];
            for(int j = i + 1; j < arr.length; j++){
                if(min > arr[j]){
                    //说明假定的最小值并不是最小的  如果想要从大到小判断条件改为min < arr[j]即可
                    //重置最小值 和 最小值的下标
                    min = arr[j];
                    minIndex = j;
                }
            }
            //将当前的最小值放在 arr[i]的位置 即交换
            if(minIndex != i){
                //只有发生改变时(存在比arr[i]小的值)才交换位置
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
            //System.out.printf("第%d轮后数组的变化%s\n",i+1, Arrays.toString(arr));
        }
    }
}

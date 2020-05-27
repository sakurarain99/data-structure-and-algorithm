package cn.cherry.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @program: DataStructures
 * @description: 冒泡排序
 * 冒泡排序（Bubble Sorting）的基本思想是：
 * 通过对待排序序列从前向后（从下标较小的元素开始）,依次比较相邻元素的值，若发现逆序则交换，使值较大的元素逐渐从前移向后部
 * @author: Mr.Cherry
 * @create: 2019-12-23 20:42
 **/
public class BubbleSort {
    public static void main(String[] args) {
        /*int arr[] = {3,9,-1,10,20};
        System.out.printf("排序前的数组%s\n",Arrays.toString(arr));
        bubbleSort(arr);
        System.out.printf("排序后的数组%s\n",Arrays.toString(arr));*/

        //测试冒泡排序的速度O(n^2) 给80000个数据 测试
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            //Math.random() 生成一个[0 - 800000] 之间的随机数 Math.random()默认生成0-1之间
            arr[i] = (int)(Math.random() * 800000);
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是：" + format1);

        bubbleSort(arr);
        Date date2 = new Date();
        String format2 = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是：" + format2);


    }


    public static void bubbleSort(int[] arr){
        //辅助变量
        int temp = 0;
        //标识变量 表示是否进行过交换
        boolean flag = false;
        //冒泡排序 时间复杂度O(n^2)
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++){
                if(arr[j] > arr[j + 1]){
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
           // System.out.printf("第%d趟排序后的数组%s\n",i+1,Arrays.toString(arr));
            if(!flag){
                //在某趟排序中 一次都没有发生过交换
                break;
            }else{
                //重置flag 进行下一次判断
                flag = false;
            }
        }
    }
}

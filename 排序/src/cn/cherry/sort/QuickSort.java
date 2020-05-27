package cn.cherry.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @program: DataStructures
 * @description: 快速排序
 * @author: Mr.Cherry
 * @create: 2019-12-24 14:44
 **/
public class QuickSort {
    public static void main(String[] args) {
        /*int[] arr = {-9,78,0,23,-567,70,-1,900,4561,50};

        quickSort(arr,0,arr.length-1);
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

        quickSort(arr,0,arr.length-1);
        Date date2 = new Date();
        String format2 = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是：" + format2);
        //System.out.println(Arrays.toString(arr));
    }

    /**
     * 快速排序  八万个数据大约1秒内   8百万个数据大约一秒
     * @param arr 待排序的数组
     * @param left 左下标 数组的最左边
     * @param right 右下标
     */
    public static void quickSort(int[] arr,int left,int right){
        //左下标
        int l = left;
        //右下标
        int r = right;
        //pivot 中轴值
        int pivot = arr[left];
        //临时遍历
        int temp = 0;
        //while循环的目的时让比pivot值小的放到左边，大的放在右边
        while (l < r){
            //在pivot的左边一直找 找到大于等于pivot的值 才退出
            while (arr[l] < pivot){
                l += 1;
            }
            //在pivot的右边一直找 找到小于等于pivot的值 才退出
            while (arr[r] > pivot){
                r -= 1;
            }
            //如果l >= r 说明怕pivot的左右两边的值
            // 已经按照左边的值全部小于pivot的值  右边的值全部大于pivot的值
            if(l >= r){
                break;
            }
            //交换
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            //用于结束while
            //如果交换完后 发现这个arr[l] == pivot的值 r-- 前移
            if(arr[l] == pivot){
                r -= 1;
            }
            //如果交换完后 发现这个arr[r] == pivot的值 l++ 后移
            if(arr[r] == pivot){
                l += 1;
            }
        }
        //如果 l == r 必须l++ r-- 否则会出现栈内存溢出
        if(l == r){
            l += 1;
            r -= 1;
        }
        if (r > left){
            quickSort(arr,left,r);
        }
        if (right > l){
            quickSort(arr,l,right);
        }
    }
}

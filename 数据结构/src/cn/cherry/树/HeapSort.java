package cn.cherry.树;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @program: DataStructures
 * @description: 堆排序
 * @author: Mr.Cherry
 * @create: 2019-12-29 11:02
 **/
public class HeapSort {
    public static void main(String[] args) {
        int arr[] = {4,6,8,5,9,45,89,56,95,21,-4,-45};
        heapSort(arr);
        /*int[] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            //Math.random() 生成一个[0 - 800000] 之间的随机数 Math.random()默认生成0-1之间
            arr[i] = (int)(Math.random() * 800000);
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是：" + format1);

        heapSort(arr);

        Date date2 = new Date();
        String format2 = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是：" + format2);*/


    }

    public static void heapSort(int[] arr){
        int temp = 0;
        for (int i = arr.length / 2 - 1; i >= 0; --i) {
            adjustHeap(arr,i,arr.length);
        }

        for (int i = arr.length-1; i > 0; i--) {
            temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            adjustHeap(arr,0,i);
        }

        System.out.println("arr = " + Arrays.toString(arr));
    }


    /**
     * 完成 将以i对应的非叶子节点的树调整成大顶堆  8百万个数据大约2秒
     * @param arr 待调整的数组
     * @param i 表示非叶子节点在数组中的索引
     * @param length 表示对多数个元素进行调整   length是在逐渐减少的
     */
    public static void adjustHeap(int[] arr,int i,int length){
        //先取出当前元素的值 保存在临时变量中
        int temp = arr[i];

        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            //判断左子节点的值是否小于右子节点的值
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                //++k k指向右子节点
                ++k;
            }
            //右子节点大于当前节/父节点点时
            if (arr[k] > temp) {
                //把较大的值赋给当前节点
                arr[i] = arr[k];
                //!!! i 指向 k 继续循环比较
                i = k;
            }else {
                break;
            }
        }
        //当for循环结束后 已经将以i为父节点的树的最大值 放在了最顶部(局部的)
        //将temp放到调整后的位置
        arr[i] = temp;
    }
}

package cn.cherry.sort;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: DataStructures
 * @description: 插入排序
 * 插入排序法思想:
   插入排序（Insertion Sorting）的基本思想是：
    把n个待排序的元素看成为一个有序表和一个无序表，开始时有序表中只包含一个元素，无序表中包含有n-1个元素，
    排序过程中每次从无序表中取出第一个元素，把它的排序码依次与有序表元素的排序码进行比较，
    将它插入到有序表中的适当位置，使之成为新的有序表。
 * @author: Mr.Cherry
 * @create: 2019-12-23 22:27
 **/
public class insertSort {
    public static void main(String[] args) {
        int arr[] = {3,9,-1,10,20,12,99,45,6,8};
        insertSort(arr);

        /*//测试速度
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            //Math.random() 生成一个[0 - 800000] 之间的随机数 Math.random()默认生成0-1之间
            arr[i] = (int)(Math.random() * 800000);
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是：" + format1);

        insertSort(arr);
        Date date2 = new Date();
        String format2 = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是：" + format2);*/
    }

    /**
     * 插入排序 时间复杂度O(n^2)   八万个数据大约1秒
     * @param arr 要排序的数组
     */
    public static void insertSort(int[] arr){
        //待排序的数  待插入的数
        int insertValue = 0;
        //要对比的内容的下标  即arr[i] 前一个数的下标
        int insertIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            insertValue = arr[i];
            insertIndex = i - 1;
            /**
             * 给insertValue 找到插入的位置
             * 说明：
             * 1.insertIndex >= 0 保证向前对比/找插入位置时 不会出现下标越界
             * 2.insertValue < arr[insertIndex] 待插入的数 小于前面比较的数 ，表示还没有找到插入位置
             * 3.需要将 arr[insertIndex]的值后移 因为它的值比待插入的值要大
             *
             * 如果要从大到小 修改 insertValue > arr[insertIndex] 即可
             */
            while (insertIndex >= 0 && insertValue < arr[insertIndex]){
                //将比待插入数，大的值后移
                arr[insertIndex + 1] = arr[insertIndex];
                //将对比下标后移
                --insertIndex;
            }
            //当退出while循环时 说明插入的位置找到 要插入的位置是 insertIndex + 1
            //判断是否需要赋值 如果不需要则是因为当前初始的时候所在的位置就是要插入的位置
            if(insertIndex + 1 != i){
                arr[insertIndex + 1] = insertValue;
            }
            //System.out.printf("第%d轮插入排序后数组的变化%s\n",i, Arrays.toString(arr));
        }
    }
}

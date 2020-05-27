package cn.cherry.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @program: DataStructures
 * @description: 基数排序  当前代码无法做负数的排序
 * 基数排序基本思想
 *  将所有待比较数值统一为同样的数位长度，数位较短的数前面补零。然后，从最低位开始，依次进行一次排序。
 *  这样从最低位排序一直到最高位排序完成以后, 数列就变成一个有序序列。
 * @author: Mr.Cherry
 * @create: 2019-12-24 21:13
 **/
public class RadixSort {
    public static void main(String[] args) {
        /*int arr[] = {53,3,542,748,14,214};
        radixSort(arr);*/
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

        radixSort(arr);

        Date date2 = new Date();
        String format2 = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是：" + format2);
        //System.out.println(Arrays.toString(arr));
    }


    /**
     * 基数排序是使用空间换时间的经典算法
     *  八万个数据大约1秒内   8百万个数据大约1秒
     * @param arr
     */
    public static void radixSort(int[] arr){
        /**
         * 定义一个二维数组 表示10个桶 每个桶就是一个一维数组
         * 说明：
         *  1.二维数组包含10个一维数组 每个一维数组是一个桶
         *  2.为了防止在放入数据的时候出现下标越界的错误 则每个一维数组(桶)，大小定义为arr.length
         *  3.基数排序是使用空间换时间的经典算法
         */
        int[][] bucket = new int[10][arr.length];
        /**
         * 为了记录每个桶中实际存放了多少个数据 需要定义一个一维数组来记录各个桶每次放入的数据个数
         * 可以理解 bucketElementCounts[0] 就记录着bucket[0] 桶中数据的个数
         */
        int[] bucketElementCounts = new int[10];

        //1.得到数组中最大的数的位数
        //假设第一个数就是拥有最大位的数
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if(arr[i] > max){
                max = arr[i];
            }
        }
        //得到最大数是几位数
        max = (max + "").length();
        //循环的次数就是当前数组中最大位数 的位数量
        for (int i = 0,n = 1; i < max; ++i,n *= 10) {
            //针对每个元素的位数进行排序  第一次是个位、第二次是十位 以此类推....
            for (int j = 0;j < arr.length;++j){
                //取出每个元素对应位上的值
                int digitofElement = arr[j] / n % 10;
                //放入到对应的桶中
                bucket[digitofElement][bucketElementCounts[digitofElement]]
                        = arr[j];
                ++bucketElementCounts[digitofElement];
            }
            //按照这个桶的顺序(一维数组的下标依次取出数据 放入原数组)
            //表示原数组下标
            int index = 0;
            //遍历每一个桶 并将桶中的数据 放入到原数组中  k 表示当前是那个桶
            for(int k = 0;k < bucket.length;++k){
                //如果桶中 有数据 才放到原数组中
                if(bucketElementCounts[k] != 0){
                    //l 表示该桶 所有数据的下标  bucketElementCounts[k] 表示该桶中有多少数据
                    for(int l = 0;l < bucketElementCounts[k];++l){
                        //取出元素放到arr中
                        arr[index++] = bucket[k][l];
                    }
                    //放入arr中后 每次都需要将表示这个桶中数据的数量的变量归0
                    //就是 bucketElementCounts[k]
                    bucketElementCounts[k] = 0;
                }
            }
            //System.out.printf("第%d轮之后数组的变化%s\n",i+1, Arrays.toString(arr));
        }
    }
}

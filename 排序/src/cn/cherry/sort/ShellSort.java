package cn.cherry.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @program: DataStructures
 * @description: 希尔排序
 * 希尔排序法基本思想：
 *  希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，
 *  每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止
 *  计算格式  gap = arr.length/2  gap = gap / 2 .... 一直/2直到（gap == 1）被分为一组时算法便终止
 * @author: Mr.Cherry
 * @create: 2019-12-24 09:48
 **/
public class ShellSort {
    public static void main(String[] args) {
        /*int[] arr = {8,9,1,7,2,3,5,4,6,0};
        shellSort2(arr);*/

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

        shellSort2(arr);
        Date date2 = new Date();
        String format2 = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是：" + format2);
    }

    /**
     * 希尔排序 对有序序列在插入时采用 交换法  八万个数据 大约7秒
     *  将数据分为若干个组 每组内的数据进行比较 按排序规则交换 直到所有分组对比结束后进行新的分组 新分组数量 = 原分组数量 / 2
     *  如果分组内数据数量超出了2位(第二次分组之后)，两数进行比较后 如果前面还存在数值(已经比较过的)则进行回退比较(计算格式：当前的下标值 -= 分组数量)直到比较的下标置为0
     * @param arr
     * gap 分组数量（本次被分为多少组）
     * i 表示每一组 控制循环次数不超出gap
     * j 表示当前组中每一个元素的下标  表示每个待比较值的下标
     */
    public static void shellSort(int[] arr){
        //交换的辅助变量
        int temp = 0;
        int count = 0;
        for(int gap = arr.length / 2;gap > 0;gap /= 2){
            for (int i = gap; i < arr.length ; ++i) {
                for(int j = i - gap;j >= 0;j -= gap){
                    if(arr[j] > arr[j + gap]){
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
            //System.out.printf("经过%d轮遍历后数组变化%s\n",++count, Arrays.toString(arr));
        }
    }


    /**
     * 对交换法的希尔排序进行优化 -> 移位法 八万个数据 大约一秒以内   8百万个数据大约2秒
     * @param arr
     */
    public static void shellSort2(int[] arr){
        //增量gap 并逐步的缩小增量
        for(int gap = arr.length / 2;gap > 0;gap /= 2){
            //从第gap个元素 逐个对其所在的分组进行直接插入排序
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                if(arr[j] < arr[j - gap]){
                    while (j - gap >= 0 && temp < arr[j - gap]){
                        //移动
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    //当退出while后 就给temp找到了插入的位置
                    arr[j] = temp;
                }
            }
        }
    }
}

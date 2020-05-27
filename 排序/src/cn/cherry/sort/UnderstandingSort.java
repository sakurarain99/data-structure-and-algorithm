package cn.cherry.sort;

import java.util.Arrays;

/**
 * @program: DataStructures
 * @description: 排序算法的理解
 * @author: Mr.Cherry
 * @create: 2019-12-25 12:27
 **/
public class UnderstandingSort {
    public static void main(String[] args) {
        int arr[] = {51,97,1,6,75,11,-65,-7,5,-4};
        int temp[] = new int[arr.length];
        int[] result = MergeSort1.branch(arr,0,arr.length-1,temp);
        System.out.println("arr = " + Arrays.toString(result));




    }


}

/**
 * 插入排序
 */
class InsertSort{

    /**
     * 直接插入排序
     * @param arr 待排序的无序列表
     * @return 返回有序列表
     */
    public static int[] insertSort(int[] arr){
        //辅助变量
        int insertValue = 0;
        int insertIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            //待插入的值
            insertValue = arr[i];
            //表示有序列表中待比较值的下标
            insertIndex = i - 1;
            //待比较下标 大于等于0 说明前面还有值  待插入值 <= 比较值   就说明还未找到插入的位置
            while (insertIndex >= 0 && insertValue <= arr[insertIndex]){
                //让大于当前待插入值的值向后移
                arr[insertIndex +1] = arr[insertIndex];
                //跟所有值进行比较  让insertIndex--
                --insertIndex;
            }

            /**
             * while循环结束 两种可能  insertIndex < 0 || insertValue > arr[insertIndex]
             *  1.insertIndex < 0  有序列表中没有比当前值小的直接插入在最前面即可
             *  2.insertValue > arr[insertIndex]   有序列表中存在比待插入值大的元素 插入在它后面即可
             * +1 有两个原因
             *  1.insertIndex == -1  待插入值是当前有序列表中最小的值
             *  2.insertIndex下标在有序列表中索引的值比待插入值小 需要插入到它后面   并且insertIndex下标后两位的值是相同的一样的  需要覆盖
             */
            //判断 当前值需要换位的之后再 进行插入
            //insertIndex +1 != i   因为insertIndex是i-1得来的 如果有比待插入值大的值 insertIndex就会--  再加1都不会等于i
            if(insertIndex +1 != i){
                arr[insertIndex + 1] = insertValue;
            }
        }
        return arr;
    }

    /**
     * 希尔排序交换法
     * @param arr
     * @return
     */
    public static int[] shellSortExchangeMethod(int[] arr){
        //辅助变量
        int temp = 0;
        //gap 当前分的组数  每次 / 2  进行分组
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = 0; i < arr.length; i++) {
                //当前值与后面的值进行比较 交换   j -= gap 还大于0时 说明前面还有该分组的值 继续回退进行比较
                for(int j = i - gap;j >= 0;j -= gap){
                    if(arr[j] > arr[j + gap]){
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
        }
        return arr;
    }

    /**
     * 希尔排序移位法
     * @param arr
     * @return
     */
    public static int[] shellSortShiftMethod(int[] arr){
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //从前向后比较   从下标大的向下标小的进行比较
            for (int i = gap; i < arr.length; ++i) {
                int j = i;
                //获取待插入的值
                int temp = arr[j];
                if(arr[j] < arr[j - gap]){
                    while (j - gap >= 0 && temp < arr[j - gap]){
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    if(j != i){
                        arr[j] = temp;
                    }
                }


            }
        }

        return arr;
    }

}

/**
 * 选择排序
 */
class SelectSort1{

    /**
     * 简单选择排序
     * @param arr
     * @return
     */
    public static int[] SimpleSelectionSort(int[] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            int min = arr[i];
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]){
                    min = arr[j];
                    minIndex = j;
                }
            }
            if(minIndex != i){
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
        }
        return arr;
    }


}

/**
 * 交换排序
 */
class ExchangeSort{

    /**
     * 冒泡排序
     * @param arr
     * @return
     */
    public static int[] bubbleSort(int[] arr){
        //辅助变量
        int temp = 0;
        //标识变量 表示是否进行过交换
        boolean flag = false;
        for (int i = 0; i < arr.length-1; i++) {
            //每次j 循环都会把当前这次循环中的最大值移动到最后 所以j的范围可以 -i 当前是第几次遍历
            for (int j = 0; j < arr.length-1-i; j++) {
                if(arr[j] > arr[j + 1]){
                    flag =true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            if(!flag){
                break;
            }else{
                //重置flag 进行下一次判断
                flag = false;
            }
        }
        return arr;
    }

    /**
     * 快速排序
     * @param arr 待排序的数组
     * @param left 左下标 数组的最左边
     * @param right 右下标
     */
    public static int[] quickSort(int[] arr,int left,int right){
        //左下标
        int l = left;
        //右下标
        int r = right;
        //pivot 中轴值
        int pivot = arr[left];
        //临时遍历
        int temp = 0;
        while (l < r){
            while (arr[l] < pivot) {
                ++l;
            }
            while (arr[r] > pivot) {
                --r;
            }
            if(l >= r){
                break;
            }
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
        }
        if(l == r){
            ++l;
            --r;
        }

        if(r > left){
            quickSort(arr,left,r);
        }
        if(l < right){
            quickSort(arr,l,right);
        }
        return arr;
    }

}

/**
 * 归并排序
 */
class MergeSort1{
    public static int[] branch(int[] arr,int left,int right,int[] temp){
        if(left < right){
            int mid = (left +right) / 2;
            branch(arr,left,mid,temp);
            branch(arr,mid +1 ,right,temp);
            cure(arr,left,mid,right,temp);
        }
        return arr;
    }
    public static int[] cure(int[] arr,int left,int mid,int right,int[] temp){
        int i = left;
        int j = mid+1;
        int t = 0;
        while (i <= mid && j <= right){
            if(arr[i] <= arr[j]){
               temp[t] = arr[i];
               ++i;
            }else{
                temp[t] = arr[j];
                ++j;
            }
            ++t;
        }
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

        t = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            arr[tempLeft] = temp[t];
            ++t;
            ++tempLeft;
        }
        return arr;
    }



}

/**
 * 基数排序
 */
class RadixSort1{

}


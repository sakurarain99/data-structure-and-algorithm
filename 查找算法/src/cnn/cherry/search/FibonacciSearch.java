package cnn.cherry.search;

import java.util.Arrays;

/**
 * @program: DataStructures
 * @description: 斐波那契查找
 * @author: Mr.Cherry
 * @create: 2019-12-26 10:47
 **/
public class FibonacciSearch {
    private static final int maxSize = 20;
    public static void main(String[] args) {
        int arr[] = {1,8,10,89,1000,1234};
        System.out.println("index = " + fibSearch(arr,89));
    }

    /**
     * 生成返回一个长度为maxSize 的斐波那契数组
     * @return
     */
    public static int[] fib(){
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }


    public static int fibSearch(int[] arr,int key){
        //左边下标
        int low = 0;
        //右边下标
        int high = arr.length - 1;
        //表示斐波那契分割数值的下标
        int k = 0;
        //存放mid值 表示查找数值的分割值
        int mid = 0;
        //获得斐波那契数列
        int f[] = fib();
        //获取斐波那契分割数值的下标
        while (high > f[k] - 1){
            ++k;
            System.out.println("(f[k] - 1) = " + (f[k] - 1));
        }
        /**
         * 因为 f[k] 的值可能大于arr的长度 因此我们需要使用Arrays类构造一个新的数组
         * 并指向temp[] 不足的部分会使用0填充
         */
        //Arrays.copyOf(原来的数组,新数组的长度);
        int[] temp = Arrays.copyOf(arr,f[k]);
        /**
         * 扩充出来的内容使用arr数组的最后一个值填充
         * 例：
         * 新数组：temp = {1,8,10,89,1000,1234,0,0}
         * 需要修改为：temp = {1,8,10,89,1000,1234,1234,1234}
         */
        for (int i = high; i < temp.length; i++) {
            temp[i] = arr[high];
        }
        //使用while循环处理 找到我们要查找的数字
        //只要low 还没有大于high就可以继续查找
        while (low <= high){
            mid = low + f[k] - 1;
            if (key < temp[mid]){
                //满足这个条件，应该继续向数组的前面查找(左边)
                high = mid - 1;
                /**
                 * 为什么 --k
                 * 说明：
                 *  1.全部元素 = 前面的元素 + 后边的元素
                 *  2.f[k] = f[k-1] + f[k-2]
                 *  因为前面右f[k-1]个元素 所以可以继续拆除f[k-1] = f[k-2] + f[k-3]
                 *  即在 f[k-1]的前面继续查找 --k;
                 *  下次循环 mid = f[k-1-1]-1
                 */
                --k;
            }else if(key > temp[mid]){
                low = mid + 1;
                /**
                 * 为什么是 k -= 2
                 * 说明
                 *  1.全部元素 = 前面的元素 + 后边的元素
                 *  2.f[k] = f[k-1] + f[k-2]
                 *  3.因为前面右f[k-1]个元素 所以可以继续拆除f[k-1] = f[k-2] + f[k-3]
                 *  4.即在f[k-2]的前面进行查找k -= 2
                 *  5.即下次循环 mid = f[k-1-2]-1
                 */
                k -= 2;
            }else {
                //需要确定 返回的是那个下标
                if (mid <= high){
                    return mid;
                }else {
                    return high;
                }
            }
        }
        return -1;
    }
}

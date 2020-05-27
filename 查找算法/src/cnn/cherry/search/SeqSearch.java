package cnn.cherry.search;

/**
 * @program: DataStructures
 * @description: 线性查找
 * @author: Mr.Cherry
 * @create: 2019-12-25 18:42
 **/
public class SeqSearch {
    public static void main(String[] args) {
        int arr[] = {1,6546,15,-54,94,-21,0,98};
        int i = seqSerch(arr, -222);
        System.out.println("i = " + i);
    }

    /**
     * 线性查找 这里找到一个满足条件的值就返回
     * @param arr 要查找的数组
     * @param value 要查找的值
     * @return
     */
    public static int seqSerch(int[] arr,int value){
        //线性查找就是逐一对比，发现有相同值 就返回下标
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == value){
                return i;
            }
        }
        return -1;
    }
}

package cn.cherry.algorithm.binarysearchnorecursion;

/**
 * @program: DataStructures
 * @description: 二分查找算法(非递归)
 * @author: Mr.Cherry
 * @create: 2020-01-09 16:42
 **/
public class BinarySearchNoRecur {
    public static void main(String[] args) {
        int[] arr = {1,3, 8, 10, 11, 67, 100};
        int i = binarySearch(arr, 100);
        System.out.println("i = " + i);

    }

    /**
     * 二分查找非递归实现
     * @param arr 待查找的数组  arr是升序排列的
     * @param target 需要查找的数
     * @return 返回对应的下标 没有找到返回-1
     */
    public static int binarySearch(int[] arr,int target){
        int left = 0;
        int right = arr.length - 1;
        while (left <= right){
            //说明可以继续查找
            //得到中间指针
            int mid = (left + right) / 2;
            if(arr[mid] == target){
                return mid;
            }else if(arr[mid] > target){
                //向左边查找
                right = mid - 1;
            }else {
                //向右边查找
                left = mid + 1;
            }
        }
        return -1;
    }

}

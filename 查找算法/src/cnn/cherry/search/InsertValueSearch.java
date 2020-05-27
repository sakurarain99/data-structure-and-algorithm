package cnn.cherry.search;

/**
 * @program: DataStructures
 * @description: 插值查找  对mid分割值//中间值进行计算
 * 插值查找注意事项：
 *  对于数据量较大，关键字(数组的值)分布比较均匀的查找表来说，采用插值查找, 速度较快.
 *  关键字分布不均匀的情况下，该方法不一定比折半查找要好
 * @author: Mr.Cherry
 * @create: 2019-12-25 20:27
 **/
public class InsertValueSearch {
    public static void main(String[] args) {
        int arr[] = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i+1;
        }
        int i = insertValueSearch(arr, 0, arr.length - 1, 26);
        System.out.println("i = " + i);
    }

    /**
     *
     * @param arr 要查找的数组
     * @param left 左下标
     * @param right 右下标
     * @param findVal 要查找的值
     * @return
     */
    public static int insertValueSearch(int[] arr,int left,int right,int findVal){
        System.out.println("查找次数 -");
        //注意：findVal < arr[0] 和 findVal > arr[arr.length - 1] 必须需要
        //否则我们得到的mid可能越界
        if(left > right || findVal < arr[0] || findVal > arr[arr.length - 1]){
            return -1;
        }
        //求出mid
        //插值算法的计算公式
        //int mid = left + (right – left) * (findVal – arr[left]) / (arr[right] – arr[left])
        //int mid = 左下标 + (右下标 - 左下标) * (要查找的值 - 查找的数组[左下标]) / (查找的数组[右下标] - 查找的数组[左下标])
        int mid = left + (right - left) * (findVal - arr[left])
                / (arr[right] - arr[left]);
        int midVal = arr[mid];
        if(findVal > midVal){
            //当分割值 大于要找的值时 向左递归
            // 右下标是当前mid分割下标-1 因为它已经确定不是可以省略
            return insertValueSearch(arr,mid + 1,right,findVal);
        }else if(findVal < midVal){
            //当分割值 小于要找的值时 向右递归
            // 左下标是当前mid分割下标+1 因为它已经确定不是可以省略
            return insertValueSearch(arr,left,mid - 1,findVal);
        }else {
            return mid;
        }
    }
}

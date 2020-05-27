package cn.cherry.algorithm.dynamic;

import java.util.Arrays;

/**
 * @program: DataStructures
 * @description: 动态规划算法  解决背包问题
 * @author: Mr.Cherry
 * @create: 2020-01-12 19:15
 **/
public class KnapsackProblem {
    public static void main(String[] args) {
        //物品的重量
        int[] w = {1,4,3};
        //物品的价值  这里val[i] 就是前面的v[i]
        int[] val = {1500,3000,2000};
        //背包的容量
        int m = 4;
        //物品的个数
        int n = val.length;

        //创建二维数组
        //v[i][j] 表示在前i个物品中能够装入容量为j的背包中的最大价值
        int[][] v = new int[n+1][m+1];
        //用于记录放入商品的情况
        int[][] path = new int[n+1][m+1];
        //初始化第一行和第一列
        for (int i = 0; i < v.length; i++) {
            v[i][0] = 0;
        }
        for (int i = 0;    i < v[0].length; i++) {
            v[0][i] = 0;
        }

        //根据前面得到的公式进行动态规划处理
        for (int i = 1; i < v.length; i++) {
            //不处理第一行 i从1开始
            for (int j = 1; j < v[0].length; j++) {
                //不处理第一列 j从1开始
                //因为程序i是从1开始 所有需要-1
                if(w[i-1] > j){
                    v[i][j] = v[i-1][j];
                }else {
                    //公式调整因为i是从1开始 需要-1
                    //v[i][j] = Math.max(v[i-1][j],val[i-1] + v[i-1][j-w[i-1]]);
                    //为了记录商品存放到背包的情况 不能直接使用上面的公式 需要使用if-else体现公式
                    if(v[i-1][j] < val[i-1] + v[i-1][j-w[i-1]]){
                        v[i][j] = val[i-1] + v[i-1][j-w[i-1]];
                        //把当前的情况记录到path
                        path[i][j] = 1;
                    }else {
                        v[i][j] = v[i-1][j];
                    }
                }
            }
        }



        for (int[] ints : v) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println("---------------------");
        for (int[] ints : path) {
            System.out.println(Arrays.toString(ints));
        }
        //行的最大下标
        int i = path.length - 1;
        //列的最大下标
        int j = path[0].length - 1;
        //逆向遍历  从path最后开始找
        while (i > 0 && j > 0){
            if (path[i][j] == 1){
                System.out.printf("第%d个商品放入到背包\n",i);
                j -= w[i - 1];
            }
            i--;
        }


    }
}

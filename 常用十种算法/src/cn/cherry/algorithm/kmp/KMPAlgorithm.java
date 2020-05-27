package cn.cherry.algorithm.kmp;

import com.sun.org.apache.regexp.internal.RE;

import java.util.Arrays;

/**
 * @program: DataStructures
 * @description: kmp算法
 * @author: Mr.Cherry
 * @create: 2020-01-13 17:16
 **/
public class KMPAlgorithm {
    public static void main(String[] args) {
        String str1= "BBC ABCDABAABCDABCDABDE";
        String str2="ABCDABD";

        int[] next = kmpNext(str2);
        System.out.println("next = " + Arrays.toString(next));

        int index = kmpSearch(str1, str2, next);
        System.out.println("index = " + index);
    }

    /**
     * kmp搜索方法
     * @param str1 源字符串
     * @param str2 要搜索的字符串/子串
     * @param next 子串的部分匹配表
     * @return 第一次出现的位置(下标)
     */
    public static int kmpSearch(String str1,String str2,int[] next){
        for (int i = 0,j = 0; i < str1.length(); i++) {

            //需要处理str1.charAt(i) != str2.charAt(j) 去调整j的大小
            //kmp算法核心点
            while (j > 0 && str1.charAt(i) != str2.charAt(j)){
                j = next[j-1];
            }

            if (str1.charAt(i) == str2.charAt(j)){
                j++;
            }
            if (j == str2.length()){
                //如果最后匹配到了 y执行y++ 而i并没有机会自增+1 所以需要+1
                return i - j + 1;
            }
        }
        return -1;
    }


    /**
     * 获得到一个字符串(子串)的部分匹配值表
     * @param dest 要生成的字符串
     * @return 部分匹配值 数组表示
     */
    public static int[] kmpNext(String dest){
        //创建一个next 数组保存部分匹配值
        int[] next = new int[dest.length()];
        //如果字符串长度为1 部分匹配值就是0
        next[0] = 0;
        for (int i = 1,j = 0; i < dest.length(); i++) {
            //当dest.charAt(i) != dest.charAt(j) 时需要从next[j-1]获取新的j
            //直到发现有dest.charAt(i) == dest.charAt(j)成立才退出
            //kmp算法的核心点
            while (j > 0 && dest.charAt(i) != dest.charAt(j)){
                j = next[j - 1];
            }
            //当dest.charAt(i) == dest.charAt(j) 满足时部分匹配值就+1
            if(dest.charAt(i) == dest.charAt(j)){
                ++j;
            }
            next[i] = j;
        }
        return next;
    }
}

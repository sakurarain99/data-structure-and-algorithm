package cn.cherry.algorithm.kmp;

/**
 * @program: DataStructures
 * @description: 暴力匹配
 * @author: Mr.Cherry
 * @create: 2020-01-12 20:05
 **/
public class ViolenceMatch {
    public static void main(String[] args) {
        String str1= "硅硅谷 尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
        String str2= "尚硅谷你尚硅你";
        int i = violenceMatch(str1, str2);
        System.out.println("i = " + i);
    }

    /**暴力匹配算法实现*/
    public static int violenceMatch(String str1,String str2){
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        //i索引指向s1
        int i = 0;
        //j索引指向s2
        int j = 0;
        //保证匹配时 不越界
        while (i < s1.length && j < s2.length){
            if (s1[i] == s2[j]){
                i++;
                j++;
            }else {
                //如果失配（即str1[i]! = str2[j]），令i = i - (j - 1)，j = 0。
                // 相当于每次匹配失败时，i 回溯，j 被置为0。
                i = i - (j-1);
                j = 0;
            }
        }
        //判断是否匹配成功
        if(j == s2.length){
            return i - j;
        }else {
            return -1;
        }
    }
}

package cn.cherry.algorithm.dac;

/**
 * @program: DataStructures
 * @description: 分治算法  实例 汉诺塔
 * @author: Mr.Cherry
 * @create: 2020-01-09 17:25
 **/
public class Hanoitower {
    public static void main(String[] args) {
        hanoiTower(3,'a','b','c');
     }

    /**
     * 汉诺塔的移动方法 使用分治算法
     * @param num 盘数
     * @param a 柱子名称
     * @param b ~
     * @param c ~
     */
    public static void hanoiTower(int num,char a,char b,char c){
        //如果只有一个盘
        if (num == 1){
            System.out.printf("第%d个盘从 %s -> %s\n",num,a,c);
        }else {
            //如果我们有 n >= 2 情况，我们总是可以看做是两个盘 1.最下边的一个盘 2.上面的所有盘
            //1.先把 最上面的所有盘 A->B  需要借助c
            hanoiTower(num - 1,a,c,b);
            //2.把最下边的盘 A->C
            System.out.printf("第%d个盘从 %s -> %s\n",num,a,c);
            //3.把B塔的所有盘 从 B->C    需要借助a
            hanoiTower(num - 1,b,a,c);
        }
    }
}

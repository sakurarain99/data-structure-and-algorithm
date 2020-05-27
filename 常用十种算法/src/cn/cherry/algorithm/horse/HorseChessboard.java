package cn.cherry.algorithm.horse;

import org.omg.PortableInterceptor.INACTIVE;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @program: DataStructures
 * @description: 马踏棋盘/骑士周游
    思路：
        1.  创建棋盘 chessBoard , 是一个二维数组
        2.  将当前位置设置为已经访问，然后根据当前位置，计算马儿还能走哪些位置，并放入到一个集合中(ArrayList), 最多有8个位置， 每走一步，就使用step+1
        3. 遍历ArrayList中存放的所有位置，看看哪个可以走通 , 如果走通，就继续，走不通，就回溯.
        4.  判断马儿是否完成了任务，使用   step 和应该走的步数比较 ， 如果没有达到数量，则表示没有完成任务，将整个棋盘置0
 * @author: Mr.Cherry
 * @create: 2020-01-17 17:29
 **/
public class HorseChessboard {
    /**棋盘的列数*/
    private static int X;
    /**棋盘的行数*/
    private static int Y;
    /**创建一个数组标记棋盘的各个位置是否被访问过*/
    private static boolean visited[];
    /**使用一个属性 标记棋盘的所有位置是否都被访问  如果为true 表示成功*/
    private static boolean finished;

    public static void main(String[] args) {
        X = 8;
        Y = 8;
        //马初始位置的行 从1开始编号
        int row = 1;
        //马初始位置的列 从1开始编号
        int column = 1;
        //创建棋盘
        int[][] chessboard = new int[X][Y];
        //初始值默认都是false
        visited = new boolean[X*Y];
        //测试耗时
        long start = System.currentTimeMillis();
        traversalChessborad(chessboard,row-1,column-1,0);
        long end = System.currentTimeMillis();

        System.out.printf("共耗时：%d 毫秒\n",(end-start));

        //输出棋盘的最后情况
        for (int[] rows : chessboard) {
            for (int i : rows) {
                System.out.print(i + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 骑士周游算法  贪心算法进行优化
     * @param chessboard 棋盘
     * @param row 马当前位置的行  从0开始
     * @param column 马当前位置的列  从0开始
     * @param step 是第几步 初始位置是第0步
     */
    public static void traversalChessborad(int[][] chessboard,int row,int column,int step){
        chessboard[row][column] = step;
        //row = 4 X = 8 column = 4  4*8+4 = 36  从0开始数
        //标记该位置已经访问
        visited[row * X + column] = true;
        List<Point> ps = next(new Point(column, row));
        //对ps进行排序 排序规则就是对ps的所有Point对象的下一步的位置的数目 进行非递减排序
        //贪心算法核心方法(选择最优的一条路线，先尝试ps中Point对象的下一步的位置数目较小的一个点(Point) 进行搜索/回溯)
        //优先尝试选择方案数量较小的Point/下一步可选点较少的一个Point
        sort(ps);
        //遍历ps
        while (!ps.isEmpty()){
            //取出下一个可以走的位置    必定是当前可走点并且下一步可选点较少的一个Point
            Point p = ps.remove(0);
            //判断该点是否已经访问过
            if(!visited[p.y * X + p.x]){
                //说明还没有访问过
                traversalChessborad(chessboard,p.y,p.x,step+1);
            }
        }
        /*
            判断马是否完成了任务 使用step和应该走的步数比较
            如果没有达到数量 则表示没有完成任务将整个棋盘置0
            step < X * Y  成立的情况有两中
            1.棋盘到目前位置 仍然没有走完
            2.棋盘处于一个回溯过程
        */
        if(step < X * Y -1 && !finished){
            //回溯重置路线
            chessboard[row][column] = 0;
            visited[row * X + column] = false;
        }else {
            finished = true;
        }

    }


    /**
     * 根据当前位置(Point对象) 计算马还能走那些位置(Point) 并放入到一个集合中 最多有8个位置
     * Point  代表一个点
     * @param curPoint  当前点
     * @return
     */
    public static List<Point> next(Point curPoint){
        //创建list
        List<Point> ps = new ArrayList<>();
        Point p1 = new Point();
        /*
            curPoint.x - 2  当前点向左移动两位 是否>=0
            curPoint.y - 1  当前点向上移动一位 是否>=0
            x 代表列  y代表行  如果可以走则记录到list中
        */
        //判断马是否可以走5这个位置
        if((p1.x = curPoint.x - 2) >= 0 &&
                (p1.y = curPoint.y - 1) >= 0){
            ps.add(new Point(p1));
        }
        //判断马是否可以走6这个位置
        if((p1.x = curPoint.x - 1) >= 0 &&
                (p1.y = curPoint.y - 2) >= 0){
            ps.add(new Point(p1));
        }
        //判断马是否可以走7这个位置
        if((p1.x = curPoint.x + 1) < X &&
                (p1.y = curPoint.y - 2) >= 0){
            ps.add(new Point(p1));
        }
        //判断马是否可以走0这个位置
        if((p1.x = curPoint.x + 2) < X &&
                (p1.y = curPoint.y - 1) >= 0){
            ps.add(new Point(p1));
        }
        //判断马是否可以走1这个位置
        if((p1.x = curPoint.x + 2) < X &&
                (p1.y = curPoint.y + 1) < Y){
            ps.add(new Point(p1));
        }
        //判断马是否可以走2这个位置
        if((p1.x = curPoint.x + 1) < X &&
                (p1.y = curPoint.y + 2) < Y){
            ps.add(new Point(p1));
        }
        //判断马是否可以走3这个位置
        if((p1.x = curPoint.x - 1) >= 0 &&
                (p1.y = curPoint.y + 2) < Y){
            ps.add(new Point(p1));
        }
        //判断马是否可以走4这个位置
        if((p1.x = curPoint.x - 2) >= 0 &&
                (p1.y = curPoint.y + 1) < Y){
            ps.add(new Point(p1));
        }
        return ps;
    }


    /**
     * 根据ps集合中所有步(Point)的下一步的数量  进行非递减排序
     * 9,7,6,5,3,2,1    //递减排序     从大到小
     * 1,2,3,4,5,6,10     //递增排序   从小到大
     * 1,2,2,2,3,3,4,5,6    //非递减  从小到大可重复/允许有重复的值
     * 9,7,6,6,6,5,5,3,2,1 //非递增   从大到小可重复
     * @param ps 下一步所有Point的集合
     */
    public static void sort(List<Point> ps){
        ps.sort(new Comparator<Point>() {
            /***
             * ps中所有的元素都会赋给o1，o2进行比较
             * @param o1 ps中的元素
             * @param o2 ps中的元素
             * @return o1 < o2 返回 负整数  两数相等返回0  o1 > o2 返回正整数
             */
            @Override
            public int compare(Point o1, Point o2) {
                //获取到o1的下一步的所有位置个数
                int count1 = next(o1).size();
                //获取到o1的下一步的所有位置个数
                int count2 = next(o2).size();
                if(count1 < count2){
                    return -1;
                }else if (count1 == count2){
                    return 0;
                }else {
                    return 1;
                }
            }
        });

    }


}


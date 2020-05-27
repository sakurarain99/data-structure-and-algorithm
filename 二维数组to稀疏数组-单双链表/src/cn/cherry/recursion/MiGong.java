package cn.cherry.recursion;

/**
 * @program: DataStructures
 * @description: 使用递归回溯 完成小球找路
 * @author: Mr.Cherry
 * @create: 2019-12-23 12:39
 **/
public class MiGong {
    public static void main(String[] args) {
        //先创建一个二维数组 模拟迷宫
        //地图
        int[][] map = new int[8][7];
        //使用1表示墙
        //上下全部置为1
        for(int i = 0;i < 7;++i){
            map[0][i] = 1;
            map[7][i] = 1;
        }
        //左右全部置为1
        for(int i = 0;i < 8;++i){
            map[i][0] = 1;
            map[i][6] = 1;
        }
        //设置挡板
        map[3][1] = 1;
        map[3][2] = 1;
        //输出地图
        System.out.println("地图的情况：");
        for (int i = 0;i < 8;++i){
            for(int j = 0;j < 7;++j){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        setWay(map,1,1);

        System.out.println("递归后小球标注后 地图的情况：");
        for (int i = 0;i < 8;++i){
            for(int j = 0;j < 7;++j){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     *说明
     * 1.map 表示地图
     * 2.i,j 表示从地图的那个位置开始出发(1,1)
     * 3.如果小球到map[6][5] 位置 表示通路找到
     * 4.约定： 当map[i][j] 为 0表示该节点没有走过 当为1表示墙 2表示通路可以走 3表示该点已经走过但是走不通
     * 5.在走迷宫时 需要确定一个策略(方法) 下->右->上->左  如果该点在不通再回溯
     * @param map 表示地图 共享的 因为是一个引用类型
     * @param i 从哪个位置开始找
     * @param j
     * @return 如果找到通路 就返回true 否则返回false
     */
    public static boolean setWay(int[][] map,int i,int j){
        if(map[6][5] == 2){
            //表示已经通路已经找到
            return true;
        }else {
            if(map[i][j] == 0){
                //如果当前这个点还没有走过 按照策略 下->右->上->左 走
                //先假定这个点是可以走通的
                map[i][j] = 2;
                if(setWay(map,i+1,j)){
                    //向下走 判断
                    return true;
                }else if(setWay(map,i,j+1)){
                    //向右走 判断
                    return true;
                }else if(setWay(map,i-1,j)){
                    //向上走 判断
                    return true;
                }else if(setWay(map,i,j-1)){
                    //向左走 判断
                    return true;
                }else{
                    //当下->右->上->左 都无法走通时 则置为死路 3
                    map[i][j] = 3;
                    return false;
                }
            }else{
                //如果map[i][j] != 0 可能是 1 2 3
                return false;
            }
        }
    }
}

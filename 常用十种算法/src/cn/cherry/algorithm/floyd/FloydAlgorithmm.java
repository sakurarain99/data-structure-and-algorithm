package cn.cherry.algorithm.floyd;

import java.util.Arrays;

/**
 * @program: DataStructures
 * @description: 弗洛伊德算法  最佳应用-最短路径 计算出各村庄到 其它各村庄的最短距离
 * @author: Mr.Cherry
 * @create: 2020-01-17 16:01
 **/
public class FloydAlgorithmm {
    public static void main(String[] args) {
        char[] vertexs = {'A','B','C','D','E','F','G'};
        //邻接矩阵
        int[][] matrix = new int[vertexs.length][vertexs.length];
        //表示不可连接
        final int N = 65535;
        matrix[0] = new int[] { 0, 5, 7, N, N, N, 2 };
        matrix[1] = new int[] { 5, 0, N, 9, N, N, 3 };
        matrix[2] = new int[] { 7, N, 0, N, 8, N, N };
        matrix[3] = new int[] { N, 9, N, 0, N, 4, N };
        matrix[4] = new int[] { N, N, 8, N, 0, 5, 4 };
        matrix[5] = new int[] { N, N, N, 4, 5, 0, 6 };
        matrix[6] = new int[] { 2, 3, N, N, 4, 6, 0 };

        //创建图对象
        Graph graph = new Graph(vertexs.length, matrix, vertexs);
        graph.floyd();
        graph.show();
    }
}


class Graph{
    /**存放顶点数组*/
    private char[] vertex;
    /**保存从各个顶点出发到其它顶点的距离 最后的结果 也是保留在该数组*/
    private int[][] dis;
    /**保存到达目标顶点的前驱节点*/
    private int[][] pre;

    /**
     * 构造器
     * @param length 大小(顶点个数)
     * @param matrix 邻接矩阵
     * @param vertex 顶点数组
     */
    public Graph(int length,int[][] matrix,char[] vertex) {
        this.vertex = vertex;
        this.dis = matrix;
        this.pre = new int[length][length];
        //对pre进行初始化 存放的是前驱节点的下标
        for (int i = 0; i < length; i++) {
            Arrays.fill(pre[i],i);
        }
    }

    /**
     * 显示pre和dis数组
     */
    public void show(){
        char[] vertexs = {'A','B','C','D','E','F','G'};
        System.out.println("dis ---");
        for (int i = 0; i < dis.length; i++) {
            for (int j = 0; j < pre.length; j++) {
                System.out.print(vertex[pre[i][j]] + " ");
            }
            System.out.println();
            for (int j = 0; j < dis[0].length; j++) {
                String str ="("+vertexs[i] +"->"+vertexs[j] +
                        "：" + dis[i][j] + ") ";
                System.out.printf("%15s",str);
            }
            System.out.println();
            System.out.println();
        }
    }

    /**
     * 弗洛伊德算法
     */
    public void floyd(){
        //变量保存距离
        int len = 0;
        //对中间顶点的遍历
        for (int k = 0; k < dis.length; k++) {
            //从i顶点开始出发
            for (int i = 0; i < dis.length; i++) {
                //终点
                for (int j = 0; j < dis.length; j++) {
                    //求出从i顶点出发 经过k中间顶点到达j顶点的距离
                    len = dis[i][k] + dis[k][j];
                    if(len < dis[i][j]){
                        //更新距离
                        dis[i][j] = len;
                        //更新前驱顶点
                        //pre[i][j] = pre[k][j];  设置前驱节点为 连接的上一个节点  (可能是当前的中间节点/也可能是连接当前中间节点与当前终点的中间节点的下标)
                        //pre[i][j] = k;  设置前驱节点为  当前的中间节点  各取所需
                        pre[i][j] = pre[k][j];
                    }
                }
            }
        }
    }
}
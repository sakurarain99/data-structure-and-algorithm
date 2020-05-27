package cn.cherry.algorithm.kruskal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: DataStructures
 * @description: 克鲁斯卡尔算法  公交车问题
 * @author: Mr.Cherry
 * @create: 2020-01-15 14:14
 **/
public class KruskalCase {

    /**边的个数*/
    private int edgeNum;
    /**顶点数组*/
    private char[] vertexs;
    /**邻接矩阵*/
    private int[][] matrix;
    /**使用 INF 表示两个顶点不能连通*/
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        char[] vertexs = {'A','B','C','D','E','F','G'};
        /**
         * 0 代表同一个点/自己和自己连接
         * INF 代表不可连通
         */
        int matrix[][] = {
                /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ {   0,  12, INF, INF, INF,  16,  14},
                /*B*/ {  12,   0,  10, INF, INF,   7, INF},
                /*C*/ { INF,  10,   0,   3,   5,   6, INF},
                /*D*/ { INF, INF,   3,   0,   4, INF, INF},
                /*E*/ { INF, INF,   5,   4,   0,   2,   8},
                /*F*/ {  16,   7,   6, INF,   2,   0,   9},
                /*G*/ {  14, INF, INF, INF,   8,   9,   0}};
        //创建KruskalCase 对象实例
        KruskalCase kruskalCase = new KruskalCase(vertexs, matrix);
        kruskalCase.print();
        /*EData[] edges = kruskalCase.getEdges();
        kruskalCase.sortEdges(edges);*/
        kruskalCase.kruskal();


    }


    public KruskalCase(char[] vertexs, int[][] matrix) {
        //初始化顶点数和边的个数
        int vlen = vertexs.length;

        //初始化顶点  深度克隆
        this.vertexs = new char[vlen];
        for (int i = 0; i < vertexs.length; i++) {
            this.vertexs[i] = vertexs[i];
        }
        
        //初始化边/邻接矩阵   深度克隆
        this.matrix = new int[vlen][vlen];
        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
        
        //统计边
        for (int i = 0; i < vlen; i++) {
            for (int j = i+1; j < vlen; j++) {
                if(this.matrix[i][j] != INF && this.matrix[i][j] != 0){
                    this.edgeNum++;
                }
            }
        }
    }



    public void kruskal(){
        //表示最后结果数组的索引
        int index  = 0;
        //用于保存 "已有最小生成树" 中每个顶点在最小生成树中的终点
        int[] ends = new int[edgeNum];
        ///创建结果数组 保存最后的最小生成树
        EData[] rets = new EData[edgeNum];

        //获取图中所有的边的集合 共有12边
        EData[] edges = getEdges();

        //按照边的权值大小进行排序(从小到大)
        sortEdges(edges);

        //遍历edges数组 将边添加到最小生成树中时 判断准备加入的边是否形成了回路 如果没有就加入到rets 否则不能加入
        for (int i = 0; i < edgeNum; i++) {
            //获取到第i条边的第一个顶点(起点)  下标
            int p1 = getPosition(edges[i].start);
            //获取到第i条边的第二个顶点  下标
            int p2 = getPosition(edges[i].end);

            //获取p1这个顶点在已有最小生成树中的终点
            int m = getEnd(ends,p1);
            //获取p2这个顶点在已有最小生成树中的终点
            int n = getEnd(ends,p2);

            System.out.println("n = " + n);
            //判断是否构成回路
            if(m != n){
                //设置m 在"已有最小生成树"中的终点
                ends[m] = n;
                //有一条边加入到rets数组
                rets[index++] = edges[i];
            }
        }

        //统计并打印"最小生成树" 输出rets
        for (EData ret : rets) {
            if(ret != null){
                System.out.println(ret);
            }
        }
    }


    /**
     * 打印邻接矩阵
     */
    public void print(){
        System.out.println("邻接矩阵为：");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                //%13d  13表示占位  如果这个值不够13则空着
                System.out.printf("%13d | ",matrix[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * 对边进行排序处理  冒泡排序
     * @param edges 边的集合
     */
    private void sortEdges(EData[] edges){
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length-1-i; j++) {
                if (edges[j].weight > edges[j+1].weight){
                    EData temp = edges[j];
                    edges[j] = edges[j+1];
                    edges[j+1] = temp;
                }
            }
        }
    }


    /**
     * 返回一个顶点的下标
     * @param ch ch 顶点的值 比如 'A'->0 'B'->1 ...
     * @return 返回ch顶点对应的下标 如果找不到 返回-1
     */
    private int getPosition(char ch){
        for (int i = 0; i < vertexs.length; i++) {
            if (vertexs[i] == ch){
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取图中边 放到EData[] 数组中 后面需要遍历该数组
     * 通过matrix 邻接矩阵来获取
     * EData[] 形式 [['A','B',12]...]
     * @return
     */
    private EData[] getEdges(){
        int index = 0;
        EData[] result = new EData[edgeNum];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i+1; j < matrix[0].length; j++) {
                if (matrix[i][j] != INF && matrix[i][j] != 0){
                    EData data = new EData(vertexs[i],vertexs[j],matrix[i][j]);
                    result[index] = data;
                    ++index;
                }
            }
        }
        return result;
    }


    /**
     * 获取下标为i的顶点的终点 用于后面判断两个顶点的终点是否相同
     * @param ends 数组就是记录了各个顶点对应的终点是哪个 ends数组是在遍历过程中逐步形成的
     * @param i 表示传入的顶点对应的下标
     * @return 返回的是 下标为i的这个顶点对应的终点的下标
     */
    private int getEnd(int[] ends,int i){
        while (ends[i] != 0){
            i = ends[i];
        }
        return i;
    }

}

/**
 * 边对象
 */
class EData{
    /**边的一个点*/
    char start;
    /**边的另一个点*/
    char end;
    /**边的权值*/
    int weight;

    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "EData{" +
                " <" + start +
                "," + end +
                "> = " + weight +
                " }";
    }
}

package cn.cherry.algorithm.prim;

import java.util.Arrays;

/**
 * @program: DataStructures
 * @description: 普利姆算法  解决 修路问题
 * @author: Mr.Cherry
 * @create: 2020-01-14 21:44
 **/
public class PrimAlgorithm {
    public static void main(String[] args) {
        char[] data = new char[]{'A','B','C','D','E','F','G'};
        int verxs = data.length;
        //邻接矩阵的关系使用二维数组表示  10000(较大的值)表示两个点不连通
        int[][] weight=new int[][]{
                {10000,5,7,10000,10000,10000,2},
                {5,10000,10000,9,10000,10000,3},
                {7,10000,10000,10000,8,10000,10000},
                {10000,9,10000,10000,10000,4,10000},
                {10000,10000,8,10000,10000,5,4},
                {10000,10000,10000,4,5,10000,6},
                {2,3,10000,10000,4,6,10000},};
        //创建MGraph对象
        MGraph graph = new MGraph(verxs);
        //创建MinTree对象
        MinTree minTree = new MinTree();
        //生成图
        minTree.createGraph(graph,verxs,data,weight);

        //获取最小生成树
        minTree.prim(graph,2);
    }
}

/**创建最小生成树 -> 村庄的图 */
class MinTree{

    /***
     * 创建图的邻接矩阵
     * @param graph 图对象
     * @param verxs 图对应的顶点个数/节点个数
     * @param data 图的各个顶点的值
     * @param weight 图的邻接矩阵
     */
    public void createGraph(MGraph graph,int verxs,char data[],int[][] weight){
        int i,j;
        //顶点
        for (i = 0;  i < verxs; i++) {
            graph.data[i] = data[i];
            for (j = 0; j < verxs; j++) {
                //连接边/邻接矩阵
                graph.weight[i][j] = weight[i][j];
            }
        }
    }

    /**
     * 显示邻接矩阵
     * @param graph 图对象
     */
    public void showGraph(MGraph graph){
        for (int[] link : graph.weight) {
            System.out.println(Arrays.toString(link));
        }
    }


    /**
     * 编写prim算法 得到最小生成树
     * @param graph 图
     * @param v 表示从图的第几个顶点开始生成 'A'->0 ...
     */
    public void prim(MGraph graph,int v){
        //visited[] 标记节点(顶点)是否被访问过  0未访问  1已访问
        int visited[] = new int[graph.verxs];
        //把当前这个节点标记为已访问
        visited[v] = 1;
        //h1和h2 记录两个顶点的下标
        int h1 = -1;
        int h2 = -1;
        //将minWeight 初始成一个大数 后面遍历过程中会被替换
        int minWeight = 10000;
        //因为有graph.verxs顶点 普利姆算法结束后 有graph.verxs - 1条边  所以从一开始
        for (int i = 1; i < graph.verxs; i++) {
            //这个是确定每一次生成的子图 和那个节点的距离最近
            //j节点表示被访问过的节点  参照节点
            for (int j = 0; j < graph.verxs; j++) {
                //k表示j节点的相邻节点（还未被访问的）
                for (int k = 0; k < graph.verxs; k++) {
                    if(visited[j] == 0){
                        break;
                    }
                    //j表示的节点已被访问 && k表示的节点未被访问
                    //&& 当前以j节点为参照节点的未被访问过的邻接节点的权值 < minWeight 时
                    if (visited[j] == 1 && visited[k] == 0
                            && graph.weight[j][k] < minWeight){
                        //替换minWeight(寻找已经访问过的节点和未访问过的节点之间权值最小的边)
                        minWeight = graph.weight[j][k];
                        h1 = j;
                        h2 = k;
                    }
                }
            }
            //找到一条最小的边
            System.out.printf("边 < %s , %s > 权值：%d\n",
                    graph.data[h1],graph.data[h2],minWeight);
            //将当前这个节点标记为已经访问
            visited[h2] = 1;
            //重置辅助变量
            minWeight = 10000;
        }

    }

}

class MGraph{
    /**表示图的节点个数*/
    int verxs;
    /**存放节点数据*/
    char[] data;
    /**存放边 就是邻接矩阵*/
    int[][] weight;

    public MGraph(int verxs){
        this.verxs = verxs;
        data = new char[verxs];
        weight = new int[verxs][verxs];
    }



}

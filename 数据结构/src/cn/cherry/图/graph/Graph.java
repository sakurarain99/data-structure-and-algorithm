package cn.cherry.图.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: DataStructures
 * @description: 图的使用   深度优先(纵向遍历)/广度优先(横向遍历)
 * @author: Mr.Cherry
 * @create: 2020-01-08 15:41
 **/
public class Graph {

    /**存储顶点集合*/
    private List<String> vertexList;
    /**存储图对应的邻接矩阵*/
    private int[][] edges;
    /**表示边的数目*/
    private int numOfEdges;
    /**定义一个数组boolean[] 记录某个节点是否被访问 */
    private boolean[] isVisited;

    public static void main(String[] args) {
        //String Vertexs[] = {"A","B","C","D","E"};
        String Vertexs[] = {"1","2","3","4","5","6","7","8"};
        //节点的个数
        int n = Vertexs.length;
        Graph graph = new Graph(n);
        //循环添加顶点
        for (String vertex : Vertexs) {
            graph.insertVertex(vertex);
        }
        //添加边
        /*// A-B A-C B-V B-D B-E
        graph.insertEdge(0,1,1);
        graph.insertEdge(0,2,1);
        graph.insertEdge(1,2,1);
        graph.insertEdge(1,3,1);
        graph.insertEdge(1,4,1);*/

        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);
        graph.insertEdge(3, 7, 1);
        graph.insertEdge(4, 7, 1);
        graph.insertEdge(2, 5, 1);
        graph.insertEdge(2, 6, 1);
        graph.insertEdge(5, 6, 1);


        //显示邻接矩阵
        graph.showGraph();
        System.out.println("深度优先 - ");
        graph.dfs();
        System.out.println();
        System.out.println("广度优先 - ");
        graph.bfs();

    }

    public Graph(int n) {
        //初始化矩阵和vertexList
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
        isVisited = new boolean[n];
        numOfEdges = 0;
    }

    /**
     * 返回第一个邻接节点的下标 w
     * @param index 当前节点的下标
     * @return 如果存在返回对应的下标 否则返回-1
     */
    public int getFirstNeighbor(int index){
        for (int j = 0; j < vertexList.size(); j++) {
            if (edges[index][j] > 0){
                return j;
            }
        }
        return -1;
    }

    /**
     * 根据前一个邻接节点的下标来获取下一个邻接节点
     * @param v1 当前节点 (下标)
     * @param v2 前一个邻接节点 (下标)
     * @return 如果存在返回对应的下标 否则返回-1
     */
    public int getNextNeighbor(int v1,int v2){
        for (int j = v2+1; j < vertexList.size(); j++) {
            if(edges[v1][j] > 0){
                return j;
            }
        }
        return -1;
    }

    /**
     * 深度优先遍历算法
     * @param isVisited 标识是否已被访问的数组
     * @param i 当前节点的下标
     */
    private void dfs(boolean[] isVisited,int i){
        //输出当前节点
        System.out.print(getValueByIndex(i) + " -> ");
        //将节点设置为已经被访问
        isVisited[i] = true;
        //查找节点i的第一个邻接节点w  (w是下标)
        int w = getFirstNeighbor(i);
        while (w != -1){
            //存在邻接节点
            //判断是否已经被访问过
            if(!isVisited[w]){
                dfs(isVisited,w);
            }
            //如果w节点已经被访问过
            w = getNextNeighbor(i,w);
        }
    }

    /**
     * 对dfs 进行重载 遍历所有节点 并进行dfs
     */
    public void dfs(){
        isVisited = new boolean[getNumOfVertex()];
        //遍历所有的节点 进行dfs[回溯]
        for (int i = 0; i < getNumOfVertex(); i++) {
            if(!isVisited[i]){
                dfs(isVisited,i);
            }
        }
    }

    /**
     * 对一个节点进行广度优先遍历
     * @param isVisited 标识是否已被访问的数组
     * @param i 当前节点的下标
     */
    private void bfs(boolean[] isVisited,int i){
        //表示队列的头节点对应下标
        int u;
        //邻接节点w
        int w;
        //队列 记录节点访问的顺序
        LinkedList<Integer> queue = new LinkedList<>();
        //访问节点 输出节点信息
        System.out.print(getValueByIndex(i) + " -> ");
        //标记已访问
        isVisited[i] = true;
        //将节点加入队列
        queue.addLast(i);
        //队列不为空进行循环
        while (!queue.isEmpty()){
            //1.取出队列头的节点
            u = queue.removeFirst();
            //2.得到u的第一个邻接节点的下标 w
            w = getFirstNeighbor(u);
            while (w != -1){
                //说明已经找到了第一个邻接节点
                //节点未被访问过时
                if(!isVisited[w]){
                    //访问节点 输出节点信息
                    System.out.print(getValueByIndex(w) + " -> ");
                    //标记已访问
                    isVisited[w] = true;
                    //将节点加入队列
                    queue.addLast(w);
                }
                //根据当前节点u为前驱节点 找到w后面的下一个第一个邻接节点 w
                //体现出广度优先遍历
                w = getNextNeighbor(u,w);
            }
        }
    }

    /**
     * 遍历所有节点 都进行广度优先搜索
     */
    public void bfs(){
        isVisited = new boolean[getNumOfVertex()];
        for (int i = 0; i < getNumOfVertex(); i++) {
            if(!isVisited[i]){
                bfs(isVisited,i);
            }
        }
    }

    /**
     * 插入节点
     * @param vertex 待插入节点的值
     */
    public void insertVertex(String vertex){
        vertexList.add(vertex);
    }

    /**
     * 添加边
     * @param v1 节点1的下标
     * @param v2 节点2的下标
     * @param weight 权值 1表示直连 0表示未连接
     */
    public void insertEdge(int v1,int v2,int weight){
        //无向图所以两个顶点都需要连接
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        ++numOfEdges;
    }

    //图中常用的方法

    /**
     * 返回节点个数
     * @return 节点个数
     */
    public int getNumOfVertex(){
        return vertexList.size();
    }

    /**
     * 返回边的数目
     * @return 边的数目  numOfEdges
     */
    public int getNumOfEdges(){
        return numOfEdges;
    }

    /**
     * 返回节点i(下标)对应的数据 0—>"A" 1—>"B" ....
     * @param i 下标
     * @return 值
     */
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    /**
     * 显示v1和v2的权值
     * @param v1 下标
     * @param v2 下标
     * @return 权值
     */
    public int getWeight(int v1,int v2){
        return edges[v1][v2];
    }

    /**
     * 显示图对应的矩阵
     */
    public void showGraph(){
        for (int[] link : edges) {
            System.out.println(Arrays.toString(link));
        }
    }
}

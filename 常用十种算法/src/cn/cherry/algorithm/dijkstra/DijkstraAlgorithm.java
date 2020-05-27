package cn.cherry.algorithm.dijkstra;

import java.util.*;

/**
 * @program: DataStructures
 * @description: 迪杰斯特拉算法
 * @author: Mr.Cherry
 * @create: 2020-01-15 18:22
 **/
public class DijkstraAlgorithm {
    public static void main(String[] args) {
        char[] vertexs = {'A','B','C','D','E','F','G'};
        //邻接矩阵
        int[][] matrix = new int[vertexs.length][vertexs.length];
        //表示不可连接
        final int N = 65535;
        matrix[0]=new int[]{N,5,7,N,N,N,2};
        matrix[1]=new int[]{5,N,N,9,N,N,3};
        matrix[2]=new int[]{7,N,N,N,8,N,N};
        matrix[3]=new int[]{N,9,N,N,N,4,N};
        matrix[4]=new int[]{N,N,8,N,N,5,4};
        matrix[5]=new int[]{N,N,N,4,5,N,6};
        matrix[6]=new int[]{2,3,N,N,4,6,N};
        //创建Graph对象
        Graph graph = new Graph(vertexs, matrix);
        graph.showGraph();
        graph.dsj(2);
        graph.showDijkstra();
        graph.showRoute();

    }
}

class Graph{
    /**顶点数组*/
    private char[] vertex;
    /**邻接矩阵*/
    private int[][] matrix;
    /**顶点集合对象*/
    private VisitedVertex vv;

    /**
     * TreeSet<String> pre = new TreeSet();  不可重复  有序(自动将添加的数据进行从小到大排序)
     * Set<String> pre = new HashSet<>();    不可重复  无序
     * Set<String> pre=new LinkedHashSet<>();  不可重复 有序(按照添加顺序加入)
     */
    private List<String> result = new ArrayList();

    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
    }

    /**
     * 显示图
     */
    public void showGraph(){
        for (int[] link : matrix) {
            System.out.println(Arrays.toString(link));
        }
    }

    /**
     * 迪杰斯特拉算法实现
     * @param index 出发顶点的下标
     */
    public void dsj(int index){
        //创建顶点集合对象
        vv = new VisitedVertex(vertex.length, index,vertex);
        //将出发顶点的前驱顶点设置为-1
        vv.updatePre(index,-1);
        //更新index顶点到周围顶点的距离和前驱顶点
        update(index);
        for (int i = 1; i < vertex.length; i++) {
            //选择并返回新的访问节点
            index = vv.updateArr();
            //更新index顶点到周围顶点的距离和前驱顶点
            update(index);
        }

        System.out.println();
    }


    /**
     * 显示最后的结果
     */
    public void showDijkstra(){
        vv.show();
    }

    /**
     * 显示路线
     */
    public void showRoute(){
        vv.showRoute();
    }


    /**
     * 更新index下标顶点到周围顶点的距离和周围顶点的前驱顶点
     * @param index 待更新的顶点下标
     */
    private void update(int index){
        int len = 0;
        //根据遍历邻接矩阵的matrix[index]行
        for (int i = 0; i < matrix[index].length; i++) {
            //len 含义：出发顶点到index顶点的距离 + 从index顶点到i顶点的距离之和
            len = vv.getDis(index) + matrix[index][i];
            //如果i顶点没有被访问过 并且len 小于出发顶点到i顶点的距离 就需要更新
            if(!vv.in(i) && len < vv.getDis(i)){
                //更新i顶点的前驱为index顶点
                vv.updatePre(i,index);
                //更新出发顶点到i顶点的距离
                vv.updateDis(i,len);
            }
        }

    }

}

/**
 * 已访问顶点集合
 */
class VisitedVertex {
    /**记录各个顶点是否访问过 1表示访问过,0未访问,会动态更新*/
    public int[] already_arr;
    /**每个下标对应的值为前一个顶点下标, 会动态更新*/
    public int[] pre_visited;
    /**记录出发顶点到其他所有顶点的距离,比如G为出发顶点，
     * 就会记录G到其它顶点的距离，会动态更新，求的最短距离就会存放到dis*/
    public int[] dis;
    /**顶点数组*/
    private char[] vertexs;

    /**
     * 构造器
     * @param length 顶点个数
     * @param index 出发顶点对应的下标
     */
    public VisitedVertex(int length,int index,char[] vertexs) {
        this.vertexs = vertexs;
        this.already_arr = new int[length];
        this.pre_visited = new int[length];
        this.dis = new int[length];
        //初始化 dis数组   Arrays.fill(数组变量,要更改的内容)  更改一个数组的所有值
        Arrays.fill(dis,65535);
        //设置出发顶点被访问过
        this.already_arr[index] = 1;
        //将出发顶点的访问距离设置为0
        this.dis[index] = 0;
    }

    /**
     * 判断index顶点是否被访问过
     * @param index 下标
     * @return 如果访问过就返回true 否则返回false
     */
    public boolean in(int index){
        return already_arr[index] == 1;
    }

    /**
     * 更新出发顶点到index顶点的距离
     * @param index 顶点下标
     * @param len 距离值
     */
    public void updateDis(int index,int len){
        dis[index] = len;
    }

    /**
     * 更新pre顶点的前驱顶点为index顶点
     * @param pre 当前顶点
     * @param index 前驱顶点
     */
    public void updatePre(int pre,int index){
        pre_visited[pre] = index;
    }


    /**
     * 返回出发顶点到index顶点的距离
     * @param index index顶点 (目标顶点)
     * @return 两点的距离
     */
    public int getDis(int index){
        return dis[index];
    }


    /**
     * 继续选择并返回新的访问顶点 比如这里的G 完后 就是A点作为新的访问顶点 (不是出发顶点)
     * @return 新的访问顶点的下标
     */
    public int updateArr(){
        int min = 65535,index = 0;
        for (int i = 0; i < already_arr.length; i++) {
            if(already_arr[i] == 0 && dis[i] < min){
                min = dis[i];
                index = i;
            }
        }
        //最后会找到一个权值最小并且没有被访问过的顶点下标
        //更新 index 顶点被访问过
        already_arr[index] = 1;
        return index;
    }

    /**
     * 显示最后的结果
     * 即显示三个数组的情况
     */
    public void show(){
        System.out.println("--------------------");
        System.out.println(Arrays.toString(already_arr));
        System.out.println(Arrays.toString(pre_visited));
        System.out.println(Arrays.toString(dis));

        int count = 0;
        for (int i : dis) {
            if (i != 65535){
                System.out.print(vertexs[count] + "("+i+") ");
            }else {
                System.out.print("N");
            }
            count++;
        }
        System.out.println();
    }

    /**
     * 显示路线
     */
    public void showRoute(){
        System.out.println("显示路线 ---------------------");
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < pre_visited.length; i++) {
            StringBuilder str = new StringBuilder();
            if(pre_visited[i] == -1){
                //continue 用于终止某次循环过程，即跳过循环体中尚未执行的语句，
                // 接着进行下一次是否执行循环的判断
                continue;
            }
            int temp = i;
            while (pre_visited[temp] != -1){
                stack.push(vertexs[temp]);
                temp = pre_visited[temp];
            }
            stack.push(vertexs[temp]);
            while (!stack.empty()){
                str.append(stack.pop());
                if(!stack.empty()){
                    str.append("->");
                }
            }
            System.out.printf("%"+(vertexs.length*2-2)+"s | (%d) \n",str,dis[i]);
        }
    }

}
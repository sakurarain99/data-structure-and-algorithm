package cn.cherry.树.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @program: DataStructures
 * @description: 赫夫曼树
 * @author: Mr.Cherry
 * @create: 2019-12-29 15:42
 **/
public class HuffmanTree {
    public static void main(String[] args) {
        int arr[] = {13, 7, 8, 3, 29, 6, 1};
        Node huffmanTree = createHuffmanTree(arr);
        System.out.println("huffmanTree = " + huffmanTree);
        huffmanTree.preOrder();
    }

    /**
     * 创建一个赫夫曼树
       构成赫夫曼树的步骤：
         1) 从小到大进行排序, 将每一个数据，每个数据都是一个节点 ， 每个节点可以看成是一颗最简单的二叉树
         2) 取出根节点权值最小的两颗二叉树
         3) 组成一颗新的二叉树, 该新的二叉树的根节点的权值是前面两颗二叉树根节点权值的和
         4) 再将这颗新的二叉树，以根节点的权值大小 再次排序，
         不断重复  1-2-3-4 的步骤，直到数列中，所有的数据都被处理，就得到一颗赫夫曼树
     * @param arr 要构成赫夫曼树的数组
     * @return 赫夫曼树的root节点
     */
    public static Node createHuffmanTree(int[] arr){
        /**
         * 第一步为了方便操作
         * 1.遍历arr数组
         * 2.将arr的每个元素构建成一个Node
         * 3.将Node放入到ArrayList中
         */
        List<Node> nodes = new ArrayList<>();
        for (int value : arr) {
            nodes.add(new Node(value));
        }
        /**
         * 最后集合中剩余的一个node 就是整个赫夫曼树的root节点
         */
        while (nodes.size() > 1){
            //排序
            Collections.sort(nodes);

            //取出根节点权值最小的两棵二叉树
            //(1).取出当前权值最小的节点(二叉树)  并从list中删除
            Node leftNode = nodes.remove(0);
            //(2).取出当前权值最小的节点(二叉树)  并从list中删除
            Node rightNode = nodes.remove(0);

            //(3).构建一颗新的二叉树   权值是两个子树的权值之和
            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;
            //(4).将新构成的二叉树放入集合
            nodes.add(parent);
        }
        return nodes.get(0);
    }
}

/**
 * 创建节点类
 * 为了让Node 对象支持排序Collections集合排序
 * 需要人Node类 实现Comparable接口
 */
class Node implements Comparable<Node>{
    /**
     * 节点权值
     */
    int value;
    /**
     * 指向左子节点
     */
    Node left;
    /**
     * 指向右子节点
     */
    Node right;

    public Node(int value) {
        this.value = value;
    }


    /**
     * 前序遍历
     */
    public void preOrder(){
        //输出节点
        System.out.println(this);
        if(this.left != null){
            this.left.preOrder();
        }
        if(this.right != null){
            this.right.preOrder();
        }
    }



    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }


    @Override
    public int compareTo(Node o) {
        //现在是从小到大  如果要从大到小需要改成 -(this.value - o.value)
        return this.value - o.value;
    }
}
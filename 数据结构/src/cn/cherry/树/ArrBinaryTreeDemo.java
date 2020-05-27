package cn.cherry.树;

/**
 * @program: DataStructures
 * @description: 顺序存储二叉树
   顺序存储二叉树的特点:
    顺序二叉树通常只考虑完全二叉树
    第n个元素的左子节点为  2 * n + 1
    第n个元素的右子节点为  2 * n + 2
    第n个元素的父节点为  (n-1) / 2
    n : 表示二叉树中的第几个元素(按0开始编号)

 * @author: Mr.Cherry
 * @create: 2019-12-26 20:24
 **/
public class ArrBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7};
        ArrBinaryTree binaryTree = new ArrBinaryTree(arr);
        System.out.println("前序遍历");
        //1,2,4,5,3,6,7
        binaryTree.preOrder();
        System.out.println("中序遍历");
        //4,2,5,1,4,3,7
        binaryTree.infixOrder();
        System.out.println("后序遍历");
        //4,5,2,6,7,3,1
        binaryTree.postOrder();
    }
}

/**
 * 编写一个ArrBinaryTree 实现顺序存储二叉树遍历
 */
class ArrBinaryTree{
    /**
     * 存储数据节点的数组
     */
    private int[] arr;

    public ArrBinaryTree(int[] arr) {
        this.arr = arr;
    }

    public void preOrder(){
        this.preOrder(0);
    }

    public void infixOrder(){
        this.infixOrder(0);
    }
    public void postOrder(){
        this.postOrder(0);
    }
    /**
     * 前序遍历
     * @param index 数组的下标
     */
    public void preOrder(int index){
        //如果数组为空 或者 arr.length = 0
        if(arr == null || arr.length == 0){
            System.out.println("数组为空，不能按照二叉树的前序遍历");
            return;
        }
        //输出当前这个元素
        System.out.println(arr[index]);
        //向左递归遍历
        if ((index * 2 + 1) <  arr.length){
            preOrder(2 * index + 1);
        }
        //向右递归遍历
        if ((index * 2 + 2) < arr.length){
            preOrder(index * 2 + 2);
        }
    }
    /**
     * 中序遍历
     * @param index 数组的下标
     */
    public void infixOrder(int index){
        //如果数组为空 或者 arr.length = 0
        if(arr == null || arr.length == 0){
            System.out.println("数组为空，不能按照二叉树的前序遍历");
            return;
        }
        //向左递归遍历
        if ((index * 2 + 1) <  arr.length){
            infixOrder(2 * index + 1);
        }
        //输出当前这个元素
        System.out.println(arr[index]);
        //向右递归遍历
        if ((index * 2 + 2) < arr.length){
            infixOrder(index * 2 + 2);
        }
    }
    /**
     * 后序遍历
     * @param index 数组的下标
     */
    public void postOrder(int index){
        //如果数组为空 或者 arr.length = 0
        if(arr == null || arr.length == 0){
            System.out.println("数组为空，不能按照二叉树的前序遍历");
            return;
        }
        //向左递归遍历
        if ((index * 2 + 1) < arr.length) {
            postOrder(index * 2 + 1);
        }
        //向右递归遍历
        if ((index * 2 + 2) < arr.length) {
            postOrder(index * 2 + 2);
        }
        //输出当前这个元素
        System.out.println(arr[index]);
    }
}

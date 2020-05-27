package cn.cherry.树;

/**
 * @program: DataStructures
 * @description: 二叉树 无序的 练习使用
 * @author: Mr.Cherry
 * @create: 2019-12-26 15:25
 **/
public class BinaryTreeDemo {
    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();

        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");
        HeroNode node6 = new HeroNode(6, "测试1");
        HeroNode node7 = new HeroNode(7, "测试2");

        //先手动创建二叉树 后面在使用递归创建、
        root.setLeft(node2);
        root.setRight(node3);
        node5.setLeft(node6);
        node5.setRight(node7);
        node3.setLeft(node5);
        node3.setRight(node4);
        binaryTree.setRoot(root);

        System.out.println("前序遍历");
        binaryTree.preOrder();


        binaryTree.delete(3);
        System.out.println("删除后");
        binaryTree.preOrder();

       /* System.out.println("中序遍历");
        binaryTree.infixOrde();

        System.out.println("后序遍历");
        binaryTree.postOrde();*/

        int no= 5;

       /* //前序遍历查找   前序遍历的次数：4
        System.out.println("前序遍历查询 - ");
        HeroNode result = binaryTree.preOrdeSearch(no);
        if(result != null){
            System.out.printf("找到了 信息为：%s\n",result);
        }else {
            System.out.printf("没有找到编号为：%d的英雄\n",no);
        }

        //前序遍历查找   前序遍历的次数：3
        System.out.println("中序遍历查询 - ");
        result = binaryTree.infixOrderSearch(no);
        if(result != null){
            System.out.printf("找到了 信息为：%s\n",result);
        }else {
            System.out.printf("没有找到编号为：%d的英雄\n",no);
        }
*/

        //前序遍历查找   前序遍历的次数：2
        /*System.out.println("后序遍历查询 - ");
        HeroNode result = binaryTree.postOrderSearch(no);
        if(result != null){
            System.out.printf("找到了 信息为：%s\n",result);
        }else {
            System.out.printf("没有找到编号为：%d的英雄\n",no);
        }*/

    }
}

class BinaryTree{

    private HeroNode root;

    public void setRoot(HeroNode root) {
        this.root = root;
    }
    /**
     * 前序遍历
     */
    public void preOrder(){
        if (this.root != null) {
            this.root.preOrde();
        }else {
            System.out.println("二叉树为空不能遍历 - ");
        }
    }
    /**
     * 中序遍历
     */
    public void infixOrde(){
        if (this.root != null) {
            this.root.infixOrde();
        }else {
            System.out.println("二叉树为空不能遍历 - ");
        }
    }
    /**
     * 后序遍历
     */
    public void postOrde(){
        if (this.root != null) {
            this.root.postOrde();
        }else {
            System.out.println("二叉树为空不能遍历 - ");
        }
    }


    /**
     * 前序遍历查找
     * @param no 要查找的编号
     * @return 返回的结果
     */
    public HeroNode preOrdeSearch(int no){
        if (root != null){
            return root.preOrdeSearch(no);
        }else{
            return null;
        }
    }

    /**
     * 中序遍历查找
     * @param no 要查找的编号
     * @return 返回的结果
     */
    public HeroNode infixOrderSearch(int no){
        if (root != null){
            return root.infixOrderSearch(no);
        }else{
            return null;
        }
    }


    /**
     * 后序遍历查找
     * @param no 要查找的编号
     * @return 返回的结果
     */
    public HeroNode postOrderSearch(int no){
        if (root != null){
            return root.postOrderSearch(no);
        }else{
            return null;
        }
    }

    public void delete(int no){
        if (root != null){
            if(root.getNo() != no){
                int delete = root.delete(no);
                if (delete == 1){
                    System.out.println("删除成功");
                }else {
                    System.out.println("未找到 "+no);
                }
            }else {
                root = null;
                System.out.println("删除成功");
            }
        }else{
            System.out.println("空树");
        }
    }

}

class HeroNode{
    private int no;
    private String name;
    private HeroNode left;
    private HeroNode right;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * 前序遍历
     */
    public void preOrde(){
        //输出父节点
        System.out.println(this);
        //递归向左子树前序遍历
        if (this.left != null){
            this.left.preOrde();
        }
        //递归向右子树前序遍历
        if(this.right != null){
            this.right.preOrde();
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrde(){
        //递归向左子树中序遍历
        if (this.left != null){
            this.left.infixOrde();
        }
        //输出父节点
        System.out.println(this);
        //递归向右子树中序遍历
        if(this.right != null){
            this.right.infixOrde();
        }
    }

    /**
     * 后序遍历
     */
    public void postOrde(){
        //递归向左子树后序遍历
        if (this.left != null){
            this.left.postOrde();
        }
        //递归向右子树后序遍历
        if(this.right != null){
            this.right.postOrde();
        }
        //输出父节点
        System.out.println(this);
    }


    /**
     * 前序遍历查找
     * @param no 要查找的编号
     * @return 返回的结果
     */
    public HeroNode preOrdeSearch(int no){
        System.out.println("前序遍历查找");
        if(this.no == no){
            return this;
        }
        HeroNode result = null;
        //向左前序递归
        if (this.left != null) {
            result = this.left.preOrdeSearch(no);
        }
        //每次左节点判断 都对result进行判断 如果不为空 就是找到了 就直接返回
        if(result != null){
            return result;
        }
        //向右进行前序递归
        if (this.right != null) {
            result = this.right.preOrdeSearch(no);
        }
        //最后无论是否找到都返回
        return result;
    }

    /**
     * 中序遍历查找
     * @param no 要查找的编号
     * @return 返回的结果
     */
    public HeroNode infixOrderSearch(int no){
        //返回的结果变量
        HeroNode result = null;
        //当左节点不为空时  向左进行中序递归
        if (this.left != null) {
            result = this.left.infixOrderSearch(no);
        }
        //每次左节点判断 都对result进行判断 如果不为空 就是找到了 就直接返回
        if (result != null) {
            return result;
        }
        System.out.println("中序遍历查找");
        //判断是否找到
        if (this.no == no) {
            return this;
        }
        //当右节点不为空时  向右进行中序递归
        if (this.right != null) {
            result = this.right.infixOrderSearch(no);
        }
        //最后无论是否找到都返回
        return result;
    }

    /**
     * 后序遍历查找
     * @param no 要查找的编号
     * @return 返回的结果
     */
    public HeroNode postOrderSearch(int no){
        //返回的结果变量
        HeroNode result = null;
        //当左节点不为空时  向左进行中序递归
        if (this.left != null) {
            result = this.left.postOrderSearch(no);
        }
        if (result != null) {
            return result;
        }
        //当右节点不为空时  向右进行中序递归
        if (this.right != null) {
            result = this.right.postOrderSearch(no);
        }
        if (result != null) {
            return result;
        }
        System.out.println("后序遍历查找");
        //判断是否找到
        if (this.no == no) {
            return this;
        }
        return result;
    }



    public int delete(int no){
        int result = 0;
        if (this.left != null && this.left.no == no) {
            if(this.left.left == null && this.left.right == null){
                this.left = null;
            }else {
                this.left = tempDel(this.left);
            }
            System.out.println("删除成功");
            return 1;
        }
        if (this.right != null && this.right.no == no) {
            if(this.right.left == null && this.right.right == null){
                this.right = null;
            }else {
                this.right = tempDel(this.right);
            }
            System.out.println("删除成功");
            return 1;
        }
        if(this.left != null){
            result = this.left.delete(no);
        }
        if(result == 1){
            return result;
        }
        if(this.right != null){
            result = this.right.delete(no);
        }
        if(result == 1){
            return result;
        }
        return -1;
    }


    private HeroNode tempDel(HeroNode heroNode){
        if (heroNode.left != null) {
            HeroNode result = heroNode.left;
            if(heroNode.right != null){
                HeroNode r = result;
                while (r.right != null){
                   r = r.right;
                }
                r.right = heroNode.right;
                return result;
            }else{
                return result;
            }
        }else{
            return heroNode.right;
        }
    }

}

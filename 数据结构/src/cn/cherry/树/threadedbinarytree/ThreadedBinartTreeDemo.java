package cn.cherry.树.threadedbinarytree;

/**
 * @program: DataStructures
 * @description: 线索化二叉树
 * @author: Mr.Cherry
 * @create: 2019-12-27 14:45
 **/
public class ThreadedBinartTreeDemo {
    public static void main(String[] args) {
        HeroNode root = new HeroNode(1, "tom");
        HeroNode node2 = new HeroNode(3, "jack");
        HeroNode node3 = new HeroNode(6, "smith");
        HeroNode node4 = new HeroNode(8, "mary");
        HeroNode node5 = new HeroNode(10, "king");
        HeroNode node6 = new HeroNode(14, "dim");

        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);

        ThreadedBinaryTree threadedBinaryTree=
                new ThreadedBinaryTree();
        threadedBinaryTree.setRoot(root);

        threadedBinaryTree.postOrder();

        //测试线索化  中序
        /*threadedBinaryTree.infixThreadedNodes();

        threadedBinaryTree.infixThreadedList();
        //测试线索化  前序
        threadedBinaryTree.preThreadedNodes();*/
        threadedBinaryTree.postThreadedNodes();

        HeroNode left = node5.getLeft();
        System.out.println("left = " + left);
        HeroNode right = node5.getRight();
        System.out.println("right = " + right);
        System.out.println("后序线索化 遍历");
        threadedBinaryTree.postThreadedList();


    }
}

/**
 * 定义 Threaded 实现了线索化功能的二叉树
 */
class ThreadedBinaryTree{

    private HeroNode root;

    /**
     * 为了实现线索化 需要创建一个指向当前节点的前驱节点的指针
     * 在递归进行线索化时 pre总是保留前一个节点
     */
    private HeroNode pre = null;

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    public void preThreadedNodes(){
        this.preThreadedNodes(root);
    }
    public void infixThreadedNodes(){
        this.infixThreadedNodes(root);
    }
    public void postThreadedNodes(){this.postThreadedNodes(root);}


    /**
     * 遍历线索化二叉树的方法  前序
     */
    public void preThreadedList(){
        //定义一个变量 存储当前遍历的节点 从root开始
        HeroNode node = root;
        while (node != null){
            int b = 0;
            System.out.println(node);
            if(node.getRight() == null){
                return;
            }
            if (node.getLeftType() == 0){
                node = node.getLeft();
            }else if(node.getRightType() == 1){
                node = node.getRight();
            }
        }
    }
    /**
     * 遍历线索化二叉树的方法  中序
     */
    public void infixThreadedList(){
        //定义一个变量 存储当前遍历的节点 从root开始
        HeroNode node = root;
        while (node != null){
            //循环的找到leftType == 1的节点 第一个找到的就是8节点
            //后面随着遍历而变化 因为当leftType == 1时 说明该节点时按照线索化处理后的有效节点
            while (node.getLeftType() == 0){
                node = node.getLeft();
            }
            //打印当前节点
            System.out.println(node);
            //如果当前节点的右指针指向的是后继节点 就一直输出
            while (node.getRightType() == 1){
                //获取当前节点的后继节点
                node = node.getRight();
                System.out.println(node);
            }
            //替换这个遍历的节点  否则会出现死循环
            node = node.getRight();
        }

    }

    public void postThreadedList(){
        this.postThreadedList(root);
    }
    /**
     * 遍历线索化二叉树的方法  后序
     * @param node 即将遍历的节点  第一次是root节点
     */
    public void postThreadedList(HeroNode node){
        //判断当前节点是否为空 如果为空直接返回
        if(node == null){
            return;
        }
        //判断当前的左右节点是否已经输出  如果是直接返回
        if(node.getRight() == pre){
            return;
        }
        /**
         * node.getLeft() != null 当前元素的左子树不为空
         * pre != node.getLeft()   上一个输出的不是当前元素的左子树
         * node.getLeftType() == 0 当前的左节点类型为0    1：左侧是一个前驱节点    0：左侧是子树
         * 如果当前节点的左侧不为空 && 上一个遍历的节点不是当前节点的左侧节点 && 当前节点的左节点类型 为0
         * 则进行递归 遍历 直到找到 左侧的 叶子节点
         */
        if (node.getLeft() != null && pre != node.getLeft() && node.getLeftType() == 0){
            postThreadedList(node.getLeft());
        }
        /**
         * 和上一个相识  这个是找到右侧的 叶子节点
         */
        if (node.getRight() != null && pre != node.getRight() && node.getRightType() == 0){
            postThreadedList(node.getRight());
        }
        ///判断当前节点是否是上一个已经输出过的节点  如果是直接返回
        if(node == pre){
            return;
        }
        //将当前要输出的节点 赋给pre   pre：上一个执行过的节点
        pre = node;
        //输出当前节点
        System.out.println(node);
        /**
         * node.getRightType() == 1  判断当前节点的右侧是否是后继节点
         * node.getRight() != null    后继节点不为空 进行递归 输出 直到右侧是子树
         * 根据后继线索进行 递归遍历
         */
        if(node.getRightType() == 1 && node.getRight() != null){
            postThreadedList(node.getRight());
        }

    }

    /**
     * 对二叉树进行前序线索化的方法
     * @param node 就是当前需要线索化的节点
     */
    public void preThreadedNodes(HeroNode node){
        if (node == null){
            return;
        }
        if(node.getLeft() == null){
            //指定当前节点的前驱节点
            node.setLeft(pre);
            //指定当前left指定的是前驱节点而不是 子树
            node.setLeftType(1);
        }
        //2.2 线索化后继节点  后继节点不能在当前节点完成 而是在调用这个递归的那个方法完成
        if(pre != null && pre.getRight() == null){
            //让前驱节点的右指针指向当前节点
            pre.setRight(node);
            //修改前驱节点的右指针类型
            pre.setRightType(1);
        }
        //！！！！每处理一个节点后 让当前节点是下一个节点的前驱节点
        pre = node;
        //判断当前节点的左节点类型(LeftType) 如果为1 则找到了左节点的叶子节点 不进行向左递归
        //如果左节点是子树LeftType == 0  则进行向左递归
        if(node.getLeftType() != 1){
            preThreadedNodes(node.getLeft());
        }
        //判断当前节点的左节点类型(RightType) 如果为1 则找到了右节点的叶子节点 不进行向右递归
        //如果左节点是子树RightType == 0  则进行向左递归
        if(node.getRight() != null && node.getRightType() != 1){
            preThreadedNodes(node.getRight());
        }
    }
    /**
     * 对二叉树进行中序线索化的方法
     * @param node 就是当前需要线索化的节点
     */
    public void infixThreadedNodes(HeroNode node){
        if (node == null){
            return;
        }
        //(一) 先线索化左子树  递归
        infixThreadedNodes(node.getLeft());
        //(二) 线索化当前节点
        //2.1 线索化当前节点的前驱节点
        if (node.getLeft() == null) {
            //指定当前节点的前驱节点
            node.setLeft(pre);
            //指定当前left指定的是前驱节点而不是 子树
            node.setLeftType(1);
        }
        //2.2 线索化后继节点  后继节点不能在当前节点完成 而是在调用这个递归的那个方法完成
        if(pre != null && pre.getRight() == null){
            //让前驱节点的右指针指向当前节点
            pre.setRight(node);
            //修改前驱节点的右指针类型
            pre.setRightType(1);
        }
        //！！！！每处理一个节点后 让当前节点是下一个节点的前驱节点
        pre = node;

        //(三) 再线索化右子树  递归
        infixThreadedNodes(node.getRight());
    }
    /**
     * 对二叉树进行后序线索化的方法
     * @param node 就是当前需要线索化的节点
     */
    public void postThreadedNodes(HeroNode node){
        if (node == null) {
            return;
        }
        if(node.getLeft() != null){
            postThreadedNodes(node.getLeft());
        }
        if(node.getRight() != null){
            postThreadedNodes(node.getRight());

        }
        if (node.getLeft() == null) {
            //指定当前节点的前驱节点
            node.setLeft(pre);
            //指定当前left指定的是前驱节点而不是 子树
            node.setLeftType(1);
        }
        //2.2 线索化后继节点  后继节点不能在当前节点完成 而是在调用这个递归的那个方法完成
        if(pre != null && pre.getRight() == null){
            //让前驱节点的右指针指向当前节点
            pre.setRight(node);
            //修改前驱节点的右指针类型
            pre.setRightType(1);
        }
        //！！！！每处理一个节点后 让当前节点是下一个节点的前驱节点
        pre = node;
    }


    /**
     * 前序遍历
     */
    public void preOrderr(){
        if (this.root != null) {
            this.root.preOrder();
        }else {
            System.out.println("二叉树为空不能遍历 - ");
        }
    }
    /**
     * 中序遍历
     */
    public void infixOrder(){
        if (this.root != null) {
            this.root.infixOrder();
        }else {
            System.out.println("二叉树为空不能遍历 - ");
        }
    }
    /**
     * 后序遍历
     */
    public void postOrder(){
        if (this.root != null) {
            this.root.postOrder();
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
    /**
     * 说明
     * 1.如果leftType == 0 表示指向的是左子树 如果是1则表示指向的前驱节点
     * 2.如果rightType == 0 表示指向的是右子树 如果是1则表示指向的后记节点
     */
    private int leftType;
    private int rightType;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }


    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
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
    public void preOrder(){
        //输出父节点
        System.out.println(this);
        //递归向左子树前序遍历
        if (this.left != null){
            this.left.preOrder();
        }
        //递归向右子树前序遍历
        if(this.right != null){
            this.right.preOrder();
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder(){
        //递归向左子树中序遍历
        if (this.left != null){
            this.left.infixOrder();
        }
        //输出父节点
        System.out.println(this);
        //递归向右子树中序遍历
        if(this.right != null){
            this.right.infixOrder();
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder(){
        //递归向左子树后序遍历
        if (this.left != null){
            this.left.postOrder();
        }
        //递归向右子树后序遍历
        if(this.right != null){
            this.right.postOrder();
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
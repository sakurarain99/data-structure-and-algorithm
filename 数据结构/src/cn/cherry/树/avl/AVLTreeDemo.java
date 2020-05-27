package cn.cherry.树.avl;

/**
 * @program: DataStructures
 * @description: 平衡二叉树/红黑树
 * @author: Mr.Cherry
 * @create: 2020-01-05 17:03
 **/
public class AVLTreeDemo {
    public static void main(String[] args) {
        //int[] arr = {4,3,6,5,7,8};
        //int[] arr = {10,12, 8, 9, 7, 6};
        int[] arr = { 10, 11, 7, 6, 8, 9 };
        AVLTree avlTree = new AVLTree();
        //循环的添加节点到二叉排序树
        for (int i = 0; i < arr.length; i++) {
            avlTree.add(new Node(arr[i]));
        }
        System.out.println("中序遍历");
        avlTree.infixOrder();
        System.out.println("平衡处理后");
        System.out.println("树的高度：" + avlTree.getRoot().height());
        System.out.println("树的左子树的高度：" + avlTree.getRoot().leftHeight());
        System.out.println("树的右子树的高度：" + avlTree.getRoot().rightHeight());
        System.out.println("当前根节点："+avlTree.getRoot());
    }
}

/**
 *
 */
class AVLTree{
    private Node root;


    /**
     * 查找要删除的节点
     * @param value 希望删除的节点的值
     * @return 如果找到返回该节点 否则返回null
     */
    public Node search(int value){
        if(root == null){
            return null;
        }else {
            return root.search(value);
        }
    }

    /**
     * 查找要删除节点的父节点
     * @param value 希望删除的节点的值
     * @return 如果找到返回父节点 否则返回null
     */
    public Node searchParent(int value){
        if(root == null){
            return null;
        }else {
            return root.searchParent(value);
        }
    }

    /**
     * 删除节点
     * @param value 要删除节点的值
     */
    public void delNode(int value){
        if(root == null){
            return;
        }else {
            //(1) 需要先去找到要删除的结点  targetNode
            Node targetNode = search(value);
            if(targetNode == null){
                return;
            }
            //如果我们发现当前这棵二叉排序树只有一个节点
            if(root.left == null && root.right == null){
                root = null;
                return;
            }
            //(2) 找到targetNode 的 父结点 parent
            Node parent = searchParent(targetNode.value);
            //如果要删除的节点是叶子节点
            if(targetNode.left == null && targetNode.right == null){
                //判断targetNode 是父节点的左子节点还是右子节点
                if(parent.left == targetNode){
                    parent.left = null;
                }else {
                    parent.right = null;
                }
            }else if(targetNode.left != null && targetNode.right != null){
                //右子树最小的
                int temp = delRightTreeMin(targetNode.right);
                //将temp的值赋给要删除的节点，改变要删除节点的值 即是变相的删除了这个节点
                targetNode.value = temp;
            }else {
                if(parent != null){
                    //删除的节点只有一颗子树的节点
                    //如果targetNode 是父节点的左子节点
                    if(parent.left == targetNode){
                        if (targetNode.left != null) {
                            //如果当前节点的左子节点不为空
                            //则把当前节点的父节点的左子节点置为 当前节点的左子节点
                            parent.left = targetNode.left;
                        }else {
                            //否则，把当前节点的父节点的左子节点置为 当前节点的右子节点
                            parent.left = targetNode.right;
                        }
                    }else {
                        //和上面类似
                        if (targetNode.left != null) {
                            parent.right = targetNode.left;
                        }else {
                            parent.right = targetNode.right;
                        }
                    }
                }else {
                    if (targetNode.left != null){
                        root = targetNode.left;
                    }else {
                        root = targetNode.right;
                    }
                }
            }
        }
    }

    /**
     * 返回以node为根节点的二叉排序树的最小值的节点，并且删除该节点
     * @param node 传入的节点(当做二叉排序树的根节点)
     * @return 返回以node为根节点的二叉排序树的最小值的节点
     */
    public int delRightTreeMin(Node node){
        Node target = node;
        //循环的查找左节点 就会找到最小值
        while (target.left != null){
            target = target.left;
        }
        //这时target就指向了最小节点
        //删除最小节点
        delNode(target.value);
        return target.value;
    }

    /**
     * 返回以node为根节点的二叉排序树的最大值的节点，并且删除该节点
     * @param node 传入的节点(当做二叉排序树的根节点)
     * @return 返回以node为根节点的二叉排序树的最大值的节点
     */
    public int delLeftTreeMax(Node node){
        Node target = node;
        //循环的查找左节点 就会找到最小值
        while (target.right != null){
            target = target.right;
        }
        //这时target就指向了最小节点
        //删除最小节点
        delNode(target.value);
        return target.value;
    }


    public void add(Node node){
        if (root == null){
            //如果root为空直接让root指向node
            root = node;
        }else {
            root.add(node);
        }
    }

    public void infixOrder(){
        if(root != null){
            root.infixOrder();
        }else {
            System.out.println("二叉树为空");
        }
    }

    public Node getRoot() {
        return root;
    }
}


/**
 * 创建Node节点
 */
class Node{
    public int value;
    public Node left;
    public Node right;

    public Node(int value) {
        this.value = value;
    }


    /**
     * 左旋转方法
     */
    public void leftRotate(){
        //1.创建一个新的节点 指向根节点
        Node newNode = new Node(value);
        //2.把新节点的左子树指向当前节点的左子树
        newNode.left = left;
        //3.将新节点的右子树设置为当前节点的右子树的左子树
        newNode.right = right.left;
        //4.把当前节点的值换为右子节点的值
        value = right.value;
        //5.把当前节点的右子树设置成右子树的右子树
        right = right.right;
        //6.把当前节点的左子树指向新节点
        left = newNode;
    }

    /**
     * 右旋转方法
     */
    public void rightRotate(){
        Node newNode = new Node(value);
        newNode.right = right;
        newNode.left = left.right;
        value = left.value;
        left = left.left;
        right = newNode;
    }


    /**
     * 返回左子树的高度
     * @return
     */
    public int leftHeight(){
        if (left == null){
            return 0;
        }
        return left.height();
    }

    /**
     * 返回右子树的高度
     * @return
     */
    public int rightHeight(){
        if (right == null){
            return 0;
        }
        return right.height();
    }

    /**
     * 返回以该节点为根节点的树的高度
     * @return
     */
    public int height(){
        return Math.max(left == null ? 0 : left.height(),
                right == null ? 0 : right.height()) + 1;
    }


    /**
     * 查找要删除的节点
     * @param value 希望删除的节点的值
     * @return 如果找到返回该节点 否则返回null
     */
    public Node search(int value){
        if(this.value == value){
            return this;
        }else if(value < this.value){
            if(this.left == null){
                return null;
            }
            //向右子树递归查找
            return this.left.search(value);
        }else{
            if(this.right == null){
                return null;
            }
            //向右子树递归查找
            return this.right.search(value);
        }
    }

    /**
     * 查找要删除节点的父节点
     * @param value 希望删除的节点的值
     * @return 如果找到返回父节点 否则返回null
     */
    public Node searchParent(int value){
        /**
         * 当前左子树不为空 && 当前左子树的值等于当前要查找的值 ||
         * 当前右子树不为空 && 当前右子树的值等于当前要查找的值
         * 则进入if
         */
        if((this.left != null && this.left.value == value) ||
                this.right != null && this.right.value == value){
            return this;
        }else {
            //如果当前节点的值大于要查找的值 并且当前节点的左子节点不为空 向左递归
            if(this.left != null && this.value > value){
                return this.left.searchParent(value);
            }else if(this.right != null && this.value <= value){
                //如果当前节点的值小于要查找的值 并且当前节点的右子节点不为空 向右递归
                return this.right.searchParent(value);
            }else {
                //没有父节点
                return null;
            }
        }
    }

    /**
     * 添加节点 递归的形式添加节点 需要满足二叉排序树的要求   如果有重复的值放在右子树
     * @param node 要添加的节点
     */
    public void add(Node node){
        if (node == null){
            return;
        }
        //判断传入的节点的值 和当前子树的根节点的值关系
        if(node.value < this.value){
            //如果当前节点的左子节点为空 这直接挂到左子节点的位置
            if(this.left == null){
                this.left = node;
            }else {
                //递归的向左子树添加
                this.left.add(node);
            }
        }else{
            //添加的节点的值大于等于 当前节点的值
            if(this.right == null){
                this.right = node;
            }else {
                //递归的向右子树添加
                this.right.add(node);
            }
        }
        //当添加一个节点后  如果 (右子树的高度 - 左子树的高度) > 1  左旋转
        if (rightHeight() - leftHeight() > 1){
            //如果它的右子树的左子树高度大于它的右子树的右子树的高度 需要先对他的右子树进行右旋转
            if(right != null && right.leftHeight() > right.rightHeight()){
                //对右子树进行向右旋转
                right.rightRotate();
                //再对当前节点进行向左旋转
                leftRotate();
            }else {
                //直接左旋转
                leftRotate();
            }
            //必须要 ！！！！
            return;
        }
        //当添加一个节点后  如果 (左子树的高度 - 右子树的高度) > 1  右旋转
        if(leftHeight() - rightHeight() > 1){
            //如果它的左子树的右子树高度大于它的左子树的左子树的高度 需要先对他的左子树进行左旋转
            if(left != null && left.rightHeight() > left.leftHeight()){
                //对左子树进行向左旋转
                left.leftRotate();
                //再对当前节点进行向右旋转
                rightRotate();
            }else {
                //直接右旋转
                rightRotate();
            }
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder(){
        //左子节点不为空 则进行向左递归
        if (this.left != null){
            this.left.infixOrder();
        }
        ///输出当前节点
        System.out.println(this);
        //右子节点不为空 则进行向右递归
        if (this.right != null){
            this.right.infixOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}

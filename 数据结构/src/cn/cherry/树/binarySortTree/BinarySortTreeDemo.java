package cn.cherry.树.binarySortTree;

/**
 * @program: DataStructures
 * @description: 二叉排序树
 * @author: Mr.Cherry
 * @create: 2020-01-04 12:15
 **/
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7,3,10,12,5,1,9,2};
        BinarySortTree sortTree = new BinarySortTree();
        //循环的添加节点到二叉排序树
        for (int i = 0; i < arr.length; i++) {
            sortTree.add(new Node(arr[i]));
        }

        //中序遍历二叉排序树
        System.out.println("中序遍历二叉排序树");
        sortTree.infixOrder();
        sortTree.delNode(10);

        sortTree.delNode(2);
        sortTree.delNode(5);
        sortTree.delNode(1);

        sortTree.delNode(3);
        sortTree.delNode(9);
        sortTree.delNode(12);
        sortTree.delNode(7);
        System.out.println("删除之后");
        sortTree.infixOrder();

    }
}
class BinarySortTree{
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
                /*//删除的节点有两颗子树的节点
                //从targetNode 的右子树找到最小的结点 如果找左子树则需要找左子树的最大节点
                //辅助变量 temp 保存右子树的最小节点的值
                int temp = 0;
                //获得当前节点的右子节点 进行循环遍历
                Node targetNodeRight = targetNode.right;
                while (true){
                    //如果targetNodeRight 的左子节点不为空 进入if
                    if(targetNodeRight.left != null){
                        //如果targetNodeRight 的左子节点的左子节点不为空 则把他的左子节点 赋给targetNodeRight 继续循环
                        if(targetNodeRight.left.left != null){
                            targetNodeRight = targetNodeRight.left;
                        }else {
                            //如果targetNodeRight的左子节点不为空 但是它左子节点的左子节点为空 则把targetNodeRight左子节点的值赋给temp
                            temp = targetNodeRight.left.value;
                            //将targetNodeRight的左子节点置空  删除最小节点
                            targetNodeRight.left = null;
                            //跳出循环
                            break;
                        }
                    }else {
                        //当前节点的右子节点就是 左子树里最小的值时
                        //targetNodeRight 的值赋给temp
                        temp = targetNodeRight.value;
                        //使用targetNode(当前节点) 的右节点置为空
                        targetNode.right = null;
                        //跳出循环
                        break;
                    }
                }*/
                //左子树最大的
                //int temp = delLeftTreeMax(targetNode.left);
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
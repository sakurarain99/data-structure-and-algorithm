package cn.cherry.stack;


import java.util.Scanner;
import java.util.Stack;

/**
 * @program: DataStructures
 * @description: 单链表模拟栈
 * @author: Mr.Cherry
 * @create: 2019-12-20 15:48
 **/
public class SingleLinkedListSimulatioStack {
    public static void main(String[] args) {
        SingleLinkedList stack = new SingleLinkedList(4);
        String key = "";
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);

        System.out.println("s：表示显示栈");
        System.out.println("e：退出程序");
        System.out.println("p：添加数据到栈中");
        System.out.println("u：从栈顶取出数据");
        while (loop){
            System.out.println("请输入你的选择：");
            key = scanner.next();
            switch (key){
                case "s":
                    stack.showStack();
                    break;
                case "p":
                    System.out.printf("请输入要添加的值：");
                    int i = scanner.nextInt();
                    stack.push(i);
                    break;
                case "u":
                    try {
                        Integer pop = stack.pop();
                        System.out.printf("弹出的数据是：%d\n",pop);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "e":
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出 --");
    }
}

/**
 * 定义SingleLinkedList 管理链表
 */
class SingleLinkedList {
    /**
     * 初始化一个头节点 头节点不能发生改变 不存放具体的数据
     */
    private Node bottom = null;
    private Integer maxSize;

    public SingleLinkedList(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Node getHead() {
        return bottom;
    }

    public void setHead(Node head) {
        this.bottom = head;
    }

    /**
     * 向栈中添加数据 push
     */
    public void push(Integer value){
        if(size() >= maxSize){
            System.out.println("栈满 -");
            return;
        }
        Node node = new Node(value);
        //判断当前栈是否为空
        if(bottom == null){
            this.bottom = node;
            return;
        }
        /**
         * 因为bottom节点不能动 因此我们需要一个辅助节点 temp
         */
        Node temp = this.bottom;
        //遍历链表 找到最后
        while (true){
            //找到链表的最后
            if(temp.next == null){
                break;
            }
            //如果没有找到最后 将temp后移
            temp = temp.next;
        }
        //将最后这个节点的next 指向新的节点
        temp.next = node;
    }

    /**
     * 出栈
     * @return
     */
    public Integer pop(){
        //判断当前栈是否为空
        if(bottom == null){
            System.out.println("栈为空 不能弹出");
            return null;
        }
        Node temp = this.bottom;
        //只有一个数据的时候直接返回
        if(size() == 1){
            Integer result = temp.no;
            bottom = null;
            return result;
        }
        //遍历链表 找到最后
        while (true){
            //找到要弹出节点的前一个节点
            if(temp.next.next == null){
                break;
            }
            //如果没有找到最后 将temp后移
            temp = temp.next;
        }
        //返回当前节点的下一个节点的no
        Integer result = temp.next.no;
        //将当前节点的next设置为null 达到删除节点效果
        temp.next = null;
        return result;
    }

    /**
     * 显示栈所有数据
     */
    public void showStack(){
        //如果链表为空直接返回
        if(bottom == null){
            System.out.println("栈为空 - ");
            return;
        }
        if(bottom.next == null){
            System.out.printf("栈[0] ：%d\n",bottom.no);
            return;
        }
        Node next = bottom;
        Stack<Node> stack = new Stack<>();
        while (true){
            stack.push(next);
            if(next.next == null){
                break;
            }
            next = next.next;
        }
        for (int i = stack.size()-1;i >= 0;--i){
            System.out.printf("栈[%d] ：%d\n",i,stack.pop().no);
        }
    }

    /**
     * 栈内数据个数
     * @return
     */
    public Integer size(){
        Integer result = 0;
        Node next = bottom;
        if(next == null){
            return 0;
        }
        while (true){
            ++result;
            if (next.next == null) {
                return result;
            }
            next = next.next;
        }
    }

}

/**
 * 定义HeroNode 每一个HeroNode对象就是一个节点
 */
class Node{
    public int no;
    /**
     * 指向下一个节点
     */
    public Node next;

    public Node(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                '}';
    }
}



package cn.cherry.stack;

import java.util.Scanner;

/**
 * @program: DataStructures
 * @description: 使用数组模拟栈
 * @author: Mr.Cherry
 * @create: 2019-12-20 15:10
 **/
public class ArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(4);
        String key = "";
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);

        System.out.println("show：表示显示栈");
        System.out.println("exit：退出程序");
        System.out.println("push：添加数据到栈中");
        System.out.println("pop：从栈顶取出数据");
        while (loop){
            System.out.println("请输入你的选择：");
            key = scanner.next();
            switch (key){
                case "show":
                    stack.list();
                    break;
                case "push":
                    System.out.printf("请输入要添加的值：");
                    int i = scanner.nextInt();
                    stack.push(i);
                    break;
                case "pop":
                    try {
                        Integer pop = stack.pop();
                        System.out.println(pop);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
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
 * 定义一个ArrayStack  表示栈
 */
class ArrayStack{
    /**栈的大小 */
    private Integer maxSize;
    /**数组，数组模拟栈 数据就放在该数组中*/
    private Integer[] stack;
    /**栈顶 默认-1表示没有数据*/
    private Integer top = -1;

    /**
     * 构造器
     * @param maxSize 栈大小
     */
    public ArrayStack(Integer maxSize){
        this.maxSize = maxSize;
        stack = new Integer[maxSize];
    }

    /**
     * 栈满
     * @return true/false
     */
    public boolean inFull(){
        return top == (maxSize - 1);
    }

    /**
     * 栈空
     * @return true/false
     */
    public boolean isEmpty(){
        return top == -1;
    }

    /**
     * 入栈 push
     * @param value 要入栈的数据
     */
    public void push(Integer value){
        //先判断栈是否满
        if(inFull()){
            System.out.println("栈满");
            return;
        }
        ++top;
        stack[top] = value;
    }

    /**
     * 出栈 pop 将栈顶的数据返回
     * @return
     */
    public Integer pop(){
        //先判断栈是否空
        if(isEmpty()){
            //抛出异常
            throw new RuntimeException("栈空 没有数据");
        }
        Integer value = stack[top];
        --top;
        return value;
    }

    /**
     * 显示栈的情况[遍历栈] 遍历时，需要从栈顶开始显示数据
     */
    public void list(){
        //先判断栈是否空
        if(isEmpty()){
            System.out.println("栈空 没有数据");
            return;
        }
        for (int i = top;i >= 0;--i){
            System.out.printf("stack[%d] = %d\n",i,stack[i]);
        }
    }
}

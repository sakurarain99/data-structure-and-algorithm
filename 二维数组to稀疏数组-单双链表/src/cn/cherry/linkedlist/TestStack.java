package cn.cherry.linkedlist;

import java.util.Stack;

/**
 * @program: DataStructures
 * @description: 栈Stack的基本使用
 * @author: Mr.Cherry
 * @create: 2019-12-02 17:08
 **/
public class TestStack {
    public static void main(String[] args) {
        Stack<String> stack = new Stack();
        //入栈
        stack.add("jack");
        stack.add("tom");
        stack.add("smith");
        //出栈
        while (stack.size() > 0){
            //pop 将栈顶的值弹出
            System.out.println(stack.pop());
        }
    }
}

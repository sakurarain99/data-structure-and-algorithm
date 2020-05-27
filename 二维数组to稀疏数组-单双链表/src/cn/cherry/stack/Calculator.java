package cn.cherry.stack;

/**
 * @program: DataStructures
 * @description: 使用栈完成表达式的计算    (表达式为中缀表达式)
 *  * @author: Mr.Cherry
 * @create: 2019-12-21 15:28
 **/
public class Calculator {
    public static void main(String[] args) {
        //表达式运算   中缀表达式
        String expression = "7*2*2-5+1-5+3-4";
        //创建两个栈 数栈 和 符号栈
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);
        //定义需要的相关变量
        //用于扫描
        int index = 0;
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        //用于表示是否需要拼接数字  true：需要 false：不需要
        boolean Judge = true;
        //将每次扫描得到的char保存到ch
        char ch = ' ';
        //使用while循环扫描expression
        while (true){
            //依次得到expression 的每一个字符
            ch = expression.substring(index,index + 1).charAt(0);
            //判断ch是什么 然后做相应的处理
            if (operStack.isOper(ch)){
                Judge = false;
                //如果是运算符
                //判断当前的符号栈是否为空
                if (!operStack.isEmpty()){
                    //如果符号栈有操作符，就进行比较,如果当前的操作符的优先级小于或者等于栈中的操作符，就需要从数栈中pop出两个数,
                    //在从符号栈中pop出一个符号，进行运算，将得到结果，入数栈，然后将当前的操作符入符号栈，
                    if(operStack.priority(ch) <= operStack.priority(operStack.peek())){
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1,num2,oper);
                        //把运算的结果压入数栈
                        numStack.push(res);
                        //将当前的操作符入符号栈
                        operStack.push(ch);
                    }else {
                        //如果当前的操作符的优先级大于栈中的操作符， 就直接入符号栈.
                        operStack.push(ch);
                    }
                }else {
                    //如果为空直接入符号栈
                    operStack.push(ch);
                }
            }else {
                int result = (ch - 48);
                if(Judge && !numStack.isEmpty()){
                    System.out.println("拼接");
                    int num = numStack.pop();
                    result = Integer.parseInt((num + "" + result));
                }
                //如果是数字 直接入数栈
                numStack.push(result);
            }
            //让index + 1 并判断是否扫描到expression 最后
            ++index;
            if(index >= expression.length()){
                break;
            }
        }

        //当表达式扫描完毕，就顺序的从 数栈和符号栈中pop出相应的数和符号，并运行
        while (true){
            //如果符号栈为空 则计算到最后的结果 数栈中只有一个数字[结果]
            if(!operStack.isEmpty()){
                num1 = numStack.pop();
                num2 = numStack.pop();
                oper = operStack.pop();
                res = numStack.cal(num1,num2,oper);
                //把运算的结果压入数栈
                numStack.push(res);
            }else{
                break;
            }
        }
        //将数栈的最后数 pop就是结果
        System.out.printf("表达式 %s = %d\n",expression,numStack.pop());
    }
}

/**
 * 定义一个ArrayStack2  表示栈  扩展功能
 */
class ArrayStack2{
    /**栈的大小 */
    private int maxSize;
    /**数组，数组模拟栈 数据就放在该数组中*/
    private int[] stack;
    /**栈顶 默认-1表示没有数据*/
    private int top = -1;

    /**
     * 构造器
     * @param maxSize 栈大小
     */
    public ArrayStack2(int maxSize){
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    /**
     * 判断栈是否满
     * @return true/false
     */
    public boolean inFull(){
        return top == (maxSize - 1);
    }

    /**
     * 判断栈是否为空
     * @return true/false
     */
    public boolean isEmpty(){
        return top == -1;
    }

    /**
     * 入栈 push
     * @param value 要入栈的数据
     */
    public void push(int value){
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
    public int pop(){
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
     * 增加一个方法 可以返回当前栈顶的值 但是不是真正的pop/不是弹出栈
     * @return
     */
    public int peek(){
        return stack[top];
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

    /**
     * 返回运算符的优先级 优先级是程序员来确定的，优先级使用数字表示
     * 数字越大 则优先级越高
     * @param oper 要判断优先级的符号
     * @return 返回优先级
     */
    public int priority(int oper){
        if(oper == '*' || oper == '/'){
            return 1;
        }else if(oper == '+' || oper == '-'){
            return 0;
        }else {
            //无效符号
            return -1;
        }
    }
    /**
     * 判断是不是一个运算符
     * @param val 要判断的字符
     * @return true/false
     */
    public boolean isOper(char val){
        return val == '*' || val == '/' ||
                val == '+' || val == '-';
    }
    /**
     * 计算方法
     * @param num1 右边的数
     * @param num2 左边的数 后弹出的数字
     * @param oper 运算符
     * @return 结果
     */
    public int cal(int num1,int num2,int oper){
        //res 用于存放计算的结果
        int res = 0;
        switch (oper){
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 + num1;
                break;
            default:
                break;
        }
        return res;
    }

}


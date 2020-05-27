package cn.cherry.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * @program: DataStructures
 * @description: 逆波兰计算器 & 中缀表达式转换为后缀表达式/逆波兰表达式
 * @author: Mr.Cherry
 * @create: 2019-12-22 18:25
 **/
public class PolandNotation {
    public static void main(String[] args) {
        //中缀表达式转换为逆波兰表达式
        //1. 1+((2+3)*4)-5 => 1 2 3 + 4 * + 5 -
        //2.先将中缀表达式从字符串转换为一个list
        //3.将得到的中缀表达式对应的list转化为，后缀表达式/逆波兰表达式对应的list
            //即ArrayList [1,+,(,(,2,+,3,),*,4,),-,5] => ArrayList [1,2,3,+,4,*,+,5,-]
        String expression = "1+((2+3)*4)-5";
        List<String> list = toInfixExpressionList(expression);
        System.out.println("转换前的中缀表达式 = " + list);

        List<String> list1 = parseSuffixExpressionList(list);
        System.out.println("转换后的逆波兰表达式 = " + list1);

        int calculate = calculate(list1);
        System.out.println("expression = " + calculate);

        /*//先定义一个逆波兰表达式
        //(3+4)*5-6  => 3 4 + 5 * 6 -
        //为了方便，逆波兰表达式的数字和符号使用空格隔开
        //String suffixExpression = "3 4 + 5 * 6 -";
        //4 * 5 - 8 + 60 + 8 / 2 => 4 5 * 8 - 60 + 8 2 / +
        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";
        //思路
        //1.先将 "3 4 + 5 * 6 -" => 放到ArrayList中
        //2.将ArrayList 传递给一个方法  遍历List配合栈完成计算
        List<String> list = getListString(suffixExpression);
        int result = calculate(list);
        System.out.println("result = " + result);*/
    }


    /**
     * 即ArrayList [1,+,(,(,2,+,3,),*,4,),-,5] => ArrayList [1,2,3,+,4,*,+,5,-]
     * 将得到的中缀表达式对应的List 转换为 后缀表达式对应的List
         1) 初始化两个栈：运算符栈s1和储存中间结果的栈s2；
         2) 从左至右扫描中缀表达式；
         3) 遇到操作数时，将其压s2；
         4) 遇到运算符时，比较其与s1栈顶运算符的优先级：
             1.如果s1为空，或栈顶运算符为左括号“(”，则直接将此运算符入栈；
             2.否则，若优先级比栈顶运算符的高，也将运算符压入s1；
             3.否则，将s1栈顶的运算符弹出并压入到s2中，再次转到(4.1)与s1中新的栈顶运算符相比较；
         5) 遇到括号时：
             1.如果是左括号“(”，则直接压入s1
             2.如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
         6) 重复步骤2至5，直到表达式的最右边
         7) 将s1中剩余的运算符依次弹出并压入s2
         8)  依次弹出s2中的元素并输出，结果的逆序即为中缀表达式对应的后缀表达式
     * @param list 中缀表达式list
     * @return 逆波兰表达式list
     */
    public static List<String> parseSuffixExpressionList(List<String> list){
        //定义两个栈  运算符栈(operatorStack)  中间结果栈(iResultStack)
        //运算符栈(operatorStack)
        Stack<String> operatorStack = new Stack<>();
        //中间结果栈(iResultStack)
        Stack<String> iResultStack = new Stack<>();
        List<String> operator = Arrays.asList("+","-","*","/");
        list.forEach(s -> {
            if(s.matches("\\d+")){
                //如果当前的是数字则直接压入 iResultStack
                iResultStack.push(s);
            }else if(s.charAt(0) == 40){
                //如果是左括号"("，则直接压入s1
                operatorStack.push(s);
            }else if(s.charAt(0) == 41){
                //如果是右括号")"，则依次弹出operatorStack栈顶的运算符，
                // 并压入iResultStack，直到遇到左括号为止，此时将这一对括号丢弃
                while (!"(".equals(operatorStack.peek())){
                    iResultStack.push(operatorStack.pop());
                }
                //将运算符栈中的 ( 弹出  消除左括号
                operatorStack.pop();
            }else if(operator.contains(s)){ //list.contains(Object o) 判断list中是否存在o所引用的值 返回true/false
                //当前s的优先级小于等于 运算符栈，栈顶的运算符时，将运算符栈 栈顶的运算符弹出并加入到中间结果栈中
                //再转到(4.1)与运算符栈中新的栈顶运算符相比较
                while (operatorStack.size() != 0 &&
                            priority(operatorStack.peek().charAt(0)) >= priority(s.codePointAt(0))){
                    iResultStack.push(operatorStack.pop());
                }
                //如果当前运算符栈不为空，并且优先级大于栈顶的值时 将s加入栈中
                operatorStack.push(s);
            }else{
                throw new RuntimeException("未知运算符 - ");
            }
        });
        //最后将运算符栈中所有的运算符依次 压入中间结果栈
        while (operatorStack.size() != 0){
            iResultStack.push(operatorStack.pop());
        }
        //因为Stack(栈)的体系结构和list一样 可以直接返回Stack类型的对象 Java会自动转换返回list
        return iResultStack;
    }

    /**
     * 中缀表达式转换成对应的list
     * @param s 中缀表达式
     * @return list
     */
    public static List<String> toInfixExpressionList(String s){
        //定义一个list存放中缀表达式对应的内容
        List<String> result = new ArrayList<>();
        //这是一个指针，用于遍历中缀表达式字符串
        int i = 0;
        //对多位数的拼接
        String str;
        //每遍历到一个字符 就放到c
        char c;
        do {
            //如果c是一个非字符 需要加入到result中
            if((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57){
                result.add("" + c);
                //后移
                ++i;
            }else{
                //如果是一个数字 需要考虑多位数
                //每次都先将str 置成 ""
                str = "";
                //循环条件 字符串没有遍历完成  是一个数字ASCII表  '0'[48] -> '9'[57]
                while (i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57){
                    //拼接
                    str += c;
                    ++i;
                }
                //存入list
                result.add(str);
            }

        }while (i < s.length());
        return result;
    }

    /**
     * 将一个逆波兰表达式 依次将数据和运算符放到ArrayList中
     * @param list 逆波兰表达式
     * @return 存放的list
     */
    public static List<String> getListString(String list){
        String[] split = list.split(" ");
        List<String> collect =
                Arrays.stream(split).collect(Collectors.toList());
        return collect;
    }

    /**
     * 完成对逆波兰表达式的运算
         1.从左至右扫描，将3和4压入堆栈；
         2.遇到+运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈；
         3.将5入栈；
         4.接下来是×运算符，因此弹出5和7，计算出7×5=35，将35入栈；
         5.将6入栈；
         6.最后是-运算符，计算出35-6的值，即29，由此得出最终结果
     */
    public static int calculate(List<String> list){
        //创建一个栈 只需要一个栈即可
        Stack<String> stack = new Stack<>();
        //遍历list
        list.forEach(s -> {
            //使用正则表达式来取出数字    \\d+ 匹配的是多位数
            if(s.matches("\\d+")){
                stack.push(s);
            }else{
                //pop 出两个数 并运算 再入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if(s.equals("+")){
                    res = num1 + num2;
                }else if(s.equals("-")){
                    res = num1 - num2;
                }else if(s.equals("*")){
                    res = num1 * num2;
                }else if(s.equals("/")){
                    res = num1 / num2;
                }else {
                    throw new RuntimeException("运算符错误");
                }
                //把计算结果res 入栈
                stack.push("" + res);
            }
        });
        //最后留在stack中的数据就是运算结果
        return Integer.parseInt(stack.pop());
    }


    /**
     * 返回运算符的优先级 优先级是程序员来确定的，优先级使用数字表示
     * 数字越大 则优先级越高
     * @param oper 要判断优先级的符号
     * @return 返回优先级
     */
    public static int priority(int oper){
        if(oper == '*' || oper == '/'){
            return 1;
        }else if(oper == '+' || oper == '-'){
            return 0;
        }else {
            //无效符号
            return -1;
        }
    }
}

package cn.cherry.queue;

import java.util.Scanner;

/**
 * @program: DataStructures
 * @description: ${description}
 * @author: Mr.Cherry
 * @create: 2019-12-01 21:19
 **/
public class CircleArrayQueue {
    public static void main(String[] args) {
        //创建一个队列
        CircleArray arrayQueue = new CircleArray(4);
        //接收用户输入
        char key = ' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        //输出菜单
        while (loop){
            System.out.println("s(show)：显示队列");
            System.out.println("e(exit)：退出程序");
            System.out.println("a(add)：添加数据到队列");
            System.out.println("g(get)：从队列取出数据");
            System.out.println("h(head：查看队列头的数据");
            //接收一个字符
            key = scanner.next().charAt(0);
            switch (key){
                case 's':
                    arrayQueue.showQueue();
                    break;
                case 'a':
                    System.out.println("输入一个数：");
                    int value = scanner.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g':
                    //取出数据
                    try{
                        int res = arrayQueue.getQueue();
                        System.out.printf("取出的数据是%d\n",res);
                    }catch (Exception e){
                        //打印异常
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    //查看队列头的数据
                    try {
                        int res = arrayQueue.headQueue();
                        System.out.printf("队列头的数据是%d\n",res);
                    }catch (Exception e){
                        //打印异常
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出 - ");
    }
}
/**
 * 使用数组模拟队列 ArrayQueue类
 */
class CircleArray{
    /**
     * maSize 表示数组/队列的最大容量
     * front 队列头
     * rear 队列尾
     * arr 该队列用于存放数据，模拟队列
     */
    private int maxSize;
    /**
     * front 指向队列的第一个元素 初始值是 0
     * rear 指向队列最后一个元素的后一个位置 因为需要空出一个空间做为约定 初始值是 0
     */
    private int front;
    private int rear;
    private int[] arr;

    /**
     * 创建队列的构造器
     */
    public CircleArray(int arrMaxSize){
        this.maxSize = arrMaxSize;
        arr = new int[arrMaxSize];
    }

    /**
     * 判断队列是否满
     */
    public boolean isFull(){
        return (rear + 1)%maxSize == front;
    }

    /**
     * 判断队列是否为空
     */
    public boolean isEmpty(){
        return rear == front;
    }

    /**
     * 添加数据到队列
     */
    public void addQueue(int n){
        //判断队列是否满
        if(isFull()) {
            System.out.println("队列满 不能再加入数据 - ");
            return;
        }
        //直接将数据加入
        arr[rear] = n;
        //将 rear后移 考虑取模
        int s= (rear + 1) % maxSize;
        rear = s;
    }

    /**
     * 获取队列的数据 出队列
     */
    public int getQueue(){
        //判断队列是否空
        if (isEmpty()){
            //抛出运行时异常
            throw new RuntimeException("队列空 不能取数据 - ");
        }
        int reslut = arr[front];
        front = (front + 1) % maxSize;
        return reslut;
    }

    /**
     * 显示队列的所有数据
     */
    public void showQueue(){
        //是否为空
        if(isEmpty()){
            System.out.println("队列为空 没有数据可取 - ");
            return;
        }
        int size = size();
        for(int i = front;i < front + size();++i){
            System.out.printf("arr[%d] = %d\n",i % maxSize,arr[i % maxSize]);
        }
    }

    /**
     * 求出当前队列有效数据的个数
     */
    public int size(){
        return (rear + maxSize - front) % maxSize;
    }

    /**
     * 显示队列的头数据 不是取出数据
     */
    public int headQueue(){
        //是否为空
        if(isEmpty()){
            throw new RuntimeException("队列为空 没有数据可取 - ");
        }
        return arr[front];
    }

}

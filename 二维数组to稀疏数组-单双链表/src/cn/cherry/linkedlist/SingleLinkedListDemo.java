package cn.cherry.linkedlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static cn.cherry.linkedlist.SingleLinkedList.*;

/**
 * @program: DataStructures
 * @description: 单链表
 * @author: Mr.Cherry
 * @create: 2019-12-02 13:46
 **/
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        //创建节点
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero4 = new HeroNode(4, "吴用", "智多星");
        HeroNode hero5 = new HeroNode(5, "林冲", "豹子头");

        //创建链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        /*加入
        singleLinkedList.add(hero1);
        singleLinkedList.add(hero4);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero3);*/

        //按照编号排序加入
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero5);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero2);
        //singleLinkedList.addByOrder(hero2);

        //System.out.println("修改前：");
        //singleLinkedList.list();
        //测试修改
        /*HeroNode newHeroNode = new HeroNode(2, "小卢", "玉麒麟 --");
        singleLinkedList.update(newHeroNode);
        System.out.println("修改后：");
        singleLinkedList.list();*/

        //测试删除
        /*singleLinkedList.delete(4);
        singleLinkedList.delete(3);
        System.out.println("删除后：");
        singleLinkedList.list();


        //单链表的有效数量
        System.out.println("单链表的有效数量："+getLength(singleLinkedList.getHead()));


        //查询单链表中的倒数第k个节点
        HeroNode lastIndexNode = findLastIndexNode(singleLinkedList.getHead(), 2);
        System.out.println("查询单链表中的倒数第2个节点："+lastIndexNode);
        System.out.println("反转后：");
        reversetList(singleLinkedList.getHead());
        singleLinkedList.list();
        //测试逆序打印单链表
        System.out.println("测试逆序打印单链表-链表本身不发生改变：");
        reversePrint(singleLinkedList.getHead());*/
        SingleLinkedList singleLinkedList1 = new SingleLinkedList();
        HeroNode hero3 = new HeroNode(3, "唐僧", "唐三藏");
        HeroNode hero6 = new HeroNode(6, "孙悟空", "齐天大圣");
        HeroNode hero9 = new HeroNode(9, "猪八戒", "天蓬元帅");
        HeroNode hero10 = new HeroNode(10, "沙和尚", "卷帘大将");
        singleLinkedList1.addByOrder(hero3);
        singleLinkedList1.addByOrder(hero6);
        singleLinkedList1.addByOrder(hero9);
        singleLinkedList1.addByOrder(hero10);

        System.out.println("合并的内容：");
        singleLinkedList.list();
        System.out.println();
        singleLinkedList1.list();

        System.out.println("合并之后：");
        ///HeroNode heroNode = mergeLinkedList(singleLinkedList.getHead(), singleLinkedList1.getHead());
        HeroNode heroNode = mergeLinkedList(singleLinkedList.getHead(), singleLinkedList1.getHead());
        SingleLinkedList singleLinkedList2 = new SingleLinkedList();
        singleLinkedList2.setHead(heroNode);
        singleLinkedList2.list();

        System.out.println("原始的数据：");
        singleLinkedList.list();
        System.out.println();
        singleLinkedList1.list();


    }
}
/**
 * 定义SingleLinkedList 管理链表
 */
class SingleLinkedList{
    /**
     * 初始化一个头节点 头节点不能发生改变 不存放具体的数据
     */
    private HeroNode head = new HeroNode(0,"","");

    public HeroNode getHead() {
        return head;
    }

    public void setHead(HeroNode head) {
        this.head = head;
    }

    /**
     * 添加节点到单向列表
     * 当不考虑编号顺序时
     * 1.找到当前链表的最后一个节点
     * 2.将最后这个节点的next 指向新的节点
     */
    public void add(HeroNode headNode){
        /**
         * 因为head节点不能动 因此我们需要一个辅助节点 temp
         */
        HeroNode temp = this.head;
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
        temp.next = headNode;
    }

    /**
     * 显示链表
     */
    public void list(){
        //判断链表是否为空
        if(head.next ==null){
            System.out.println("链表为空 - ");
            return;
        }
        //辅助变量
        HeroNode temp = this.head.next;
        while (true){
            //判断是否到链表最后
            if(temp == null){
                break;
            }
            //输出节点的信息
            System.out.println(temp);
            //将temp后移 负责是死循环
            temp = temp.next;
        }
    }

    /**
     * 第二种方式在添加英雄时 根据排名将英雄插入到指定位置
     * 如果这个排名已经存在 则添加失败 给出提示
     */
    public void addByOrder(HeroNode heroNode){
        /**
         * 因为头节点不能动，因此我们仍然通过一个辅助指针(变量) 来帮助找到添加的位置
         * 因为单链表 因为我们找的temp 是位于添加位置的前一个节点，否则插入不了
         */
        HeroNode temp = this.head;
        //flag标志添加的编号是否存在 默认为false
        boolean flag = false;
        while (true){
            if(temp.next == null){
                //说明temp已经在链表的最后
                break;
            }
            if(temp.next.no > heroNode.no){
                //位置找到 就在temp的后面插入
                break;
            }else if(temp.next.no == heroNode.no){
                //说明希望添加的heroNode的编号已然存在
                flag = true;
                break;
            }
            //后移
            temp = temp.next;
        }
        if(flag){
            System.out.printf("%d编号已经存在 不能添加  - \n",heroNode.no);
        }else {
            //添加到temp的后面
            heroNode.next = temp.next;
            temp.next = heroNode;
        }

    }

    /**
     * 修改节点的信息 根据no编号来修改 即no编号不能改
     * @param newHeroNode 根据no修改
     */
    public void update(HeroNode newHeroNode){
        //判断是否空
        if(head.next == null){
            System.out.println("链表为空 不能修改 - ");
            return;
        }
        //找到需要修改的节点 根据no编号
        //定义一个辅助变量
        HeroNode temp = this.head.next;
        //flag标志修改的编号是否存在 默认为false
        boolean flag = false;
        while (true){
            if (temp == null){
                //链表已经遍历完毕
                break;
            }
            if(temp.no == newHeroNode.no){
                //找到要修改的节点
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if(flag){
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        }else{
            System.out.printf("没有找到编号为：%d 的节点",newHeroNode.no);
        }
    }

    /**
     * 删除节点
     * @param no 节点编号
     */
    public void delete(int no){
        //判断是否空
        if(head.next == null){
            System.out.println("链表为空 不能删除 - ");
            return;
        }
        //定义一个辅助变量 作为带删除节点的前一个节点
        HeroNode temp = this.head;
        //flag标志是否找到待删除的编号 默认为false
        boolean flag = false;
        while (true){
            if (temp.next == null){
                //链表已经遍历完毕
                break;
            }
            if(temp.next.no == no){
                //找到待删除节点的前一个节点
                flag = true;
                break;
            }
            //后移 达到遍历
            temp = temp.next;
        }
        if(flag){
            //找到了 删除节点
            temp.next = temp.next.next;
        }else{
            System.out.printf("没有找到编号为：%d 的节点",no);
        }

    }


    /** =======================================================
     * 以下是面试题
     */
    /**
     * 1.获取到单链表的节点的个数(如果是带头节点的链表，不统计头节点)
     * @param head 链表的头节点
     * @return 返回的就是有效节点的个数
     */
    public static int getLength(HeroNode head){
        if(head.next == null){
            //空链表
            return 0;
        }
        int length = 0;
        //定义一个辅助的变量，没有统计头节点
        HeroNode cur = head.next;
        while (cur != null){
            ++length;
            //后移
            cur = cur.next;
        }
        return length;
    }

    /**
     * 2.查询单链表中的倒数第k个节点
     *  1.遍历链表得到链表的总长度
     *  2.得到链表总长度之后，从链表的第一个节点开始遍历(总长度-k)个 得到倒数第k个节点
     *  3.找到返回该节点 找不到返回null
     * @param head 头节点
     * @param index 倒数第几个节点
     * @return
     */
    public static HeroNode findLastIndexNode(HeroNode head,int index){
        //如果链表为空 返回null
        if(head.next == null){
            //没有找到
            return null;
        }
        //第一个遍历得到链表的长度(节点总个数)
        int size = getLength(head);
        //第二次遍历 size - index 位置 就是我们倒数的第k个节点
        //先做一个index的校验
        if(index <= 0 || index > size){
            return null;
        }
        //定义一个辅助变量
        HeroNode cur = head.next;
        for(int i = 0;i < size - index;++i){
            cur = cur.next;
        }
        return cur;
    }

    /**
     * 3.单链表的反转
     * @param head 头节点
     */
    public static void reversetList(HeroNode head){
        //如果链表为空 或者只有一个节点 无需反转 直接返回
        if(head.next == null || head.next.next == null){
            return;
        }
        //定义一个辅助的指针(变量) 用来遍历原来的链表
        HeroNode cur = head.next;
        //指向当前节点[cur] 的下一个节点
        HeroNode next = null;
        HeroNode reverseHead = new HeroNode(0,"","");
        //遍历原来的链表 每遍历一个节点 就将其取出 并放到新的链表reverseHead 的最前端
        while (cur != null){
            //先暂时保存当前节点的下一个节点
            next = cur.next;
            //将cur的下一个节点指向新的链表的最前端
            cur.next = reverseHead.next;
            //将cur链接到新的链表上
            reverseHead.next = cur;
            //后移
            cur = next;
        }
        //将head.next 指向reverseHead.next 实现单链表反转
        head.next = reverseHead.next;
    }

    /**
     * 1.从尾到头打印单链表
     * 2.方式1：先将单链表进行反转操作，然后再遍历即可，这样的做的问题是会破坏原来的单链表的结构，不建议
     * 3.方式2：可以利用栈这个数据结构，将各个节点压入到栈中，然后利用栈的先进后出的特点，就实现了逆序打印的效果.
     * 使用方法二
     * @param head 链表头
     */
    public static void reversePrint(HeroNode head){
        if(head.next == null){
            //空链表不打印
            return;
        }
        //创建一个栈 将各个节点压入栈
        Stack<HeroNode> stack = new Stack<>();
        HeroNode cur = head.next;
        //将链表数据压入栈中
        while (cur != null){
            stack.push(cur);
            //后移
            cur = cur.next;
        }
        while (stack.size() > 0){
            System.out.println(stack.pop());
        }
    }


    /**
     * 合并两个有序的单链表 合并之后的链表依然有序  这个方法会毁掉第一个链表 如果不想毁掉可以使用深克隆
     */
    public static HeroNode mergeLinkedList(HeroNode head1,HeroNode head2){
        if(head1.next == null && head2.next == null){
            return null;
        }
        if(head1.next != null && head2.next != null){
            //flag标志添加的编号是否存在 默认为false
            boolean flag = false;
            //最后返回的值 现在会毁掉两个合并的链表 如果想要保留两个链表可以使用 深克隆
            //定义一个辅助的指针(变量) 用来遍历第一个链表
            HeroNode temp = head1.next;
            //定义一个辅助的指针(变量) 用来遍历第二个链表
            HeroNode cur = head2.next;
            while (cur != null){
                HeroNode next = cur.next;
                while (true){
                    if(temp.next == null){
                        break;
                    }
                    if(temp.next.no > cur.no){
                        //位置找到 就在temp的后面插入
                        break;
                    }else if(temp.next.no == cur.no){
                        //说明希望添加的heroNode的编号已然存在
                        flag = true;
                        break;
                    }
                    temp = temp.next;
                }
                if(flag){
                    System.out.printf("%d编号已经存在 不能添加  - \n",cur.no);
                }else{
                    cur.next = temp.next;
                    temp.next = cur;
                }
                cur = next;
            }
            return head1;
        }else{
            if(head1.next == null){
                return head2;
            }else{
                return head1;
            }
        }
    }

/*

    public static HeroNode optimizationMergeLinkedList(HeroNode head1,HeroNode head2){
        if(head1.next == null && head2.next == null){
            return null;
        }
        if(head1.next != null && head2.next != null){
            List<HeroNode> list = new ArrayList<>();
            HeroNode reslut = new HeroNode(0,"","");
            //定义一个辅助的指针(变量) 用来遍历第一个链表
            HeroNode temp = head1;
            //定义一个辅助的指针(变量) 用来遍历第二个链表
            HeroNode cur = head2;
            while (temp.next != null || cur.next != null){
                if(temp.next != null){
                    if(temp.next.no < cur.next.no){
                        list.add(temp.next);
                        temp = temp.next;
                    }else{
                        list.add(cur.next);
                        cur = cur.next;
                    }
                }else{
                    list.add(cur.next);
                    cur = cur.next;
                }
            }
            for(int i = 0;i < list.size();++i){
                if(i+1 < list.size()){
                    list.get(i).next = list.get(i+1);
                }else{
                    list.get(i).next = null;
                }
            }
            reslut.next = list.get(0);
            return reslut;
        }else{
            if(head1.next == null){
                return head2;
            }else{
                return head1;
            }
        }
    }
*/

}


/**
 * 定义HeroNode 每一个HeroNode对象就是一个节点
 */
class HeroNode{
    public int no;
    public String name;
    public String nickname;
    /**
     * 指向下一个节点
     */
    public HeroNode next;

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}

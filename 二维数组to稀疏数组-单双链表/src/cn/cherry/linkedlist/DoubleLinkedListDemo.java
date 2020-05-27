package cn.cherry.linkedlist;

/**
 * @program: DataStructures
 * @description: 双向链表/双链表
 * @author: Mr.Cherry
 * @create: 2019-12-19 19:14
 **/
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        //测试双向链表
        //创建节点
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");

        //创建链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
       /* //添加数据
        doubleLinkedList.add(hero1);
        doubleLinkedList.add(hero2);
        doubleLinkedList.add(hero3);
        doubleLinkedList.add(hero4);
        System.out.println("修改前链表");
        doubleLinkedList.list();
        System.out.println("修改后链表");
        HeroNode2 hero5 = new HeroNode2(2, "关羽", "云长");
        doubleLinkedList.update(hero5);
        doubleLinkedList.list();
        System.out.println("删除后链表");
        doubleLinkedList.delete(3);
        doubleLinkedList.list();*/

        /**
         * 自动排序 按no
         */
        doubleLinkedList.addByNumberingSort(hero1);
        doubleLinkedList.addByNumberingSort(hero4);
        doubleLinkedList.addByNumberingSort(hero2);
        doubleLinkedList.addByNumberingSort(hero3);
        doubleLinkedList.list();
    }
}
class DoubleLinkedList{

    /**
     * 初始化一个头节点 头节点不能发生改变 不存放具体的数据
     */
    private HeroNode2 head = new HeroNode2(0,"","");

    /**
     * 获取头节点
     * @return 头节点对象
     */
    public HeroNode2 getHead() {
        return head;
    }

    /**
     * 添加节点到双向链表
     * 当不考虑编号顺序时
     * 1.找到当前链表的最后一个节点
     * 2.将最后这个节点的next 指向新的节点
     * 3.将新节点的pre指向 当前的最后一个节点
     */
    public void add(HeroNode2 headNode){
        /**
         * 因为head节点不能动 因此我们需要一个辅助节点 temp
         */
        HeroNode2 temp = this.head;
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
        //将新节点的pre指向前一个节点
        headNode.pre = temp;
    }

    /**
     * 显示链表  和单向链表相同
     */
    public void list(){
        //判断链表是否为空
        if(head.next ==null){
            System.out.println("链表为空 - ");
            return;
        }
        //辅助变量
        HeroNode2 temp = this.head.next;
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
     * 修改节点的信息 根据no编号来修改 即no编号不能改  no指向当前要修改的节点
     * @param newHeroNode 根据no修改
     */
    public void update(HeroNode2 newHeroNode){
        //判断是否空
        if(head.next == null){
            System.out.println("链表为空 不能修改 - ");
            return;
        }
        //找到需要修改的节点 根据no编号
        //定义一个辅助变量
        HeroNode2 temp = head.next;
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
        HeroNode2 temp = this.head.next;
        //flag标志是否找到待删除的编号 默认为false
        boolean flag = false;
        while (true){
            if (temp == null){
                //链表已经遍历完毕
                break;
            }
            if(temp.no == no){
                //找到待删除节点的前一个节点
                flag = true;
                break;
            }
            //后移 达到遍历
            temp = temp.next;
        }
        if(flag){
            //找到了 删除节点
            temp.pre.next = temp.next;
            //需要加一个判断，判断当前节点是否是最后一个节点
            //如果不是最后一个节点，则执行if语句，避免空指针异常
            if(temp.next != null){
                temp.next.pre = temp.pre;
            }
        }else{
            System.out.printf("没有找到编号为：%d 的节点",no);
        }

    }

    /**
     * 第二种方式在添加英雄时 根据排名将英雄插入到指定位置
     * 如果这个排名已经存在 则添加失败 给出提示
     * 排序
     */
    public void addByNumberingSort(HeroNode2 heroNode){
        /**
         * 因为头节点不能动，因此我们仍然通过一个辅助指针(变量) 来帮助找到添加的位置
         * 因为单链表 因为我们找的temp 是位于添加位置的前一个节点，否则插入不了
         */
        HeroNode2 temp = this.head;
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
            //将新节点的前一个节点设置为当前指针所指的节点
            heroNode.pre = temp;
            //将当前节点的下一个节点设置为，当前指针所指节点的下一个节点
            heroNode.next = temp.next;
            //判断当前节点是否是最后一个节点，如果是那么就不执行if的语句，否则会出现空指针异常
            if(temp.next != null){
                //将当前指针所指节点的下一个节点的前一个节点设置为，当前添加的新节点
                temp.next.pre = heroNode;
            }
            //将当前指针所指节点的下一个节点设置为，当前添加的新节点
            temp.next = heroNode;
        }

    }

}

/**
 * 定义HeroNode2 每一个HeroNode2对象就是一个节点
 */
class HeroNode2{
    public int no;
    public String name;
    public String nickname;
    /**
     * 指向下一个节点，默认为null
     */
    public HeroNode2 next;
    /**
     * 指向前一个节点，默认为null
     */
    public HeroNode2 pre;

    /**
     * 构造器
     * @param no id
     * @param name 名称
     * @param nickname 别名
     */
    public HeroNode2(int no, String name, String nickname) {
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

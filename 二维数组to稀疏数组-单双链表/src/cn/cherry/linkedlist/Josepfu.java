package cn.cherry.linkedlist;

/**
 * @program: DataStructures
 * @description: 单向环形链表 解决约瑟夫问题
 * @author: Mr.Cherry
 * @create: 2019-12-20 09:03
 **/
public class Josepfu {
    public static void main(String[] args) {
        OneWayCircularLinkedList linkedList = new OneWayCircularLinkedList();
        linkedList.addBoy(5);
        //linkedList.showLinkedList();
        //Integer size = linkedList.linkedListLength();
       // System.out.println("size = " + size);
        linkedList.listByRule(1,2);
    }
}
/**
 * 创建一个环形的单向链表
 */
class OneWayCircularLinkedList{
    /**创建一个first节点，表示第一个节点，当前没有编号*/
    private Boy first = null;

    /**
     * 添加：小孩节点，构建成一个环形的链表
     * @param number 小孩数量
     */
    public void addBoy(Integer number){
        //判断小孩数量 不能小于一
        if(number < 1){
            System.out.println("数量不合法");
            return;
        }
        //创建一个辅助节点表示当前链表的最后一个节点，以便遍历
        Boy curBoy = null;
        for(int i = 1;i <= number;++i){
            //新节点
            Boy boy = new Boy(i);
            //判断当前是否是第一个小孩，为first赋值
            if(i == 1){
                first = boy;
                //构成环
                first.setNext(first);
                //让curBoy指向第一个小孩
                curBoy = first;
            }else{
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;
            }
        }
    }

    /**
     * 显示：链表的所有小孩编号
     */
    public void showLinkedList(){
        //判断小孩数量 不能小于一
        if(first == null){
            System.out.println("链表为空不能遍历");
            return;
        }
        //创建一个辅助节点表示当前链表的最后一个节点，以便遍历，因为first不能动
        Boy curBoy = first;
        while (true){
            System.out.printf("小孩编号：%d \n",curBoy.getNo());
            //条件成立说明链表已经遍历完毕
            if(curBoy.getNext() == first){
                return;
            }
            //让辅助节点后移
            curBoy = curBoy.getNext();
        }
    }

    /**
     * 根据规则报数出列   自写
     * @param start 开始位置 (1 <= start <= n)
     * @param interval 间隔数量
     */
    public void listByRule(Integer start,Integer interval){
        //链表总长度
        Integer size = linkedListLength();
        //判断链表是否存在 && start是否合法
        if(size != 0 && !(start >= 1) && !(start <= size)){
            System.out.println("链表为空 | start值不合法");
            return;
        }
        //创建一个辅助节点表示当前链表的第一个节点，以便遍历，因为first不能动
        Boy curBoy = first;
        //创建一个辅助节点，表示每次报数的起始节点
        Boy startBoy = null;
        //找到起始节点
        for (int i = 1;i <= start;++i){
            //找到起始节点 将当前节点赋值给 startBoy
            startBoy = curBoy;
            //指针后移
            curBoy = curBoy.getNext();
        }
        while (true){
            //已遍历到最后一个节点
            if(startBoy == startBoy.getNext()){
                System.out.printf("最后 编号为：%d 的小孩出列 \n",startBoy.getNo());
                return;
            }
            for (int i = 1;i <= interval;++i){
                if((i+1) == interval){
                    //拿到待出列的节点
                    Boy aims = startBoy.getNext();
                    //将待出列节点的前一个节点的下一个节点，设置为待出列节点的下一个节点
                    startBoy.setNext(aims.getNext());
                    //将待出列节点的下一个节点设置为null 达到断开链表链接
                    aims.setNext(null);
                    System.out.printf("编号为：%d 的小孩出列 \n",aims.getNo());
                }else{
                    //当前节点的下一个节点不是要出列的节点时，将起始节点后移
                    startBoy = startBoy.getNext();
                }
            }
        }
    }

    /**
     * 根据规则报数出列   教程
     * @param start 开始位置 (1 <= start <= n)
     * @param interval 间隔数量
     */
    public void listByRules(Integer start,Integer interval){
        //链表总长度
        Integer size = linkedListLength();
        //判断链表是否存在 && start是否合法
        if(first == null || (start < 1) || (start > size)){
            System.out.println("链表为空 | start值不合法");
            return;
        }
        //创建一个辅助节点表示当前链表的最后一个节点
        Boy helper = first;
        while (true){
            if(helper.getNext() == first){
                break;
            }
            helper = helper.getNext();
        }
        //找到起始节点  先让first 和 helper移动 start - 1 次
        for (int i = 0;i < start - 1;++i){
            //找到起始节点 将当前节点赋值给 startBoy
            first = first.getNext();
            //指针后移
            helper = helper.getNext();
        }
        //当小孩报数时，让first 和 helper 指针同时 的移动 interval  - 1 次
        while (true){
            if(helper == first){
                //说明圈中只有一个节点
                break;
            }
            //让first 和 helper 指针同时移动 interval - 1
            for (int i = 0;i < interval - 1;++i){
                first = first.getNext();
                helper = helper.getNext();
            }
            //这时first指向的节点，就是要出圈的小孩节点
            System.out.printf("小孩%d出圈\n",first.getNo());
            //这时将first指向的小孩节点出圈
            first = first.getNext();
            //将helper 指向出圈节点的下一个节点
            helper.setNext(first);
        }
        System.out.printf("最后留在圈中的小孩编号%d \n",helper.getNo());
    }


    /**
     * 获取当前链表的总长度
     * @return 长度
     */
    public Integer linkedListLength(){
        if(first == null){
            return 0;
        }
        //创建一个辅助节点表示当前链表的最后一个节点，以便遍历，因为first不能动
        Boy curBoy = first;
        //总长度
        Integer result = 0;
        while (true){
            ++result;
            //条件成立说明链表已经遍历完毕
            if(curBoy.getNext() == first){
                return result;
            }
            //让辅助节点后移
            curBoy = curBoy.getNext();
        }
    }

}


/**
 * 创建一个Boy类 表示小孩节点
 */
class Boy{
    /**
     * 编号
     */
    private Integer no;
    /**
     * 指向下一个节点，默认为null
     */
    private Boy next;

    public Boy(Integer no) {
        this.no = no;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
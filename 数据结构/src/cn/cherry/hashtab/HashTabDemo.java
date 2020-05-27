package cn.cherry.hashtab;

import java.util.Scanner;

/**
 * @program: DataStructures
 * @description: 哈希表
 * @author: Mr.Cherry
 * @create: 2019-12-26 12:35
 **/
public class HashTabDemo {
    /**
     google公司的一个上机题:
     有一个公司,当有新的员工来报道时,要求将该员工的信息加入  (id,性别,年龄,名字,住址..),
     当输入该员工的id时,要求查找到该员工的  所有信息.
     要求:
        1.不使用数据库,,速度越快越好=>哈希表(散列)
        2.添加时，保证按照id从低到高插入  [课后思考：如果id不是从低到高插入，但要求各条链表仍是从低到高，怎么解决?]
        3.使用链表来实现哈希表, 该链表不带表头[即: 链表的第一个结点就存放雇员信息]
     */
    public static void main(String[] args) {
        HashTab hashTab = new HashTab(7);
        String key = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("add：添加雇员");
        System.out.println("list：显示雇员");
        System.out.println("find：查找雇员");
        System.out.println("del：删除雇员");
        System.out.println("up：删除雇员");
        System.out.println("exit：退出系统");
        while (true){
            System.out.print("请选择你的操作：");
            key = scanner.next();
            switch (key){
                case "add":
                    System.out.print("请输入id：");
                    int id = scanner.nextInt();
                    System.out.print("请输入名称：");
                    String name = scanner.next();
                    Emp emp = new Emp(id, name);
                    hashTab.add(emp);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.print("请输入查询的雇员id：");
                    id = scanner.nextInt();
                    hashTab.findById(id);
                    break;
                case "del":
                    System.out.print("请输入要删除雇员的id：");
                    id = scanner.nextInt();
                    hashTab.delById(id);
                    break;
                case "up":
                    System.out.print("请输入要修改雇员的id：");
                    id = scanner.nextInt();
                    System.out.print("请输入修改名称：");
                    name = scanner.next();
                    emp = new Emp(id, name);
                    hashTab.updateById(emp);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;
                    default:
                        break;
            }
        }
    }
}


//哈希表类
class HashTab{
    private EmpLinkedList[] empLinkedLists;
    private int size;

    public HashTab(int size) {
        this.size = size;
        //初始化empLinkedLists
        empLinkedLists = new EmpLinkedList[size];
        //需要分别初始化每个链表
        for (int i = 0; i < size; i++) {
            empLinkedLists[i] = new EmpLinkedList();
        }
    }


    public void add(Emp emp){
        int i = hashFun(emp.id);
        empLinkedLists[i].add(emp);
    }

    public void list(){
        for (int i = 0; i < size; i++) {
            empLinkedLists[i].list(i);
        }
    }

    public void findById(int id){
        int index = hashFun(id);
        Emp emp = empLinkedLists[index].findById(id);
        if (emp != null){
            System.out.printf("要查找的雇员在第 %d 条链表中 id = %d\n",index+1,id);
        }else {
            System.out.println("未找到该雇员");
        }
    }

    public void delById(int id){
        int index = hashFun(id);
        boolean b = empLinkedLists[index].delById(id);
        if (b){
            System.out.printf("已从第 %d 条链表中删除id为：%d的雇员 - 删除成功\n",index+1,id);
        }else {
            System.out.printf("找到不到id为：%d的雇员 - 删除失败\n",id);
        }
    }

    public void updateById(Emp emp){
        int index = hashFun(emp.id);
        boolean b = empLinkedLists[index].updateById(emp);
        if (b){
            System.out.printf("已从第 %d 条链表中修改了id为：%d的雇员 - 修改成功\n",index+1,emp.id);
        }else {
            System.out.printf("找到不到id为：%d的雇员 - 修改失败\n",emp.id);
        }
    }
    /**
     * 编写一个散列函数  使用一个简单的取模法
     * @param id
     * @return
     */
    public int hashFun(int id){
        return id % size;
    }
}

/**
 * 创建 EmpLinkedList 表示链表
 */
class EmpLinkedList{
    /**
     * 头指针 指向第一个Emp，因此我们这个链表的head是直接指向第一个Emp 默认为null
     */
    private Emp head;

    /**
     * 新增雇员
     * @param emp
     */
    public void add(Emp emp){
        //判断当前链表是否为空
        if (head == null) {
            head = emp;
            return;
        }
        //使用辅助指针
        Emp cerEmp = head;
        while (true){
            if (cerEmp.id == emp.id){
                System.out.println("该雇员id已经存在请切换 - ");
                return;
            }
            if (cerEmp.id < emp.id && (cerEmp.next == null || cerEmp.next.id > emp.id)){
                emp.next = cerEmp.next;
                cerEmp.next = emp;
                return;
            }else if(cerEmp.id > emp.id){
                emp.next = cerEmp;
                head = emp;
                return;
            }
            cerEmp = cerEmp.next;
        }
    }

    /**
     * 查看所有雇员
     * @param id
     */
    public void list(int id){
        if (head == null) {
            System.out.printf("第 %d 条链表为空\n",id+1);
            return;
        }
        //使用辅助指针
        Emp cerEmp = head;
        System.out.printf("第 %d 条链表内容为：",id+1);
        while (true){
            System.out.printf(" -> id = %d name = %s ",cerEmp.id,cerEmp.name);
            if(cerEmp.next == null){
                break;
            }
            cerEmp = cerEmp.next;
        }
        System.out.println();
    }

    /**
     * 根据id查询某个雇员
     * @param id
     * @return
     */
    public Emp findById(int id){
        if (head == null) {
            System.out.println("链表为空 - ");
            return null;
        }
        Emp temp = head;
        while (true){
            if(temp.id == id){
                return temp;
            }
            if (temp.next == null){
                return null;
            }
            temp = temp.next;
        }
    }


    public boolean delById(int id){
        if (head == null) {
            return false;
        }
        if(head.id == id){
            head = head.next;
            return true;
        }
        Emp cerEmp = head;
        while (true){
            if(cerEmp.next != null && cerEmp.next.id == id){
                //找到待删除节点的前一个节点
                cerEmp.next = cerEmp.next.next;
                return true;
            }else {
                return false;
            }
        }
    }


    public boolean updateById(Emp emp){
        if (head == null) {
            return false;
        }
        Emp cerEmp = head;
        while (true){
            if (cerEmp.id == emp.id) {
                cerEmp.name = emp.name;
                return true;
            }
            if(cerEmp.next == null){
                return false;
            }
            cerEmp = cerEmp.next;
        }
    }
}


/**
 * 表示一个雇员
 */
class Emp{
    public int id;
    public String name;
    /**
     * 表示下一个节点  默认为空
     */
    public Emp next;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
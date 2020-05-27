package cn.cherry.huffmancode;

import java.io.*;
import java.util.*;

/**
 * @program: DataStructures
 * @description: 赫夫曼编码 数据压缩
 * @author: Mr.Cherry
 * @create: 2019-12-29 17:24
 **/
public class HuffmanCode {
    public static void main(String[] args) {
        /*String content = "i like like like java do you like a java";
        byte[] contentBytes = content.getBytes();
        System.out.println("长度 = " + contentBytes.length);
        byte[] huffmanBytes = huffmanZip(contentBytes);
        System.out.println(huffmanBytes);
        byte[] decode = decode(huffmanCodes, huffmanBytes);
        System.out.println("decode = " + new String(decode));*/
        /*String srcFile = "K://632051.png";
        String dstFile = "K://632051.cherry";
        zipFile(srcFile,dstFile);
        System.out.println("压缩成功 -");*/
        String zipFile = "K://632051.cherry";
        String dstFile = "K://632051(2).png";
        unZipFile(zipFile,dstFile);
        System.out.println("解压成功 - ");
    }


    /**
     * 解压文件
     * @param zipFile 压缩文件所在路径
     * @param dstFile 解压后文件存放的路径
     */
    public static void unZipFile(String zipFile,String dstFile){

        //定义文件的输入流
        InputStream is = null;
        //定义一个对象输入流
        ObjectInputStream ois = null;
        //定义文件的输出流
        OutputStream os = null;
        try {
            //创建文件输入流
            is = new FileInputStream(zipFile);
            //创建一个和 is关联的对象输入流
            ois = new ObjectInputStream(is);
            //读取byte数组 huffmanBytes
            byte[] huffmanBytes = (byte[]) ois.readObject();
            //读取哈夫曼编码表
            Map<Byte,String> huffmanCodes = (Map<Byte, String>)ois.readObject();

            //解码
            byte[] decode = decode(huffmanCodes, huffmanBytes);
            //创建输出流
            os = new FileOutputStream(dstFile);
            //写数据到 dstFile文件
            os.write(decode);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                os.close();
                ois.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 文件压缩
     * @param srcFile 原文件的位置
     * @param dstFile 压缩后文件存放的位置
     */
    public static void zipFile(String srcFile,String dstFile){
        //声明输出流
        OutputStream os = null;
        //声明对象输出流
        ObjectOutputStream oos = null;
        //声明文件的输入流
        FileInputStream is = null;
        try {
            //创建文件的输入流
            is = new FileInputStream(srcFile);
            //创建一个和源文件大小一样的byte[]  用于存放源文件字符内容
            byte[] b = new byte[is.available()];
            //读取文件
            is.read(b);
            //直接对源文件进行压缩
            byte[] huffmanBytes = huffmanZip(b);
            //创建文件的输出流 存放压缩文件
            os = new FileOutputStream(dstFile);
            //创建一个和文件输出流关联的ObjectOutputStream
            oos = new ObjectOutputStream(os);
            //把 赫夫曼编码后的字节数组写入压缩文件  以对象的方式输出
            oos.writeObject(huffmanBytes);
            //以对象流的方式写入 赫夫曼编码 为了恢复源文件时使用
            //注意一定要把赫夫曼编码写入压缩文件
            oos.writeObject(huffmanCodes);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                is.close();
                os.close();
                oos.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }



    /**
     * 压缩数据的解码
     * @param huffmanCodes 赫夫曼编码表 map
     * @param huffmanBytes 赫夫曼编码得到的字节数组
     * @return 就是原来的字符串对应的数组
     */
    private static byte[] decode(Map<Byte,String> huffmanCodes,byte[] huffmanBytes){
        //1.先得到huffmanBytes对应的二进制的字符串 形式： 101010001011111....
        StringBuilder stringBuilder = new StringBuilder();
        //将byte数组转成二进制的字符串
        for (int i = 0; i < huffmanBytes.length; i++) {
            byte b = huffmanBytes[i];
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(!flag,b));
        }
        //System.out.println(stringBuilder.toString());

        //把字符串安装指定的赫夫曼编码进行解码
        //把赫夫曼编码表进行调换 因为反向查询 a->100 100->a
        Map<String,Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(),entry.getKey());
        }
        //System.out.println("map = " + map);

        //创建一个集合存放byte
        List<Byte> list = new ArrayList<>();
        //i 是一个索引 用于扫描stringBuilder
        for (int i = 0; i < stringBuilder.length(); ) {
            //小的计数器
            int count = 1;
            boolean flag = true;
            Byte b = null;
            //如果为true直接跳出for 说明已经到达stringBuilder 的最后一个再继续循环会下标越界
            boolean flag2 = false;
            while (flag){
                //递增的取出key
                //i 不动 让count移动 直到匹配到一个字符
                String key = stringBuilder.substring(i, i+count);
                b = map.get(key);
                if(b == null){
                    //说明没有匹配到
                    ++count;
                }else {
                    //匹配到
                    flag = false;
                }
                //判断是否到最后一位
                if((i+count) >= (stringBuilder.length()-1)){
                    flag2 = true;
                    break;
                }
            }
            if(flag2){
                break;
            }
            list.add(b);
            //i 直接移动到count
            i += count;
        }
        //当for循环结束后 list中就存放了原始的字符  "i like like like java do you like a java"
        byte[] b = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }
        return b;
    }
    /**
     * 将一个byte 转成一个二进制的字符串
     * @param flag 标志是否需要补高位如果是true 表示需要补高位 如果是false表示不补 如果是最后一个字节无需补高位
     * @param b 传入的byte
     * @return 是该b对应的二进制的字符串(返回的是补码)
     */
    private static String byteToBitString(boolean flag,byte b){
        //使用变量保存b
        int temp = b;
        //如果是正数我们还存在补高位
        if(flag){
            //按位与 256 1 0000 0000 | 0000 0001 => 1 0000 0001
            temp |= 256;
        }
        //返回的是temp对应的二进制的补码
        String str = Integer.toBinaryString(temp);
        if(flag){
            return str.substring(str.length() - 8);
        }else {
            return str;
        }
    }


    /**
     * 封装调用
     * @param contentBytes 原始的字符串对应的字节数组
     * @return 赫夫曼编码压缩后的byte[]
     */
    private static byte[] huffmanZip(byte[] contentBytes){
        //构建赫夫曼树的Node 节点
        List<Node> nodes = getNodes(contentBytes);
        //创建一个赫夫曼树
        Node huffmanTree = createHuffmanTree(nodes);
        //构建赫夫曼编码表
        Map<Byte, String> huffmanCodes = getCodes1(huffmanTree);
        //获得赫夫曼编码压缩后的byte[]
        byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodes);
        return huffmanCodeBytes;
    }

    /**
     * 将字符串对应的byte[] 数组，通过生成的赫夫曼编码表 返回一个赫夫曼编码压缩后的byte[]
     * @param bytes 原始字符串对应的byte[]
     * @param huffmanCodes 生成的赫夫曼编码map
     * @return 返回赫夫曼编码处理后的byte[]
     * 举例：
     * String content = "i like like like java do you like a java";  =>  byte[] contentBytes = content.getBytes();
     * 返回的是字符串 code = "101010001011111111001000101111111100100010111111110010010100110111000111000001101110100011110010100010111111110011000100101001101110"
     * 对应的byte[] huffmanCodeBytes ，即8位的code对应一个byte 放入到huffmanCodeBytes中
     * 符号位： 1代表负数 0代表正数
     * 补码转反码：符号位(最左边的)不变，补码-1
     * 反码转原码：符号位不变，其它取反
     * huffmanCodeBytes[0] = 10101000(补码) => 转成一个byte
     *  推导[10101000(补码) => 转为反码 10101000 - 1 = 10100111(反码) => 转成原码 => 11011000(原码) 原码转换为10进制(原码计算符号位不参与运算)  就是一个byte]
     */
    private static byte[] zip(byte[] bytes,Map<Byte,String> huffmanCodes){
        //1.利用huffmanCodes 将bytes转成 赫夫曼编码对应的字符串
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(huffmanCodes.get(b));
        }
        //将"101010001011......" 转成byte[]
        //统计返回byte[] huffmanCodeBytes长度
        //简单写法  int len = (builder.length() + 7) / 8;
        int len;
        if (builder.length() % 8 == 0){
            len = builder.length() / 8;
        }else {
            len = builder.length() / 8 + 1;
        }
        //存储压缩后的byte数组
        byte[] huffmanCodeBytes = new byte[len];
        //第几个byte
        int index = 0;
        //因为是每8位对应一个byte 所以步长为8
        for (int i = 0; i < builder.length(); i+=8) {
            String strByte;
            if((i+8) > builder.length()){
                //不够8位
                strByte = builder.substring(i);
            }else {
                strByte = builder.substring(i,i+8);
            }
            //将strByte 转成一个byte 放入到huffmanCodeBytes中
            huffmanCodeBytes[index] = (byte) Integer.parseInt(strByte,2);
            ++index;
        }
        return huffmanCodeBytes;
    }

    //生成赫夫曼树对应的赫夫曼编码
    /**
     * 1.将赫夫曼编码表放在 Map<Byte,String>中
     */
    private static Map<Byte,String> huffmanCodes = new HashMap<>();
    /**
     * 2.在生成赫夫曼编码表时 需要去拼接路径 定于一个StringBuilder 存储某个叶子节点的路径
     */
    private static StringBuilder stringBuilder = new StringBuilder();

    /**
     * 自己写的
     * 将传入的node节点的所有右子节点的赫夫曼编码的到，并放入到huffmanCodes集合
     * 路径： 左子节点是 0  右子节点是 1
     * @param node 当前节点
     */
    private static void getCodes(Node node){
        //如果当前节点是空 则直接返回
        if (node == null) {
            return;
        }
        //如果node.date == null 则这个节点是非叶子节点 则进行递归遍历
        if (node.data == null) {
            //向左递归
            if (node.left != null) {
                stringBuilder.append("0");
                getCodes(node.left);
            }
            //向右递归
            if(node.right != null){
                stringBuilder.append("1");
                getCodes(node.right);
            }
        }else {
            //当前节点是一个叶子节点 放入map
            huffmanCodes.put(node.data,stringBuilder.toString());
        }
        //每次递归结束 并且stringBuilder的长度大于等于1 就移除stringBuilder最后的一个值
        if(stringBuilder.length() >= 1){
            stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
        }
    }


    /**
     * 重载方法 教程里的
     */
    private static Map<Byte,String> getCodes1(Node root){
        if(root == null){
            return null;
        }
        getCodes(root,"",stringBuilder);
        //处理root的左子树
       /* getCodes(root.left,"0",stringBuilder);
        getCodes(root.right,"1",stringBuilder);*/
        return huffmanCodes;
    }
    /**
     * 教程里的
     * 将传入的node节点的所有右子节点的赫夫曼编码的到，并放入到huffmanCodes集合
     * @param node 当前节点
     * @param code 路径：左子节点是 0 右子节点是 1
     * @param stringBuilder 用于拼接路径
     */
    private static void getCodes(Node node,String code,StringBuilder stringBuilder){
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        //将code加入到stringBuilder2 中
        stringBuilder2.append(code);
        if(node != null){
            //如果node == null 不处理
            //判断当前node是叶子节点还是非叶子节点
            if (node.data == null) {
                //非叶子节点
                //递归处理
                //向左递归
                getCodes(node.left,"0",stringBuilder2);
                //向右递归
                getCodes(node.right,"1",stringBuilder2);
            }else {
                //说明这是一个叶子节点
                //就表示找到某个叶子节点的最后
                huffmanCodes.put(node.data,stringBuilder2.toString());
            }
        }
    }

    /**
     * 构建赫夫曼树的Node 节点
     * @param bytes 接收字节数组
     * @return 返回的就是Node类型的集合  List形式[Node[date=97 ,weight = 5], Node[]date=32,weight = 9]......]
     */
    private static List<Node> getNodes(byte[] bytes){
        //要返回的Node集合
        List<Node> nodes = new ArrayList<>();
        //1.统计bytes中每个字符出现的次数 存入map中
        Map<Byte,Integer> countMap = new HashMap<>();
        for (byte key : bytes) {
            Integer value = countMap.get(key);
            //判断是否存在统计数
            if(value != null){
                countMap.put(key,value + 1);
            }else {
                countMap.put(key,1);
            }
        }
        //2.遍历Map构建Node集合
        /*方法1.
        for (Map.Entry<Byte, Integer> map : countMap.entrySet()) {
            nodes.add(new Node(map.getKey(),map.getValue()));
        }*/
        countMap.keySet().forEach(key -> {
            nodes.add(new Node(key,countMap.get(key)));
        });
        return nodes;
    }


    /**
     * 创建一个赫夫曼树
     * @param nodes 要构成赫夫曼树的node集合
     * @return 赫夫曼树的root节点
     */
    private static Node createHuffmanTree(List<Node> nodes){
        while (nodes.size() > 1){
            //11.排序
            Collections.sort(nodes);
            //2.取出当前集合中两个最小权值的node
            Node leftNode = nodes.remove(0);
            Node rightNode = nodes.remove(0);
            //(3).构建一颗新的二叉树 没有data 只有权值, 权值是两个子树的权值之和
            Node parent = new Node(null, leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;
            //将新的二叉树 放入集合
            nodes.add(parent);
        }
        return nodes.get(0);
    }
}
/**
 * 创建节点类
 * 为了让Node 对象支持排序Collections集合排序
 * 需要人Node类 实现Comparable接口
 */
class Node implements Comparable<Node>{
    /**
     * 存放数据(字符)本身  比如 'a' => 97 ' ' => 32
     */
    Byte data;
    /**
     * 节点权值  表示字符出现的次数
     */
    int weight;
    /**
     * 指向左子节点
     */
    Node left;
    /**
     * 指向右子节点
     */
    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    /**
     * 前序遍历
     */
    public void preOrder(){
        //输出节点
        System.out.println(this);
        if(this.left != null){
            this.left.preOrder();
        }
        if(this.right != null){
            this.right.preOrder();
        }
    }


    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        //现在是从小到大  如果要从大到小需要改成 -(this.value - o.value)
        return this.weight - o.weight;
    }
}
package cn.cherry.sparsearray;

import java.io.*;
import java.util.ArrayList;

/**
 * @program: DataStructures
 * @description: 二维数组转换为稀疏数组 存入磁盘，
 * 然后读取磁盘文件从稀疏数组再转化为二维数组 ，达到五子棋存盘效果
 * @author: Mr.Cherry
 * @create: 2019-11-29 10:56
 **/
public class SparseArray {
    public static void main(String[] args) {
        //创建一个原始的二维数组 11 * 11
        //0：没有棋子 1：黑子 2：白子
        int chessArr1[][] = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[4][6] = 2;
        //1.输出原始的二维数组 并得到二维数组中有效数据(非0)的数量
        int sum = 0;
        System.out.println("原始的二维数组：");
        for (int[] row : chessArr1) {
            for (int i : row) {
                //%d 整数占位符
                System.out.printf("%d\t",i);
                if(i != 0){
                    ++sum;
                }
            }
            System.out.println();
        }
        //将二维数组转化为稀疏数组
        System.out.println(sum);
        //2.创建对应的稀疏数组
        int sparseArr[][] = new int[sum + 1][3];
        //给稀疏数组赋值
        sparseArr[0][0] = chessArr1.length;
        sparseArr[0][1] = chessArr1[0].length;
        sparseArr[0][2] = sum;
        //遍历二维数组将非0的值存放到稀疏数组中
        //用于记录是第几个非0数据
        int index = 1;
        for(int i = 0;i<chessArr1.length;++i){
            for(int j = 0;j<chessArr1[0].length;++j){
                if(chessArr1[i][j] != 0){
                    sparseArr[index][0] = i;
                    sparseArr[index][1] = j;
                    sparseArr[index][2] = chessArr1[i][j];
                    ++index;
                }
            }
        }
        //输出稀疏数组
        System.out.println("得到的稀疏数组：");
        for (int[] row : sparseArr) {
            for (int i : row) {
                //%d 整数占位符
                System.out.printf("%d\t",i);
            }
            System.out.println();
        }

        try {
            //保存文件  自定义格式
            PrintStream printStream = new PrintStream(new FileOutputStream("K:\\sparseArray.data"));
            for(int i = 0;i<sparseArr.length;++i){
                printStream.printf("%d\t%d\t%d\t\n",sparseArr[i][0],sparseArr[i][1],sparseArr[i][2]);
            }
            printStream.close();

            int fileTosparseArr[][] = new int[sum + 1][3];

            //读取文件
            InputStreamReader reader  = new InputStreamReader(
                    new FileInputStream(new File("K:\\sparseArray.data")));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            int row = 0;
            int clumn = 0;
            while(line != null) {
                String[] split = line.split("\t");
                for (String s : split) {
                    if(clumn == 3){
                        ++row;
                        clumn = 0;
                    }
                    fileTosparseArr[row][clumn] = Integer.parseInt(s);
                    ++clumn;
                }
                line = br.readLine();
            }
            System.out.println("文件读取转换稀疏数组内容：");
            for (int[] row1 : fileTosparseArr) {
                for (int i : row1) {
                    //%d 整数占位符
                    System.out.printf("%d\t",i);
                }
                System.out.println();
            }

            //稀疏数组 恢复 为原始的二维数组
            int chessArr2[][] = new int[fileTosparseArr[0][0]][fileTosparseArr[0][1]];
            boolean b = true;
            for (int[] ints : fileTosparseArr) {
                if (b){
                    b = false;
                }else{
                    chessArr2[ints[0]][ints[1]] = ints[2];
                }
            }

            System.out.println("稀疏数组 恢复为原始的二维数组结果：");
            for (int[] row2 : chessArr2) {
                for (int i : row2) {
                    //%d 整数占位符
                    System.out.printf("%d\t",i);
                }
                System.out.println();
            }
            } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

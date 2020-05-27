package cn.cherry.树;

/**
 * @program: DataStructures
 * @description: 查询某个字符串 在另一个字符串中存在的次数
 * @author: Mr.Cherry
 * @create: 2019-12-27 13:38
 **/
public class Test {

    public static void main(String[] args) {
        String compared = "abkkcadkabkebfkabkskababababa";
        String str = "a";
        int result = existingQuantity(compared, str);
        System.out.println("result = " + result);

    }
    public static int existingQuantity(String Compared,String SearchVal){
        char[] compared = Compared.toCharArray();
        char[] searchVal = SearchVal.toCharArray();
        int  result = 0;
        for (int i = 0; i < compared.length; ++i) {
            if (compared[i] == searchVal[0] && (i+(searchVal.length-1)) < compared.length){
                int c = i+1;
                int c1 = 1;
                while (c1 < searchVal.length && searchVal[c1] == compared[c]){
                    ++c;
                    ++c1;
                }
                if(c1 == searchVal.length){
                    ++result;
                }
            }
            if((compared.length - (i+1) < searchVal.length)){
                break;
            }
        }
        return result;
    }
}

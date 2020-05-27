package cn.cherry.algorithm.greedy;

import java.util.*;

/**
 * @program: DataStructures
 * @description: 贪心算法/贪婪算法   解决  集合覆盖问题
 * @author: Mr.Cherry
 * @create: 2020-01-14 18:08
 **/
public class GreedyAlgorithm {
    public static void main(String[] args) {
        //创建广播电台  放入到map
        Map<String, Set<String>> broadcasts = new HashMap<>();
        //allAreas 存放所有的地区
        Set<String> allAreas = new HashSet<>();
        //将各个电台放入到broadcasts
        List<String []> all = new ArrayList<>(5);
        all.add(new String[]{"北京", "上海", "天津"});
        all.add(new String[]{"广州", "北京", "深圳"});
        all.add(new String[]{"成都", "上海", "杭州"});
        all.add(new String[]{"上海", "天津"});
        all.add(new String[]{"杭州", "大连"});
        int keySuffix = 1;
        //加入到map
        for (String[] strings : all) {
            Set<String> hashSet = new HashSet<>();
            for (String string : strings) {
                hashSet.add(string);
                allAreas.add(string);
            }
            broadcasts.put("K"+keySuffix,hashSet);
            ++keySuffix;
        }

        //创建ArrayList 存放选择的电台集合
        List<String> selects = new ArrayList<>();
        
        //定义一个临时的集合 在遍历的过程中 存放遍历过程中的电台覆盖的地区和当前还没有覆盖的地区的交集
        Set<String> tempSet = new HashSet<>();
        
        //定义maxKey 保存在一次遍历过程中能够覆盖最大未覆盖地区对应的电台的key
        //如果maxkey 不为null 则会加入到selects中
        String maxKey = null;
        
        //如果allAreas的长度 不为0 则表示还没有覆盖到所有的地区
        while (allAreas.size() > 0){
            //重置maxKey的值
            maxKey = null;
            for (String key : broadcasts.keySet()) {
                //清空tempSet
                tempSet.clear();
                //将当前key能够覆盖的地区赋给tempSet
                tempSet.addAll(broadcasts.get(key));
                //求出tempSet和allAreas的交集   然后赋给调用者(tempSet)
                tempSet.retainAll(allAreas);
                //如果当前这个集合包含的未覆盖地区的数量 比maxKey指向的集合地区还多，就需要重置maxKey
                //tempSet.size() > broadcasts.get(maxKey).size()  体现出贪心算法的特点 每次都选择最优的
                if (tempSet.size() > 0 &&
                        (maxKey == null || tempSet.size() > broadcasts.get(maxKey).size())){
                    maxKey = key;
                }
            }
            //如果maxKey不为空 就将maxKey存入 selects
            if (maxKey != null){
                selects.add(maxKey);
                //将maxKey指向的广播电台所覆盖的地区 从allAreas去掉
                //从allAreas中去除所有和broadcasts.get(maxKey)集合内容相同的值
                allAreas.removeAll(broadcasts.get(maxKey));
            }
        }

        System.out.println("selects = " + selects);
    }
}

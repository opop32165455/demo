package com.function.functional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * link: https://blog.csdn.net/chengxuyuan_110/article/details/81112913
 *
 * @author zhangxuecheng4441
 * @date 2021/2/5/005 14:46
 */
public class Boxer {


    private String group;
    private Integer weight;

    public Boxer(String group, Integer weight) {
        this.group = group;
        this.weight = weight;
    }


    public static <T> List<List<T>> divider(List<T> datas, Comparator<T> c) {
        //声明一个列表来接收各个分组
        List<List<T>> list = new ArrayList<>();
        for (T t : datas) {
            //通过isSameGroup 标识来判断分组是否已创建
            boolean isSameGroup = false;
            for (List<T> ts : list) {
                //compare函数返回值为int，正数说明param1>param2,0说明param1=param2,负数说明param1<param2
                //这里用到的原理是将List列表datas中的各项与分组列表list中的元素比较（比较的规则由外面作为参数传递，这就是函数式编程），
                // 值为0表示规则相符即为同一组
                if (c.compare(t, ts.get(0)) == 0) {
                    isSameGroup = true;
                    ts.add(t);
                    break;
                }
            }
            //比较完了发现没有规则相符的，即自成一系
            if (!isSameGroup) {
                List<T> e = new ArrayList<>();
                e.add(t);
                list.add(e);
            }
        }

        return list;

    }


    /**
     * @param t   入参 需要匹配规则的参数
     * @param p   函数接口 匹配的动作
     * @param <T>
     * @return 验证成功 是集合 则只留匹配元素，是String，Integer ..匹配成功返回本身，反之返回null
     */

    public static <T> T match(T t, Predicate<Object> p) {

        if (t instanceof Collection) {

            if (t instanceof List) {

                t = (T) ((List) t).stream().filter(a -> p.test(a)).collect(Collectors.toList());

            }
            if (t instanceof Set) {
                t = (T) ((Set) t).stream().filter(a -> p.test(a)).collect(Collectors.toSet());
            }
        } else {

            if (!p.test(t)) {

                return null;

            }

        }

        return t;
    }

    public static void main(String[] args) {
        List<Boxer> boxers = Arrays.asList(
                new Boxer("红队", 120),
                new Boxer("绿队", 180),
                new Boxer("蓝队", 200),
                new Boxer("绿队", 220),
                new Boxer("蓝队", 120),
                new Boxer("红队", 80),
                new Boxer("红队", 90),
                new Boxer("绿队", 240)

        );
        //分组一样 即认为 相同
        List<List<Boxer>> dividByGroup = divider(boxers, (o1, o2) -> o1.group.equals(o2.group) ? 0 : 1);
        System.out.println("根据小组区分:");
        dividByGroup.forEach(System.out::println);
        //体重/100 即体重百分位相同 即认为 相同
        List<List<Boxer>> dividByWeight = divider(boxers, (o1, o2) -> (o1.weight / 100 - o2.weight / 100) == 0 ? 0 : 1);
        System.out.println("根据体重区分:");
        dividByWeight.forEach(System.out::println);

        System.out.println("----------------------------------------------------------------");

        List<Object> list = Arrays.asList(1, 2, "", 100, "3", 'c');

        List<Object> matchList = match(list, a -> a instanceof Integer && (Integer) a > 10);
        //100
        System.out.println(matchList);


        Set set = new HashSet<>();

        set.add("haha");

        set.add(1);

        set.add(100);


        Set matchSet = match(set, a -> a instanceof Integer);
        //[1, 100]
        System.out.println(matchSet);


        String matchString = match("java", a -> a instanceof String && ((String) a).startsWith("j"));
        //java
        System.out.println(matchString);

        Integer noMatch = match(100, a -> a instanceof String && ((String) a).startsWith("j"));
        //null
        System.out.println(noMatch);

    }


    @Override
    public String toString() {
        return "Boxer{" +
                "小组='" + group + '\'' +
                ", 体重=" + weight +
                '}';
    }

}



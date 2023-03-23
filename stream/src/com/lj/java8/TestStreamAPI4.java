package com.lj.java8;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author LiuJuan
 * @projectName java8
 * @date 2023/3/23 14:19
 * @description
 * @copyright
 */
public class TestStreamAPI4 {

    public void test1(){
        //根据List集合获取流
        List<String> list = new ArrayList<>();
        list.add("xh");
        list.add("we");
        list.add("qa");
        list.add("hg");
        list.add("nm");
        list.add("cv");
        Stream<String> stream1 = list.stream();
    }

    public void  test2(){
        //根据set集合获取流
        Set<String> set = new HashSet<>();
        set.add("ww");
        set.add("rt");
        set.add("fg");
        set.add("vv");
        set.add("hh");
        set.add("xx");
        Stream<String> stream2 = set.stream();
    }
    public void test3(){
        //根据Map集合获取流
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"ss");
        map.put(2,"ff");
        map.put(3,"xx");
        map.put(4,"vv");
        map.put(5,"bb");
        map.put(6,"jj");

        //1，根据map集合的键获取流
        Set<Integer> map1 = map.keySet();
        Stream<Integer> stream3 = map1.stream();

        //根据map集合的值获取获取流
        Collection<String> collections = map.values();
        Stream<String> stream4 = collections.stream();

        //估计map集合的键值对对象获取流
        Set<Map.Entry<Integer,String>> map2 = map.entrySet();
        Stream<Map.Entry<Integer,String>> stream5 = map2.stream();

    }

    public void test4(){
        //根据数组获取流
        String[] strings = {"ee","ff","ew","rr"};
        Stream<String> stream6 = Stream.of(strings);
    }



}

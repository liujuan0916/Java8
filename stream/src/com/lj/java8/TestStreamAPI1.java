package com.lj.java8;

/**
 * @author LiuJuan
 * @projectName java8
 * @date 2023/3/21 14:50
 * @description
 * @copyright
 */


import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Stream操作的3个步骤
 * 1.创建Stream
 * 2.中间操作
 * 3，终止操作（终端操作）
 */
public class TestStreamAPI1 {
    //创建Stream
     @Test
    public void test1(){
        //1.通过Collection集合提供的stream()或parallelStream()
          List<String> list = new ArrayList<>();
        //顺序流
          Stream<String> stream1 = list.stream();
        //并行流
          Stream<String> parallelStream = list.parallelStream();

        //2.通过Stream类中的静态方法of()方法创建
          Stream<String> stream2 = Stream.of(
                list.toArray(new String[list.size()]));

        //3.通过Arrays 中的静态方法stream()获取数组流
          Employee[] employee = new Employee[10];
          Stream<Employee> stream3 = Arrays.stream(employee);
        //4.无限流
          Stream<Integer> stream4 = Stream.iterate(0,(x) -> x+2);
          stream4.limit(10).forEach(System.out::println);

         //生成
         Stream.generate(() -> Math.random())
                 .limit(5)
                 .forEach(System.out::println);


    }
}

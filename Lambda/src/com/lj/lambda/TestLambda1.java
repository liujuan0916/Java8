package com.lj.lambda;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author LiuJuan
 * @projectName java8
 * @date 2023/3/22 9:05
 * @description
 * @copyright
 */
public class TestLambda1 {

    /**
     * Lambda表达式使用示例
     * Lambda表达式语法：(函数式接口的args) -> {函数式接口中抽象方法的实现逻辑}
     * 1.创建多线程
     */
    @Test
    public void demo1(){
        //no lambda
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread run....");
            }
        }).start();

        //lambda
        new Thread(() -> {
            System.out.println("thread run....");
        }).start();
    }


    /**
     *2.集合的处理（排序）
     */
    @Test
    public void demo2(){
        List<String> strings = Arrays.asList("llnew","oldd","nginxxx","tomcat","maven");
        //no lambda
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        System.out.println(strings);

        //lambda
        Collections.sort(strings, (a,b) -> a.length() - b.length());
        System.out.println(strings);
    }



}

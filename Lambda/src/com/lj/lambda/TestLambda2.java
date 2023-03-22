package com.lj.lambda;

import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author LiuJuan
 * @projectName java8
 * @date 2023/3/22 9:48
 * @description
 * @copyright
 */
public class TestLambda2 {

    /**
     * lambda表达式实例
     * 1.无参无返回值实例
     */
    @Test
    public void demo1(){
        //不使用lambda
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("run");
            }
        };
        new Thread(runnable).start();

        //Lambda
        Runnable runnable1 = () -> {System.out.println("run");};
        //省略大括号
        Runnable runnable2 = () -> System.out.println("run");
    }

    /**
     * 2.无参有返回值实例
     */
    @Test
    public void demo2() throws Exception {
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                return "hello!";
            }
        };
        callable.call();

        Callable<String> callable1 = () -> { return "hello";};
        Callable<String> callable2 = () -> "Hello";

    }

    /**
     * 3.有参无返回值
     */
    @Test
    public void demo3() {
        UserMapper u1 = new UserMapper() {
            @Override
            public void insert(User user) {
                System.out.println("insert user: " + user);
            }
        };
        // 声明参数类型
        UserMapper u2 = (User user) -> {System.out.println("insert user: " + user);};
        // 省略参数类型
        UserMapper u3 = user -> {System.out.println("insert user: " + user);};

        u1.insert(new User());
        u2.insert(new User());
        u3.insert(new User());
    }

    // 函数式接口
    interface UserMapper {
        // 抽象方法
        void insert(User user);
    }

    interface OrderMapper {
        // 抽象方法
        int insert(Order order);
    }

    /**
     * 4.有参有返回值实例，返回自定义的函数式接口：
     */
    @Test
    public void demo4(){
       OrderMapper o1 = new OrderMapper() {
           @Override
           public int insert(Order order) {
               System.out.println("insert order"+order);
               return 1;
           }
       };

       OrderMapper o2 = (Order order) ->  {return 1;};
       OrderMapper o3 = oder -> {return 1;};
       OrderMapper o4 = (Order order) -> 1;
       OrderMapper o5 = order -> 1;

       o1.insert(new Order());

    }

    /**
     * 5.具有较复杂方法体的函数式接口实例
     */
    @Test
    public void demo5(){
        //输入int，返回int
        Function<Integer, Integer> f1 = a ->{
            int sum = 0;
            for (int i = 0 ;i <= a; i++){
                sum += i;
            }
            return sum;
        };
        System.out.println(f1.apply(10));
    }

    /**
     * 6.Lambda表达式直接调用其他方法实现抽象方法实例：
     */
    @Test
    public void demo6(){
        Runnable r1 = () -> get();
        Runnable r2 = () -> find();
        Runnable r3 = () -> exec();

        FOO f1 = () -> get();
        FOO f2 = () -> 100;


    }
    public int get(){
        return 1;
    }
    public void exec(){}
    public String find(){
        return "Hello";
    }
    interface FOO{
        int get();
    }

    /**
     * 使用BiFunction函数式接口实例。方法体的实现为简单的计算传入的两个String类型变量的长度和并返回。
     */
    @Test
    public void  demo7(){
        BiFunction<String,String,Integer> biFunction = (a,b) ->{
            return a.length()+b.length();
        };
        System.out.println(biFunction.apply("wdfffd","ddfgdw"));
    }


}

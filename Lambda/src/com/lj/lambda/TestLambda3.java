package com.lj.lambda;

/**
 * @author LiuJuan
 * @projectName java8
 * @date 2023/3/22 10:56
 * @description
 * @copyright
 */


import org.testng.annotations.Test;

import javax.lang.model.type.ReferenceType;
import java.lang.ref.Reference;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Lambda表达式中方法的引用
 */
public class TestLambda3 {

    //实例
    @Test
    void demo5() {
        // 输入一个字符串，将其转换为大写字母
        Function<String, String> fn = (str) -> str.toUpperCase();
        System.out.println(fn.apply("admin"));

        // Consumer表示一个输入没有输出，下面实现输出输入的参数
        Consumer<String> consumer = arg -> {System.out.println(arg);};
        consumer.accept("hello");
    }
    /**
     * 1.静态方法引用
     * 静态方法引用：如果函数式接口的实现恰好可以通过调用一个静态方法来实现，那么就可以使用静态方法引用。
     * 语法：类名::staticMethod
     */@Test
    public void demo1(){
        Supplier<String> s1 = () -> TestLambda3.get();
        //等价于上面
        Supplier<String> s2 = TestLambda3::get;
        Supplier<String> s3 = Fun::get;

        System.out.println(s1.get());
        System.out.println(s2.get());
        System.out.println(s3.get());

        //有输入参数
        Consumer<Integer> c1 = (size) -> TestLambda3.consumer(size);
        Consumer<Integer> c2 = TestLambda3::consumer;
        c1.accept(100);
        c2.accept(200);

        //有输入有输出
        //原始的lambda表达式
        Function<String,String> f1 = str -> str.toUpperCase();
        //调用String类中的静态方法
        Function<String,String> f2 = String::toUpperCase;
        //调用自定义的静态方法
        Function<String,String> f3 =TestLambda3::toUpperCase;
        System.out.println(f1.apply("dfgr"));
        System.out.println(f2.apply("ercgg"));
        System.out.println(f3.apply("kjho"));

        //2个输入1个输出
        BiFunction<String,String,Integer> biFunction1 = (str1,str2) -> str1.length() + str2.length();
        BiFunction<String,String,Integer> biFunction2 = TestLambda3::length;
        System.out.println(biFunction1.apply("ergfg","dfgr"));
        System.out.println(biFunction2.apply("sdfgr","scsc"));

    }


    static String get(){return "Hello!";}
    static void consumer(Integer size){System.out.println("size"+size);}
    static String toUpperCase(String str){return str.toUpperCase();}
    static Integer length(String str1, String str2){return str1.length() + str2.length();}

    static class  Fun{
        static String get(){
            return "Successful";
        }
    }

    /**
     * 2.实例方法的引用
     * 实例方法引用：如果函数式接口的实现恰好可以通过调用一个实例的实例方法来实现，那么就可以使用实例方法引用。
     * 语法：instance::instanceMethod
     */
    @Test
    public void demo2(){
        Supplier<String> s1 = () -> new TestLambda3().put();
        Supplier<String> s2 = () ->{return new TestLambda3().put();};
        Supplier<String> s3 = new TestLambda3()::put;
        System.out.println(s1.get());
        System.out.println(s2.get());
        System.out.println(s3.get());

        //无输入，有输出
        Consumer<Integer> c1 = (size) -> new TestLambda3().con(size);
        Consumer<Integer> c2 = (size) -> {new TestLambda3().con(size);};
        Consumer<Integer> c3 = new TestLambda3()::con;
        c1.accept(100);
        c2.accept(200);
        c3.accept(300);

        //其他
        Function<String,Integer> f1 = (str) -> this.toUpper(str);
        Function<String,Integer> f2 =this::toUpper;
        System.out.println(f1.apply("dfgr"));
        System.out.println(f2.apply("ercgg"));
    }

    public String put(){return "lalalal";}
    public void con(Integer size){System.out.println("size"+size);}
    public Integer toUpper(String str){return str.length();}

    /**
     * 3.构造方法引用
     * 语法：类名::new
     */

    @Test
    public void demo3(){
        Supplier<Person> s1 = () -> new Person();
        Supplier<Person> s2 = Person::new;
        s1.get();
        s2.get();

        // 已存在的类，只要有无参构造方法都可以使用
        Supplier<List> listSupplier = ArrayList::new;
        Supplier<String> stringSupplier = String::new;
        Supplier<Thread> threadSupplier = Thread::new;
        Supplier<Set> setSupplier = HashSet::new;
    }

    /**
     * 构造方法有参数的情况
     */
    @Test
    public void demo6(){

         Consumer<Integer> c1 = (age) -> new Account(age);
         Consumer<Integer> c2 = Account::new;
         c1.accept(100);
         c2.accept(200);
    }


    class Account{
        public Account(){
            System.out.println("Account()");
        }
        public Account(String name){
            System.out.println("Account(name)");
        }
        public Account(int age){
            System.out.println("Account(age)");
        }
    }


    class Person {
        public Person() {
            System.out.println("new Person()");
        }
    }



}

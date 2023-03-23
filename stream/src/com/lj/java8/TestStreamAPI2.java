package com.lj.java8;



import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author LiuJuan
 * @projectName java8
 * @date 2023/3/21 15:56
 * @description
 * @copyright
 */

/**
 * Stream操作的3个步骤
 * 1.创建Stream
 * 2.中间操作
 * 3.终止操作（终端操作）
 */
public class TestStreamAPI2 {
    /**
     * 中间操作
     * 筛选与切片
     * filter——接受Lambda，从流中排除某些元素
     * limit——截断流，使元素不能超过指定范围
     * skip(n)—— 跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流。
     * distinct——筛选，通过流所生产元素的hashCode()和equals()去除重复元素
     */
    List<Employee> employees = Arrays.asList(
            new Employee("zhangsan",34,33333.332),
            new Employee("lisi",36,5677.3322),
            new Employee("wangwu",32,65554.3532),
            new Employee("mili",24,55788.45),
            new Employee("kaka",44,5788.3632)
    );

    @Test
    /**
     * 内部迭代：迭代操作由 Strem API完成
     */
    public void test1(){
        //中间操作，不会执行任何操作
        Stream<Employee> stream1 = employees.stream()
                .filter((e) -> {
                    System.out.println("中间操作");
                    return  e.getAge() > 30;
                });
        //终止操作，一次性执行全部内容，即“惰性求值”
        stream1.forEach(System.out::println);

    }

    /**
     * 外部迭代
     */
    @Test
    public void test2(){
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
    @Test
    public void test3(){
        employees.stream()
                .filter((e) -> {
                    System.out.println("ddd");
                    return e.getSalary() > 5500;
                })
                .limit(2)
                .forEach(System.out::println);

    }

    @Test
    public void test4(){
        employees.stream()
                .filter((e) -> e.getAge() > 30)
                .skip(2)
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 中间操作
     * 映射
     * map元素转换成其他形式或提取信息。接收一个函数作为参数，该幽数会被应用到每个元素上，并将其映射成一个新的元素。
     * flatMap—接收一个函数作为参数，将淡中的每个值都换成另一个流,然后把所有流连接成一个流
     */
    @Test
    public void test5(){
        List<String> list = Arrays.asList("aa","bb","cc","dd");
        list.stream()
                .map(s -> s.toUpperCase())
                .forEach(System.out::println);

        System.out.println("-------------------------");
        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
    }

    /**
     * 中间操作
     * 排序
     * sorted() ——自然排序
     * sorted(Comparator comparator) —— 定制排序
     */
    @Test
    public void test6(){

        List<String> list = Arrays.asList("cc","aa","bb","ee","dd");
        list.stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println("--------------------");

        employees.stream()
                .sorted((e1,e2) ->{
                    if (e1.getAge().equals(e2.getAge())){
                        return e1.getName().compareTo(e2.getName());
                    } else {
                        return e1.getAge().compareTo(e2.getAge());
                    }
                }).forEach(System.out::println);

    }
}

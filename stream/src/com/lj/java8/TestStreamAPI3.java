package com.lj.java8;

import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 终止操作
 * @author 1
 */
public class TestStreamAPI3 {

    /**
     * 1.查找与匹配
     * allMatch(Predicate p)	检查是否匹配所有元素
     * anyMatch(Predicate )	    检查是否至少匹配一个元素
     * noneMatch(Predicate p)	检查是否没有匹配所有元素
     * findFirst()	            返回第一个元素
     * findAny()	            返回当前流中的任意元素
     * count()	                返回流中元素总数
     * max(Comparator c)	    返回流中最大值
     * min(Comparator c)	    返回流中最小值
     */
    List<Employee> employees = Arrays.asList(
            new Employee("zhangsan",34,33333.332, Employee.Status.BUSY),
            new Employee("lisi",36,5677.3322, Employee.Status.FREE),
            new Employee("wangwu",32,65554.3532, Employee.Status.FREE),
            new Employee("mili",24,55788.45, Employee.Status.BUSY),
            new Employee("kaka",44,5788.3632, Employee.Status.BUSY)
    );


    @Test
    public void test1(){
         boolean b1 = employees.stream()
                .allMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b1);

        boolean b2 = employees.stream()
                .anyMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b2);

        boolean b3 = employees.stream()
                .noneMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b3);

        Optional<Employee> op = employees.stream()
                .sorted((e1,e2) -> Double.compare(e1.getSalary(),e2.getSalary()))
                .findFirst();
        System.out.println(op.get());

        Optional<Employee> op1 = employees.stream()
                .filter(e -> e.getStatus().equals(Employee.Status.BUSY))
                .findAny();
        System.out.println(op1.get());

    }

    @Test
    public void test2(){
       Long count = employees.stream()
                .count();
        System.out.println(count);

       Optional<Employee> op1 = employees.stream()
                .max((e1,e2) -> Double.compare(e1.getSalary(),e2.getSalary()));
        System.out.println(op1.get());

       Optional<Double> op2 = employees.stream()
               .map(Employee::getSalary)
               .min(Double::compare);
        System.out.println(op2.get());

    }
    /**
     * 归约
     * reduce(T iden, BinaryOperator b)	可以将流中元素反复结合起来，得到一个值。返回 T
     */
    @Test
    public void test3(){

        List<Integer> list = Arrays.asList(1,43,22,67,95,28,78);
        Integer sum = list.stream()
                .reduce(0,(x,y) ->x+y);
        System.out.println(sum);
        System.out.println("-----------------------");

        //工资总和
       Optional<Double> op3 = employees.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(op3.get());
    }

    /**
     * 收集
     */
    @Test
    public void test4(){
        //获取所有员工姓名，并收集到集合中
        employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        employees.stream()
               .map(Employee::getName)
               .collect(Collectors.toSet());
        employees.stream()
               .map(Employee::getName)
               .collect(Collectors.toCollection(ArrayList::new));
       //计算总数
        Long count = employees.stream()
                .collect(Collectors.counting());
        System.out.println(count);
        //计算薪资总数
         Double sum = employees.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));

        //平均工资
        Double avg = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));

        //获取袁工薪资的最大值/最小值 maxBy()/minBy()
        Optional<Employee> op3 =  employees.stream()
                .collect(Collectors.maxBy((e1,e2) -> Double.compare(e1.getSalary(),e2.getSalary())));
        System.out.println(op3.get());

        Optional<Double> min = employees.stream()
                .map(Employee::getSalary)
                .collect(Collectors.minBy(Double::compare));
        System.out.println(min.get());



    }@Test
    public void test5(){
        //分组

        Map<Employee.Status,List<Employee>> re = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(re);
    }

    @Test
    public void test6(){
        //多级分组
        Map<Employee.Status,Map<String,List<Employee>>> map =employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus,Collectors.groupingBy(e ->{
                    if (e.getAge() <= 35){
                        return "青年";
                    } else if(e.getAge() <=45){
                        return "中年";
                    } else {
                        return "老年";
                    }
                })));
        System.out.println(map);

    }
}

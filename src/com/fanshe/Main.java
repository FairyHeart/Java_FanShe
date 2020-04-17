package com.fanshe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {

        /**
         * 获取class的三种方式，三种获取的class为同一对象
         */
        //第一种方式获取Class对象
        Student stu1 = new Student();//这一new 产生一个Student对象，一个Class对象。
        Class stuClass1 = stu1.getClass();//获取Class对象

        //第二种方式获取Class对象
        Class stuClass2 = Student.class;

        //第三种方式获取Class对象
        Class clazz = Class.forName("com.fanshe.Student");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名

        /**
         * 通过反射获取构造方法
         *
         */
        System.out.println("***** 所有公有构造方法 *****");
        Constructor[] conArray = clazz.getConstructors();
        for (Constructor c : conArray) {
            System.out.println(c);
        }


        System.out.println("\n***** 所有的构造方法(包括：私有、受保护、默认、公有) *****");
        conArray = clazz.getDeclaredConstructors();
        for (Constructor c : conArray) {
            System.out.println(c);
        }

        System.out.println("\n***** 获取公有、无参的构造方法 *****");
        Constructor con = clazz.getConstructor(null);
        //1>、因为是无参的构造方法所以类型是一个null,不写也可以：这里需要的是一个参数的类型，切记是类型
        //2>、返回的是描述这个无参构造函数的类对象。
        System.out.println("con = " + con);

        //调用构造方法
        Object object = con.newInstance();
        //  System.out.println("obj = " + obj);
        //  Student stu = (Student)obj;

        System.out.println("\n***** 获取私有构造方法，并调用 *****");
        con = clazz.getDeclaredConstructor(char.class);
        System.out.println(con);
        //调用构造方法
        con.setAccessible(true);//暴力访问(忽略掉访问修饰符)
        object = con.newInstance('男');


        /**
         * 获取成员变量并调用
         */
        System.out.println("\n***** 获取所有公有的字段 *****");
        Field[] fieldArray = clazz.getFields();
        for (Field f : fieldArray) {
            System.out.println(f);
        }
        System.out.println("\n***** 获取所有的字段(包括私有、受保护、默认的) *****");
        fieldArray = clazz.getDeclaredFields();
        for (Field f : fieldArray) {
            System.out.println(f);
        }
        System.out.println("\n***** 获取公有字段**并调用 *****");
        Field f = clazz.getField("name");
        System.out.println(f);
        //获取一个对象
        Object obj = clazz.getConstructor().newInstance();//产生Student对象--》Student stu = new Student();
        //为字段设置值
        f.set(obj, "刘德华");//为Student对象中的name属性赋值--》stu.name = "刘德华"
        //验证
        Student stu = (Student) obj;
        System.out.println("验证姓名：" + stu.name);


        System.out.println("\n***** 获取私有字段****并调用 *****");
        f = clazz.getDeclaredField("phoneNum");
        System.out.println(f);
        f.setAccessible(true);//暴力反射，解除私有限定
        f.set(obj, "18888889999");
        System.out.println("验证电话：" + stu);
    }
}

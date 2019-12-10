package com.gas.test.learn.delegate;

import com.gas.test.utils.TUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {

    public static void main(String[] args) {


        //
        /*
         *  java 反射
         *  ==============================================
         *  class 字节码文件
         *  获取Class的三种方式
         *  Class.forName("class的全路径名") 用的最多
         *  clazz.getClass()
         *  clazz.class
         *  ==============================================
         *  构造器对象
         *  //只能得到public声明的构造器
         *  Constructor[] cons= clazz.getConstructors();
         *  //得到所有的构造器
         *  Constructor[] cons= clazz.getDeclaredConstructors();
         *  Constructor c = cons[0];
         *  Object obj = c.newInstance();
         *  ==============================================
         *
         *
         *
         */
        Model model = new Model();
        Class<Model> clazz = Model.class;

        System.out.println(model.getClass() == Model.class);
        try {
            System.out.println(Class.forName("com.gas.test.learn.delegate.Model") == Model.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        TUtil.jdkPrintln("getConstructors");
        Constructor[] cons = model.getClass().getConstructors();
        for (Constructor con : cons
        ) {
            System.out.println(con);
        }

        TUtil.jdkPrintln("getDeclaredConstructors");

        Constructor[] conss = model.getClass().getDeclaredConstructors();
        for (Constructor con : conss
        ) {
            System.out.println(con);
        }

        try {
            Model model2 = (Model) cons[0].newInstance("小花花", 23);
            System.out.println(model2 == model);
            System.out.println(model2.getName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            Constructor<Model> constructor = Model.class.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            Model model2 = constructor.newInstance("二花");
            TUtil.jdkPrintlnEquit("model2.name", model2.getName());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        TUtil.jdkPrintln("method");

        TUtil.jdkPrintln("getMethods");
        //得到当前类中的方法 包含父类
        Method[] methods = clazz.getMethods();

        for (Method method : methods
        ) {
            System.out.println(method);
        }
        TUtil.jdkPrintln("getDeclaredMethods");
        //得到当前类中的方法 不包含父类
        Method[] declaredMethods = clazz.getDeclaredMethods();

        for (Method method : declaredMethods) {
            System.out.println(method);
        }
        TUtil.jdkPrintln("得到特定的方法");
        //方法名
        //参数类型的字节码对象
        try {
            Method method1 = clazz.getMethod("method");
            System.out.println(method1);
            Method method2 = clazz.getDeclaredMethod("method", int.class);
            method2.setAccessible(true);
            System.out.println(method2);

            //直接用字节码的newInstance 必须要在类的声明中有无参构造
            Model model3 = clazz.newInstance();
            //怎么调用方法 （方法所属的对象，方法的参数）
            //返回方法执行之后的值
            TUtil.jdkPrintlnEquit("model3.name", method1.invoke(model3));
            method2.invoke(model3, 30);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        TUtil.jdkPrintln("field");

        Field[] fields = clazz.getFields();
        for (Field f : fields
        ) {
            System.out.println(f);
        }

        try {
            Field name = clazz.getDeclaredField("name");
            System.out.println(name);
            name.setAccessible(true);
            Model model4 = clazz.newInstance();
            name.set(model4, "李佳星");
            System.out.println(model4);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}

package com.gas.test.learn.delegate;

public class Model {

    private String name;
    private int age;

    public Model() {
    }

    private Model(String name) {
        this.name = name;
    }

    public Model(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String method() {
        return "花花";
    }

    private void method(int age) {
        System.out.printf("age=%d", age);
        System.out.println();
    }

    @Override
    public String toString() {
        return "Model{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

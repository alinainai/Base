package com.gas.test.learn.thread;

public class Synchronized3Demo implements TestDemo {

    private int x = 0;
    private int y = 0;
    private String name;
    private final Object monitor1 = new Object();
    private final Object monitor2 = new Object();

    //我们加锁关注的是资源 而不是方法
    private void count(int newValue) {
        synchronized (monitor1) {
            x = newValue;
            y = newValue;
        }
    }

    private void minus(int delta) {
        synchronized (monitor1) {
            x -= delta;
            y -= delta;
        }
    }

    private void setName(String newName) {
        synchronized (monitor2) {
            name = newName;
        }
    }

    @Override
    public void runTest() {

    }
}

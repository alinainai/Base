package com.gas.test.learn.thread;

public class WaitDemo implements TestDemo {

    private String shareString;

    private synchronized void printString() {

        while (shareString == null) {
            try {
                //wait 和 notify 都需要在 synchronized 修饰的方法中运行
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("String:" + shareString);


    }

    private synchronized void initString() {
        shareString = "lijiaxing";
        //wait 和 notify 都需要在 synchronized 修饰的方法中运行
        notifyAll();
    }


    @Override
    public void runTest() {


        final Thread thread2=new Thread(){
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                initString();

            }
        };
        thread2.start();

        final Thread thread1=new Thread(){
            @Override
            public void run() {

                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                printString();

            }
        };
        thread1.start();
        //把时间段让给同优先级的线程，只让一下
//        Thread.yield();
    }
}

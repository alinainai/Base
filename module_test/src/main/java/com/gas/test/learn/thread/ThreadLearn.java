package com.gas.test.learn.thread;

import android.app.IntentService;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLearn {

    public static void main(String[] args) {

//        thread();
//        runnable();
//        threadFactory();
//        executor();
//        callable();
//        intercept();

//        waitAndNotify();

        //GC Root
        // 1.运行中的线程
        // 2.静态对象
        // 3.本地代码指针指向的对象

        //可以被杀死的线程 干掉进程时不需要考录的线程
//        Thread thread;
//        thread.setDaemon(true);
        //


        //
//        Executor;
//        AsyncTask;
//        HandlerThread;  和Executor差不多 用起来比Executor麻烦
//        IntentService; 执行单个任务并且可以马上被关闭的service

        //Service 和 IntentService


    }


    static void waitAndNotify(){
        new WaitDemo().runTest();
    }

    /**
     * intercept用法
     */
    static void intercept() {

        Thread thread = new Thread() {

            @Override
            public void run() {

                // android的这个睡眠会一直睡完时间
//                SystemClock.sleep(2000);

                try{
                    // 调用interrupt()方法会强力中断正在进行的睡眠操作
                    // 并触发InterruptedException异常
                    Thread.sleep(2000);


                }catch (InterruptedException e){
                    e.printStackTrace();
                    //在这里做一些收尾操作
                    return;
                }
                System.out.println("number hahaha");


//                for (int i = 0; i < 1_000_000; i++) {
                    //在耗时处理之前调用 是否已经中断的方法

                    //Thread.interrupted()
                    // 这个方法会重置中断状态

                    //isInterrupted
                    //
//                    if (isInterrupted()) {
//
//                        if (i < 10000)
//                            //进行收尾操作
//                            return;
//                    }
//                    if (Thread.interrupted()) {
//                        if (i < 10000)
//                            return;
//                    }
//                    System.out.println("number" + i);
//                }

            }
        };

        thread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // stop方法比较暴力
        thread.interrupt();

    }


    /**
     * 使用Thread来定义工作
     */
    static void thread() {

        Thread thread = new Thread() {

            @Override
            public void run() {
                System.out.println("Thread Start");
            }
        };
        thread.start();


    }

    /**
     * 使用 Runnable 类来定义工作
     */
    static void runnable() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread whit Runnable");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }

    static void threadFactory() {

        ThreadFactory factory = new ThreadFactory() {

            int count = 0;


            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Thread-" + count++);
            }
        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "started!");
            }
        };
        Thread thread = factory.newThread(runnable);
        thread.start();
        Thread thread1 = factory.newThread(runnable);
        thread1.start();

    }

    static void executor() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("thread with runnable started!");
            }
        };
        Executor executor = Executors.newCachedThreadPool();
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

        //newFixedThreadPool
        //应用场景：马上提供20个线程 执行某事
        //执行完毕后立马取消
//        ExecutorService bitmapProcessor=Executors.newFixedThreadPool(20);
//        Runnable bitmapRunner=...;
//        List<Bitmap> bitmaps=...;
//        for (Bitmap bitmap:bitmaps) {
//            bitmapProcessor.execute(bitmapRunner);
//        }
//        //温和版的取消
//        bitmapProcessor.shutdown();

        //可以定时的 Schedule
//        Executors.newSingleThreadScheduledExecutor();

        //很常用的写法
        BlockingDeque<Runnable> queue = new LinkedBlockingDeque<>(1000);
        ThreadPoolExecutor myExecutor = new ThreadPoolExecutor(5, 100, 5, TimeUnit.SECONDS, queue);

        //

    }

    //Callable 有返回值的 Runnable
    static void callable() {

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return "Done";
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        //使用 Future 拿到最终结果
        Future<String> future = executor.submit(callable);

        while (!future.isDone()) {
            //做点事
        }
        try {

            String result = future.get();
            System.out.println("result:" + result);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }

    static void runSynchronized1Demo() {

    }

    static void runSynchronized2Demo() {

    }

    static void runSynchronized3Demo() {

    }

    static void runReadWriteLockDemo() {

    }


}

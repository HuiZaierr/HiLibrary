package com.ych.coroutine;

/**
 * TODO：分别开启三个线程A,B,C,打印输出Log。确保线程执行顺序同样为A,B,C
 *     思路：使用thread.join方法
 */
public class Test {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread1");
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread2");
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread3");
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }






    }
}

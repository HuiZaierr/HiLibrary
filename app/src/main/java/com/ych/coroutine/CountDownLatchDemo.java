package com.ych.coroutine;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * TODO：CountDownLatch实现
 *    多人做过山车场景，假设5人做过山车，5个都准备好了，才能发车。
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(new Random().nextInt(4000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"：准备好了");
                    countDownLatch.countDown(); //计数器会减1，当计数器为0是会唤醒线程。
                }
            }).start();
        }

        try {
            countDownLatch.await();
            System.out.println("所有人都准备好了。准备发车...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package com.ych.hi_library;

import android.os.SystemClock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO：通过多个线程进行打印。区分ReentrantLock的公平锁(true)与非公平锁(false)
 *     默认为非公平锁
 *     非公平锁的性能远远高于公平锁的。
 *     使用场景：
 *       公平锁：交易，用户先下单的先获取锁
 *       非公平锁：synchronized，场景比比皆是。
 */
public class ReentrantLockDemo {

    static class ReentrantTask{
        ReentrantLock lock = new ReentrantLock(false);

        void print(){
            String name = Thread.currentThread().getName();
            try{
                //尝试获取锁
                lock.lock();
                System.out.println(name + "第一次打印");
                Thread.sleep(1000);
                lock.unlock();

                lock.lock();
                System.out.println(name + "第二次打印");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantTask task = new ReentrantTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                task.print();
            }
        };
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }

}

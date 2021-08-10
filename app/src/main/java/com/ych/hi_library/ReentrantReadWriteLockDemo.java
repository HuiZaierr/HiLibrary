package com.ych.hi_library;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * TODO:ReentrantReadWriteLock：读写锁,他分别有两个锁，
 *      一把读锁，读取数据并不涉及安全问题，可以同时有多个线程进行获取读锁
 *      一把写锁，写入数据是涉及数据安全问题，此时同一时间只能有一个线程获取到该锁，进行数据操作，此时其他线程进入等待状态。
 */
public class ReentrantReadWriteLockDemo {

    static class ReentrantReadWriteTask{
        private final ReentrantReadWriteLock.ReadLock readLock;
        private final ReentrantReadWriteLock.WriteLock writeLock;
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        public ReentrantReadWriteTask(){
            readLock = lock.readLock();
            writeLock = lock.writeLock();
        }

        void read(){
            String name = Thread.currentThread().getName();
            try{
                readLock.lock();
                System.out.println("线程"+name + "正在读取数据...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
                System.out.println("线程"+name + "释放了读锁...");
            }
        }

        void write(){
            String name = Thread.currentThread().getName();
            try{
                writeLock.lock();
                System.out.println("线程"+name + "正在写入数据...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
                System.out.println("线程"+name + "释放了写锁...");
            }
        }
    }

    public static void main(String[] args) {
        ReentrantReadWriteTask task = new ReentrantReadWriteTask();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    task.read();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    task.write();
                }
            }
        }).start();

//        ThreadPoolExecutor executor = new ThreadPoolExecutor(
//                1,
//                10,
//                60,
//                TimeUnit.SECONDS,
//                new LinkedBlockingQueue<>(),
//                new ThreadPoolExecutor.AbortPolicy());
//        executor.execute();
    }
}

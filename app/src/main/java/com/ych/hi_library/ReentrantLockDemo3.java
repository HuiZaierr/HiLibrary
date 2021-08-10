package com.ych.hi_library;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO：ReentrantLock的进阶，Condition条件对象。
 *   场景：一个Boss生产砖，当砖的序号为偶数时工人2去搬，奇数时工人1去搬。
 *        消费者是两个工人，有砖就搬，没砖就休息。
 */
public class ReentrantLockDemo3 {

    static class ReentrantLockTask{
        ReentrantLock lock = new ReentrantLock(true);
        private Condition condition1,condition2;
        volatile int flag = 0;
        public ReentrantLockTask(){
            //创建
            condition1 = lock.newCondition();
            condition2 = lock.newCondition();
        }


        void work1(){
            try{
                lock.lock();
                //当工人1，为偶数时需要休息
                if (flag == 0 || flag % 2 ==0){
                    System.out.println("工人1无砖可搬，休息会");
                    condition1.await();
                }

                System.out.println("工人1 搬到的砖是："+flag);
                flag = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        void work2(){
            try{
                lock.lock();
                //当工人1，为偶数时需要休息
                if ( flag % 2 !=0){
                    System.out.println("工人2无砖可搬，休息会");
                    condition2.await();
                }

                System.out.println("工人2 搬到的砖是："+flag);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        void boss(){
            try{
                lock.lock();
                flag = new Random().nextInt(100);
                if (flag % 2 ==0){
                    condition2.signal();
                    System.out.println("生产出来了砖，唤醒工人2去搬" + flag);
                }else {
                    condition1.signal();
                    System.out.println("生产出来了砖，唤醒工人1去搬" + flag);
                }
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockTask task = new ReentrantLockTask();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    task.work1();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    task.work2();
                }
            }
        }).start();

        //生产10块砖。
        for (int i = 0; i < 10; i++) {
            task.boss();
        }
    }
}

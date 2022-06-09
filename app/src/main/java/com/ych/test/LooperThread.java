package com.ych.test;

import android.os.Looper;

public class LooperThread extends Thread{

    private Looper mLooper;

    public LooperThread(String name) {
        super(name);
    }

    /**
     * TODO：提供一个获取当前线程的Looper对象，我们此时需要进处理，如果还未创建，进行阻塞等待，知道创建完成后调用notify进行通知
     * @return
     */
    public Looper getLooper() {
        if (mLooper == null && isAlive()){
            synchronized (this){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return mLooper;
    }

    @Override
    public void run() {
        //会通过ThreadLocal检查是否有对应的Looper对象，没有则进行创建 new Looper(quitAllowed) 创建Looper的同时也会创建其MessageQueue对象
        Looper.prepare();
        synchronized (this){
            mLooper = Looper.myLooper();
            notifyAll();
        }
        //开启消息循环
        Looper.loop();
    }
}

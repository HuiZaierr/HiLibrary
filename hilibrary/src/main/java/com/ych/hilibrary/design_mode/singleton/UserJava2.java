package com.ych.hilibrary.design_mode.singleton;

/**
 * TODO：Java的懒汉式单例，当使用的时候才会进行创建
 *     双重检索判断：第一次判断是为了解决我们创建过对象时，避免重复获取锁对象。
 *                 第二次判断是为了防止对此创建对象。当两个线程同时访问时，一个线程第一次判断为空，第二个线程第一次判断也为空，
 *                 那么就会进入下面synchronized关键字为对象进行上锁，此时只能有一个线程获取到锁对象，另一个线程需等待，该线程
 *                 释放掉锁之后才能获取，所以如果线程获取到锁后，再次判断是否为空。就防止多次对象的创建。
 */
public class UserJava2 {

    public static volatile UserJava2 mInstance;

    private UserJava2(){}

    public static UserJava2 getInstance(){
        if (mInstance == null){
            synchronized (UserJava2.class){
                if (mInstance == null){
                    mInstance = new UserJava2();
                }
            }
        }
        return mInstance;
    }
}

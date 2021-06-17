package com.ych.hilibrary.design_mode.singleton;

/**
 * TODO：Java的饿汉式单例（表示直接创建对象）
 *     1.在类被初始化的死后就创建了对象。
 *     2.他不存在线程安全问题。
 */
public class UserJava1 {
    public static UserJava1 mInstance = new UserJava1();

    private UserJava1(){}

    public static UserJava1 getInstance(){
        return mInstance;
    }
}

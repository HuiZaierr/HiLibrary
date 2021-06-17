package com.ych.hilibrary.design_mode.singleton;

/**
 * TODO：Java静态内部类实现单例
 */
public class UserJava3 {

    public static class UserFiveProvider{
        private static UserJava3 instance = new UserJava3();
    }

    private UserJava3(){}

    public static UserJava3 getInstance(){
        return UserFiveProvider.instance;
    }
}

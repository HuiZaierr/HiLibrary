package com.ych.hilibrary.design_mode.presenter;

public class HomePresenter extends HomeContract.Presenter{

    /**
     * TODO：这里就编写具体的网络请求代码
     */
    @Override
    void getUserInfo() {
        //通过Retrofit进行网络请求，或者是Kotlin的Coroutine
        //成功获取数据后，进行判断View状态。
        if (view!=null && view.isAlive()){
            view.onGetUserInfoSuccess("获取成功！！！");
        }
    }
}

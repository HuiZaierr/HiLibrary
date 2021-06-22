package com.ych.hilibrary.design_mode.presenter;

public interface HomeContract {

    interface View extends BaseView{
        //View的回掉方法
        void onGetUserInfoSuccess(String msg);
    }

    /**
     * BasePresenter具体的类型就行我们的View.
     */
    abstract class Presenter extends BasePresenter<View>{
        abstract void getUserInfo();
    }

}

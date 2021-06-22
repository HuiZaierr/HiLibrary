package com.ych.hilibrary.design_mode.presenter;

/**
 * TODO:Presenter需要持有View的引用，通过回掉。
 *      1.只需要编写两个方法，attach/detach方法。
 *      2.通过泛型来指定具体的View。因为我们所有的View都实现BaseView(基类)
 */
public class BasePresenter<IView extends BaseView>{
    protected IView view;
    public void attach(IView view){
        this.view = view;
    }

    public void detach(){
        view = null;
    }

}

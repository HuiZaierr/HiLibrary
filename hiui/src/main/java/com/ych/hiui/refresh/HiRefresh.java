package com.ych.hiui.refresh;

import com.ych.hiui.tab.top.HiTabTop;

public interface HiRefresh {

    /**
     * 刷新时是否禁止滚动
     * @param disableRefreshScroll
     */
    void setDisableRefreshScroll(boolean disableRefreshScroll);

    /**
     * 刷新完成
     */
    void refreshFinished();

    /**
     * 设置下拉刷新监听的方法
     * @param hiRefreshListener
     */
    void setRefreshListener(HiRefreshListener hiRefreshListener);

    /**
     * 设置下拉刷新的视图
     * @param hiOverView
     */
    void setRefreshOverView(HiOverView hiOverView);


    //刷新监听器
    interface HiRefreshListener{

        void onRefresh();
        //禁止刷新
        void enableRefresh();
    }
}

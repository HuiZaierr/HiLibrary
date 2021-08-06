package com.ych.hi_library.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ych.common.ui.component.HiBaseFragment;
import com.ych.day01.SignatureUtils;
import com.ych.hi_library.R;
import com.ych.hi_library.SecondActivity;
import com.ych.hi_library.demo.DataBindingActivity;
import com.ych.hi_library.demo.HiLogDemoActivity;
import com.ych.hi_library.demo.HiNavigationDemoActivity;
import com.ych.hi_library.demo.HiRefreshDemoActivity;
import com.ych.hi_library.demo.HiTabTopDemoActivity;
import com.ych.hilibrary.log.HiLog;


public class HomePageFragment extends HiBaseFragment implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void initView() {
        TextView homeTv = layoutView.findViewById(R.id.homeTv);
        Button btnHiLog = layoutView.findViewById(R.id.btnHiLog);
        Button btnDataBind = layoutView.findViewById(R.id.btnDataBind);
        Button btnTabTop = layoutView.findViewById(R.id.btnTabTop);
        Button btnRefresh = layoutView.findViewById(R.id.btnRefresh);
        Button btnNaviigation = layoutView.findViewById(R.id.btnNaviigation);
        Button btnArouter = layoutView.findViewById(R.id.btnArouter);
        Button btnArouterDegrade = layoutView.findViewById(R.id.btnArouterDegrade);
        btnHiLog.setOnClickListener(this);
        btnDataBind.setOnClickListener(this);
        btnTabTop.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
        btnNaviigation.setOnClickListener(this);
        btnArouter.setOnClickListener(this);
        btnArouterDegrade.setOnClickListener(this);

        homeTv.postInvalidateOnAnimation();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnHiLog:
                 startActivity(new Intent(getActivity(),HiLogDemoActivity.class));
                break;
            case R.id.btnDataBind:
                startActivity(new Intent(getActivity(), DataBindingActivity.class));
                break;
            case R.id.btnTabTop:
                startActivity(new Intent(getActivity(), HiTabTopDemoActivity.class));
                break;
            case R.id.btnRefresh:
                startActivity(new Intent(getActivity(), HiRefreshDemoActivity.class));
                break;
            case R.id.btnNaviigation:
                startActivity(new Intent(getActivity(), HiNavigationDemoActivity.class));
                break;
            case R.id.btnArouter:
                Bundle bundle = new Bundle();
                bundle.putString("id","123456");
                bundle.putString("name","张三");
                bundle.putInt("age",26);
                ARouter.getInstance()
                        .build("/test/router1")
                        .with(bundle)
                        .navigation(getActivity(), new NavCallback() {
                            @Override
                            public void onFound(Postcard postcard) {
                                HiLog.et("Test1Interceptor","找到了！！");
                            }

                            @Override
                            public void onLost(Postcard postcard) {
                                //当定义的路由未找到时，我们通过自定义的DegradeServiceImpl来实现跳转到统一的错误页
                                HiLog.et("Test1Interceptor","未找到！！");
                            }

                            @Override
                            public void onArrival(Postcard postcard) {
                                HiLog.et("Test1Interceptor","同意了");
                            }

                            @Override
                            public void onInterrupt(Postcard postcard) {
                                HiLog.et("Test1Interceptor","被拦截了");
                            }
                        });
                break;
            case R.id.btnArouterDegrade:    //ARoute全局降级操作
                ARouter.getInstance().build("/degrade/global/ac").navigation();
                break;
        }
    }
}

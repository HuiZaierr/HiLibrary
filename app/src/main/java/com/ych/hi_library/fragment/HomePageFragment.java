package com.ych.hi_library.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ych.common.ui.component.HiBaseFragment;
import com.ych.hi_library.R;
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
        ObjectAnimator animator = ObjectAnimator.ofFloat(btnHiLog,"trx",1f);
        animator.start();
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
                bundle.putString("name","??????");
                bundle.putInt("age",26);
                ARouter.getInstance()
                        .build("/test/router1")
                        .with(bundle)
                        .navigation(getActivity(), new NavCallback() {
                            @Override
                            public void onFound(Postcard postcard) {
                                HiLog.et("Test1Interceptor","???????????????");
                            }

                            @Override
                            public void onLost(Postcard postcard) {
                                //?????????????????????????????????????????????????????????DegradeServiceImpl????????????????????????????????????
                                HiLog.et("Test1Interceptor","???????????????");
                            }

                            @Override
                            public void onArrival(Postcard postcard) {
                                HiLog.et("Test1Interceptor","?????????");
                            }

                            @Override
                            public void onInterrupt(Postcard postcard) {
                                HiLog.et("Test1Interceptor","????????????");
                            }
                        });
                break;
            case R.id.btnArouterDegrade:    //ARoute??????????????????
                ARouter.getInstance().build("/degrade/global/ac").navigation();
                break;
        }
    }
}

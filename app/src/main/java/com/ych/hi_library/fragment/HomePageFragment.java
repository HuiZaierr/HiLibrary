package com.ych.hi_library.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ych.common.ui.component.HiBaseFragment;
import com.ych.day01.SignatureUtils;
import com.ych.hi_library.R;
import com.ych.hi_library.SecondActivity;
import com.ych.hi_library.demo.DataBindingActivity;
import com.ych.hi_library.demo.HiLogDemoActivity;
import com.ych.hi_library.demo.HiRefreshDemoActivity;
import com.ych.hi_library.demo.HiTabTopDemoActivity;


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
        btnHiLog.setOnClickListener(this);
        btnDataBind.setOnClickListener(this);
        btnTabTop.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
//        homeTv.setText(SignatureUtils.signatureParams("userName=240336124&userPwd=123456"));
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
        }
    }
}

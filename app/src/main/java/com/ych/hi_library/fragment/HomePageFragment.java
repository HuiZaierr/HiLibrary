package com.ych.hi_library.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.ych.common.ui.component.HiBaseFragment;
import com.ych.hi_library.R;
import com.ych.hi_library.demo.DataBindingActivity;
import com.ych.hi_library.demo.HiLogDemoActivity;
import com.ych.hi_library.demo.HiTabTopDemoActivity;

public class HomePageFragment extends HiBaseFragment implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void initView() {
        Button btnHiLog = layoutView.findViewById(R.id.btnHiLog);
        Button btnDataBind = layoutView.findViewById(R.id.btnDataBind);
        Button btnTabTop = layoutView.findViewById(R.id.btnTabTop);
        btnHiLog.setOnClickListener(this);
        btnDataBind.setOnClickListener(this);
        btnTabTop.setOnClickListener(this);
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
        }
    }
}

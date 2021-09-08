package com.ych.hi_library.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ych.common.ui.component.HiBaseFragment;
import com.ych.hi_library.R;

public class FavoriteFragment extends HiBaseFragment implements View.OnClickListener {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void initView() {
        Button btnZero = getView().findViewById(R.id.btnZero);
        btnZero.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnZero:
                Toast.makeText(getActivity(),1/0 + "-crash",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

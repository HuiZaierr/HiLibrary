package com.ych.common.tab;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ych.hiui.tab.bottom.HiTabBottomInfo;

import java.util.List;

/**
 * TODO：用于管理Fragment的Adapter.
 */
public class HiTabViewAdapter {
    //底部的列表
    private List<HiTabBottomInfo<?>> mInfoList;
    //当前的Fragment
    private Fragment mCurFragment;
    private FragmentManager mFragmentManager;


    public HiTabViewAdapter(FragmentManager mFragmentManager,List<HiTabBottomInfo<?>> infoList) {
        this.mFragmentManager = mFragmentManager;
        this.mInfoList = infoList;
    }

    /**
     * todo:实例化以及显示指定位置的Fragment
     * @param contatiner
     * @param position
     */
    public void instantiateItem(View contatiner,int position){
        FragmentTransaction mCurTransaction = mFragmentManager.beginTransaction();
        if (mCurFragment!=null){
            mCurTransaction.hide(mCurFragment);
        }
        String tag = contatiner.getId() + ":"+position;
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        //表示添加过Fragment
        if (fragment!=null){
            mCurTransaction.show(fragment);     //显示fragment
        }else {
            //创建Fragment
            fragment = getItem(position);
            //fragment是否已经添加
            if (!fragment.isAdded()){
                mCurTransaction.add(contatiner.getId(),fragment,tag);
            }
        }
        //将赋值的fragment赋值给当前的fragment
        mCurFragment = fragment;
        //提交修改
        mCurTransaction.commitAllowingStateLoss();

    }

    public Fragment getCurrentFragment() {
        return mCurFragment;
    }

    public Fragment getItem(int position){
        try {
            return mInfoList.get(position).fragment.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCount(){
        return mInfoList == null?0:mInfoList.size();
    }
}

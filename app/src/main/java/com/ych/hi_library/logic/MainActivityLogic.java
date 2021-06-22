package com.ych.hi_library.logic;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.ych.common.tab.HiFragmentTabView;
import com.ych.common.tab.HiTabViewAdapter;
import com.ych.hi_library.R;
import com.ych.hi_library.fragment.CategoryFragment;
import com.ych.hi_library.fragment.FavoriteFragment;
import com.ych.hi_library.fragment.HomePageFragment;
import com.ych.hi_library.fragment.ProfileFragment;
import com.ych.hi_library.fragment.RecommendFragment;
import com.ych.hilibrary.util.HiDisplayUtil;
import com.ych.hiui.tab.bottom.HiTabBottom;
import com.ych.hiui.tab.bottom.HiTabBottomInfo;
import com.ych.hiui.tab.bottom.HiTabBottomLayout;
import com.ych.hiui.tab.common.IHiTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO：处理MainActivity的逻辑，让MainActivity中的逻辑更加清爽
 */
public class MainActivityLogic {
    //每个Fragment对应的TabView
    private HiFragmentTabView fragmentTabView;
    //底部的BottomLayout
    private HiTabBottomLayout hiTabBottomLayout;
    //底部BottomLayout对应的数据信息。以及Fragment
    private List<HiTabBottomInfo<?>> infoList;
    //当前选中的哪个Fragment
    private int currentItemIndex;
    private ActivityProvider activityProvider;

    public MainActivityLogic(ActivityProvider activityProvider) {
        this.activityProvider = activityProvider;
        initTabBottom();         //初始化底部Tab
    }


    private void initTabBottom() {
        hiTabBottomLayout = activityProvider.findViewById(R.id.tab_bottom_layout);
        hiTabBottomLayout.setAlpha(1f);
        infoList = new ArrayList<>();
        int defaultColor = activityProvider.getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = activityProvider.getResources().getColor(R.color.tabBottomTintColor);
        //添加底部Tab实体信息
        HiTabBottomInfo homeInfo = new HiTabBottomInfo(
                "首页",
                BitmapFactory.decodeResource(activityProvider.getResources(),R.mipmap.ic_maintab0_white_24dp_np,null),
                BitmapFactory.decodeResource(activityProvider.getResources(),R.mipmap.ic_maintab0_white_24dp,null),
                defaultColor,
                tintColor);
        homeInfo.fragment = HomePageFragment.class;

        //收藏
        HiTabBottomInfo favoriteInfo = new HiTabBottomInfo(
                "收藏",
                BitmapFactory.decodeResource(activityProvider.getResources(),R.mipmap.ic_maintab1_white_24dp_np,null),
                BitmapFactory.decodeResource(activityProvider.getResources(),R.mipmap.ic_maintab1_white_24dp,null),
                defaultColor,
                tintColor);
        favoriteInfo.fragment = FavoriteFragment.class;

        //分类
        HiTabBottomInfo categoryInfo = new HiTabBottomInfo(
                "分类",
                BitmapFactory.decodeResource(activityProvider.getResources(),R.mipmap.fire,null),
                BitmapFactory.decodeResource(activityProvider.getResources(),R.mipmap.fire,null));
        categoryInfo.fragment = CategoryFragment.class;

        //推荐
        HiTabBottomInfo recommendInfo = new HiTabBottomInfo(
                "推荐",
                BitmapFactory.decodeResource(activityProvider.getResources(),R.mipmap.ic_maintab2_white_24dp_np,null),
                BitmapFactory.decodeResource(activityProvider.getResources(),R.mipmap.ic_maintab2_white_24dp,null),
                defaultColor,
                tintColor);
        recommendInfo.fragment = RecommendFragment.class;

        //我的
        HiTabBottomInfo profileInfo = new HiTabBottomInfo(
                "我的",
                BitmapFactory.decodeResource(activityProvider.getResources(),R.mipmap.ic_maintab3_white_24dp_np,null),
                BitmapFactory.decodeResource(activityProvider.getResources(),R.mipmap.ic_maintab3_white_24dp,null),
                defaultColor,
                tintColor);
        profileInfo.fragment = ProfileFragment.class;

        //添加数据
        infoList.add(homeInfo);
        infoList.add(favoriteInfo);
        infoList.add(categoryInfo);
        infoList.add(recommendInfo);
        infoList.add(profileInfo);
        //设置数据
        hiTabBottomLayout.inflateInfo(infoList);
        initFragmentTabView();   //初始化Tab对应的Fragment（TabView）

        hiTabBottomLayout.addTabSelectedChangeListener(new IHiTabLayout.OnTabSelectedListener<HiTabBottomInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @Nullable HiTabBottomInfo<?> prevInfo, @NonNull HiTabBottomInfo<?> nextInfo) {
                fragmentTabView.setCurrentItem(index);
            }
        });
        //默认选中哪一个
        hiTabBottomLayout.defaultSelected(homeInfo);
        //改变某个tab的高度
        HiTabBottom tabBottom = hiTabBottomLayout.findTab(infoList.get(2));
        tabBottom.resetHeight(HiDisplayUtil.dp2px(66f, activityProvider.getResources()));
    }

    private void initFragmentTabView() {
        HiTabViewAdapter tabViewAdapter = new HiTabViewAdapter(activityProvider.getSupportFragmentManager(),infoList);
        fragmentTabView = activityProvider.findViewById(R.id.fragmnent_tab_view);
        fragmentTabView.setAdapter(tabViewAdapter);

    }


    public interface ActivityProvider{
        <T extends View> T findViewById(@IdRes int id);

        Resources getResources();

        FragmentManager getSupportFragmentManager();

        String getString(@StringRes int resId);
    }

    public HiFragmentTabView getFragmentTabView() {
        return fragmentTabView;
    }

    public HiTabBottomLayout getHiTabBottomLayout() {
        return hiTabBottomLayout;
    }

    public List<HiTabBottomInfo<?>> getInfoList() {
        return infoList;
    }
}

package com.ych.hi_library.fragment;

import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import com.ych.common.ui.component.HiBaseFragment;
import com.ych.coroutine.CoroutineScene;
import com.ych.coroutine.CoroutineScene2_decompiled;
import com.ych.hi_library.R;
import com.ych.hilibrary.executor.HiExecutor;
import com.ych.hilibrary.log.HiLog;
import com.ych.hilibrary.util.HiRes;


public class CategoryFragment extends HiBaseFragment implements View.OnClickListener {

    private String TAG = "CategoryFragment";
    private boolean pause = false;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        HiLog.et("TAG","CategoryFragment：onConfigurationChanged");
    }

    @Override
    protected void initView() {
        Button btnPriority =  layoutView.findViewById(R.id.btnPriority);
        btnPriority.setOnClickListener(this);
        btnPriority.setTextColor(HiRes.Companion.getINSTANCE().getColor(R.color.color_d43));
        Button btnPauseResume =  layoutView.findViewById(R.id.btnPauseResume);
        btnPauseResume.setOnClickListener(this);
        btnPauseResume.setTextColor(HiRes.Companion.getINSTANCE().getColor(R.color.color_4a4));
        Button btnAsyncTask =  layoutView.findViewById(R.id.btnAsyncTask);
        btnAsyncTask.setOnClickListener(this);
        Button btnStratCoroutine =  layoutView.findViewById(R.id.btnStratCoroutine);
        btnStratCoroutine.setOnClickListener(this);
        Button btnStratAsyncCoroutine =  layoutView.findViewById(R.id.btnStratAsyncCoroutine);
        btnStratAsyncCoroutine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPriority:      //按去任务优先级去执行
                for (int priority = 0; priority < 10; priority++) {
                    int finalPriority = priority;
                    HiExecutor.INSTANCE.executor(priority,()->{
                        try {
                            Thread.sleep(1000 - finalPriority * 100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
                break;
            case R.id.btnPauseResume:   //暂停恢复线程池
                if (pause){
                    HiExecutor.INSTANCE.resume();
                }else {
                    HiExecutor.INSTANCE.pause();
                }
                pause = !pause;
                break;
            case R.id.btnAsyncTask:     //将异步任务回掉到主线程当中
                HiExecutor.INSTANCE.executor(0,new HiExecutor.Callable<String>() {
                    @Override
                    public void onCompleted(String s) {
                        String name = Thread.currentThread().getName();
                        HiLog.et(TAG,"onCompleted-当前线程名称："+name);
                        HiLog.et(TAG,"onCompleted-任务的结果是result："+s);
                    }

                    @Override
                    public String onBackground() {
                        String name = Thread.currentThread().getName();
                        HiLog.e(TAG,"onBackground-当前线程名称："+name);
                        return "我是异步任务的结果";
                    }
                });
                break;
            case R.id.btnStratCoroutine:         //启动同步协程
                CoroutineScene.INSTANCE.startScene1();
                break;
            case R.id.btnStratAsyncCoroutine:    //启动异步协程
                CoroutineScene.INSTANCE.startScene2();
                break;
        }
    }
}

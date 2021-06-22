package com.ych.hilibrary.design_mode.presenter;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView{
    protected P mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //动态创建Presenter对象，并且绑定
        Type superclass = this.getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType){
            Type[] arguments = ((ParameterizedType) superclass).getActualTypeArguments();
            if (arguments!=null && arguments[0] instanceof BasePresenter){
                try {
                    mPresenter = (P) arguments[0].getClass().newInstance();
                    mPresenter.attach(this);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 是否还存活。
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean isAlive() {
        return !isDestroyed() && !isFinishing();
    }

    /**
     * 如果不为空进行释放
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.detach();
        }
    }
}

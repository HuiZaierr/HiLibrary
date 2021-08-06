package com.ych.hiui.arouter.interceptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.ych.hilibrary.log.HiLog;

/**
 * TODO:ARouter拦截器
 *     Interceptor:
 *          priority:优先级
 *          name:拦截器名称
 */
@Interceptor(priority = 7,name = "login_interceptor")
public class BizInterceptor implements IInterceptor {

    private Context context;

    /**
     * TODO：该方法运行在ARoute的线程池当中的，也就是子线程中，所以我们需要操作时需要切换到主线程中。
     * @param postcard
     * @param callback
     */
    @Override
    public void process(final Postcard postcard, final InterceptorCallback callback) {
        HiLog.e("Test1Interceptor","process");
        //获取目标也Route注解中的path字段的值。也可以通过其他字段判断
        if ("/test/router1".equals(postcard.getPath())) {
            // 这里的弹窗仅做举例，代码写法不具有可参考价值
            final AlertDialog.Builder ab = new AlertDialog.Builder(postcard.getContext());
            ab.setCancelable(false);
            ab.setTitle("温馨提醒");
            ab.setMessage("想要跳转到RouterActivity么？(触发了\"//test/router1\"拦截器，拦截了本次跳转)");
            ab.setNegativeButton("继续", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    callback.onContinue(postcard);
                }
            });
            ab.setNeutralButton("算了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    callback.onInterrupt(null);
                }
            });
            ab.setPositiveButton("加点料", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    postcard.withString("extra", "我是在拦截器中附加的参数");
                    callback.onContinue(postcard);
                }
            });

            MainLooper.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ab.create().show();
                }
            });
        } else {
            callback.onContinue(postcard);
        }

    }

    @Override
    public void init(Context context) {
        this.context = context;
    }
}

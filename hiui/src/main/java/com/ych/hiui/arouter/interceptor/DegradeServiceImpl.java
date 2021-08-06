package com.ych.hiui.arouter.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ych.hilibrary.log.HiLog;

/**
 * TODO：全局的降级服务，当用户传入的路由页面不存在，此时重定向到统一的错误页。
 *    作用：当我们使用ARoute时，路由找不到时，就会触发onLost方法，我们可以将页面跳转到统一的错误页。
 */
@Route(path = "/degrade/global/service")
public class DegradeServiceImpl implements DegradeService {
    /**
     * TODO：当路由不存在会触发该方法,此时我们只需要将它中转到统一错误页
     */
    @Override
    public void onLost(Context context, Postcard postcard) {
        HiLog.et("DegradeServiceImpl","onLost");
        ARouter.getInstance()
                .build("/degrade/global/activity")
                .greenChannel()
                .navigation();
    }

    @Override
    public void init(Context context) {

    }
}

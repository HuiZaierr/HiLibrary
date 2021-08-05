package com.ych.hiui.arouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ych.hiui.R;

/**
 * TODO:ARouter的注解介绍
 *     @Route介绍：作用在类上
 *        path：指定路径，至少需要有两级，/xx/xx
 *              示例：@Route(path = "/test/activity")
 *        group：指定该页面路由所在的组，组的名称，通常不需要指定。默认会通过path的第一级作为组的名称。
 *     @AutoWired：作用在属性上，自动注入。需要通过Arouter.getInstance().inject(this)进行注入。
 */
@Route(path = "/test/router1")
public class RouterActivity extends AppCompatActivity {

    @Autowired
    String id;
    @Autowired
    String name;
    @Autowired
    int age;
    @Autowired
    String extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //使用Autowired是必须使用，或者使用正常的getIntent/getArgument
        ARouter.getInstance().inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router);


//        id = getIntent().getStringExtra("id");
//        name = getIntent().getStringExtra("name");
//        age = getIntent().getIntExtra("age",0);
//        extra = getIntent().getStringExtra("extra");

        TextView tvName = findViewById(R.id.tv_name);
        tvName.setText("id的值："+id + "--- name的值："+name+"--- age的值："+age+"--- extra的值："+extra);
        Toast.makeText(this,"id的值："+id + "--- name的值："+name+"--- age的值："+age+"--- extra的值："+extra,Toast.LENGTH_LONG).show();

    }
}
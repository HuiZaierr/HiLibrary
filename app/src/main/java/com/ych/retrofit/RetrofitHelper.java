package com.ych.retrofit;



import com.ych.hi_library.BuildConfig;

import java.util.concurrent.TimeUnit;

//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
//    private static final int TIMEOUT_READ = 20;
//    private static final int TIMEOUT_CONNECTION = 10;
//    private static OkHttpClient okHttpClient = null;
//
//    public static Retrofit create(){
//        //OkHttp配置信息
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS);
//        builder.readTimeout(TIMEOUT_READ, TimeUnit.SECONDS);
//        builder.writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS);
//        if (BuildConfig.DEBUG){
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            builder.addInterceptor(loggingInterceptor);
//        }
//        okHttpClient = builder.build();
//        //创建Retrofit对象
//        return new Retrofit.Builder()
//                    .baseUrl("https://www.jianshu.com/p/1a222a9394ce")
//                    .client(okHttpClient)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .build();
//    }
//
//    public static DWDApi createApi() {
//        //动态代理创建
//        return create().create(DWDApi.class);
//    }
}

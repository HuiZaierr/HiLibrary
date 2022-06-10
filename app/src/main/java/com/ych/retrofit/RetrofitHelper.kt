package com.ych.retrofit

import com.ych.hilibrary.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    private const val TIMEOUT_READ = 20
    private const val TIMEOUT_CONNECTION = 10

    private val okHttpClient: OkHttpClient by lazy {
        initOkhttp()
    }

    private fun initOkhttp(): OkHttpClient {
        //OkHttp配置信息
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(TIMEOUT_CONNECTION.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
        //缓存设置
        val cacheFile = File(Constant.PATH_CACHE, "cache_retrofit")
        val cache = Cache(cacheFile, 1024 * 1024 * 10)
        builder.cache(cache)
        //HttpLogging日志拦截器
        if (BuildConfig.API_ENV == 1){
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
//        //添加公共的请求头 iNetworkRequiredInfo是用于获取app的版本信息
//        builder.addInterceptor(new HeaderInterceptor(iNetworkRequiredInfo));
//        //添加api拦截器（模拟数据使用）
//        builder.addInterceptor(new ApiInterceptor(iNetworkRequiredInfo));
//        //添加缓存拦截器
//        builder.addNetworkInterceptor(new CacheInterceptor());
//        builder.addInterceptor(new CacheInterceptor());
        return builder.build()
    }

    private fun create(): Retrofit {
        //创建Retrofit对象
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_HOST)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun createApi(): LovolApi {
        //动态代理创建
        return create().create(LovolApi::class.java)
    }
}
package com.ych.retrofit


import com.ych.hilibrary.BuildConfig
import com.ych.hilibrary.util.AppGlobals
import java.io.File

class Constant {

    companion object{
        //==================  Host  ===================
        /** 服务器正式  */
        val RELEASE_HOST = "http://indc.21vianet.com/"
        /** 服务器测试  */
        val DEBUG_HOST = "http://172.22.99.27:83/"
        /** 服务器预发布  */
        val BETA_HOST = "http://172.22.99.27:83/"
        //动态获取服务器域名
        var BASE_HOST =
            when(BuildConfig.API_ENV){
                0 -> RELEASE_HOST
                1 -> DEBUG_HOST
                2 -> BETA_HOST
                else -> { // 注意这个块
                    DEBUG_HOST
                }
            }

        //==================  PATH  ====================
        val PATH_DATA: String = AppGlobals.get()!!.cacheDir.absolutePath + File.separator.toString() + "data"
        val PATH_CACHE = "$PATH_DATA/NetCache"
        const val TAG :String = "张三"

    }
}
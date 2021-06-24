package com.ych.hi_library.http

import com.ych.hilibrary.util.SPUtil

object ApiFactory {
    val KEY_DEGRADE_HTTP = "degrade_http"
    val HTTPS_BASE_URL = "https://api.devio.org/as/"
    val HTTP_BASE_URL = "http://api.devio.org/as/"
    val degrade2Http = SPUtil.getBoolean(KEY_DEGRADE_HTTP)
    val baseUrl = if (degrade2Http) HTTP_BASE_URL else HTTPS_BASE_URL

    init {
//        hiRestful.addInterceptor(BizInterceptor())
//        hiRestful.addInterceptor(HttpStatusInterceptor())
        //清除Http，还原Https，也就是只能在本次有效。
        SPUtil.putBoolean(KEY_DEGRADE_HTTP,false)
    }
}
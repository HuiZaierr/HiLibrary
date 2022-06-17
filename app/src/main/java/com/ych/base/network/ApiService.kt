package com.ych.base.network

import com.ych.base.network.requestbody.BaseRequestBody
import com.ych.base.network.response.LoginResponse
import com.ych.base.network.response.base.ApiResponse
import retrofit2.http.Body

import retrofit2.http.POST

interface ApiService {

    companion object {
        //==================  Host  ===================
        /** 服务器正式  */
        const val RELEASE_HOST = "http://ifarmapi.lovol.com"
        /** 服务器测试  */
        const val DEBUG_HOST = "http://101.254.102.22:7000"
        /** 服务器预发布  */
        const val BETA_HOST = "http://192.168.200.35:7200"
    }


    @POST("/userservice/login")
    suspend fun login(@Body map:BaseRequestBody<HashMap<String,Any?>>): ApiResponse<LoginResponse>

}
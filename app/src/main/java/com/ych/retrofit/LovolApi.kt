package com.ych.retrofit

import com.ych.retrofit.requestbody.RequestBodyModel
import com.ych.retrofit.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

interface LovolApi {

    @POST("/userservice/login")
    suspend fun login(@Body map: RequestBodyModel<HashMap<String, Any?>>):LoginResponse
}
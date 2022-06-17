package com.ych.retrofit.response

import java.io.Serializable

open class BaseResponse<T> {
    val body:T? = null
    open val header: Header? = null
}

open class Header(
    open val systemTime: Long? = 0L,
    open val responseCode: Int? = 0,
    open val errorMessage:String = ""
) : Serializable
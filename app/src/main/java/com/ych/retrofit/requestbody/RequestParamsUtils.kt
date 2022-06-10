package com.ych.retrofit.requestbody

import com.ych.retrofit.requestbody.RequestBodyModel

class RequestParamsUtils {

    companion object {
        fun <T> getRequestModel(t: T): RequestBodyModel<T> {
            val requestBodyModel = RequestBodyModel<T>().apply {
//                body = t
//                header.token = UserInfoUtils.getToken()
//                header.ua.platform = Tags.PLATFORM
//                header.ua.imei = NetWorkUtil.imei
            }
            return requestBodyModel
        }
    }
}
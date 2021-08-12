package com.ych.hilibrary.restful

interface HiCallback<T> {

    fun onSuccess(response: HiResponse<T>)

    fun onFailed(throwable: Throwable)
}
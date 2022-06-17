package com.ych.base.network.base.exception

/**
 * 描述　:自定义错误信息异常
 */
class AppException : Exception {

    var errorMsg: String //错误消息
    var errCode: Int = 0 //错误码
    var errorLog: String? //错误日志
    var throwable: Throwable? = null

    constructor(errCode: Int, errorMsg: String?, errorLog: String? = "", throwable: Throwable? = null) : super(errorMsg) {
        this.errCode = errCode
        this.errorMsg = errorMsg ?: "请求失败，请稍后再试"
        this.errorLog = errorLog ?: this.errorMsg
        this.throwable = throwable
    }

    constructor(error: Error, e: Throwable?) {
        errCode = error.getKey()
        errorMsg = error.getValue()
        errorLog = e?.message
        throwable = e
    }
}
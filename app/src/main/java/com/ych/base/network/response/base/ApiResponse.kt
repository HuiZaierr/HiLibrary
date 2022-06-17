package com.ych.base.network.response.base
import java.io.Serializable

/**
 * 作者　: 闫长辉
 * 时间　: 2022/6/16
 * 描述　:服务器返回数据的基类
 * 如果你的项目中有基类，那美滋滋，可以继承BaseResponse，请求时框架可以帮你自动脱壳，自动判断是否请求成功，怎么做：
 * 1.继承 BaseResponse
 * 2.重写isSucces 方法，编写你的业务需求，根据自己的条件判断数据是否请求成功
 * 3.重写 getResponseCode、getResponseData、getResponseMsg方法，传入你的 code data msg
 */
data class ApiResponse<T>(
    val body: T,
    val header: Header
) : BaseResponse<T>() {

    // 这里是示例，wanandroid 网站返回的 错误码为 0 就代表请求成功，请你根据自己的业务需求来改变
    override fun isSucces() = header.responseCode == 0

    override fun isLogin() = header.responseCode == 107401

    override fun getResponseCode() = header.responseCode

    override fun getResponseData() = body

    override fun getResponseMsg() = header.errorMessage

}

data class Header(
    open val systemTime: Long = 0L,
    open val responseCode: Int = 0,
    open val errorMessage: String = ""
) : Serializable
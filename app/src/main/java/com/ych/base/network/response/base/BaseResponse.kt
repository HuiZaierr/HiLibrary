package com.ych.base.network.response.base

/**
 * TODO：描述　: 服务器返回数据的基类
 *  如果需要框架帮你做脱壳处理请继承它！！请注意：
 *  2.必须实现抽象方法，根据自己的业务判断返回请求结果是否成功
 */
abstract class BaseResponse<T> {

    //是否成功（和后台对接，状态码==0时）
    abstract fun isSucces(): Boolean

    //用户是否登录
    abstract fun isLogin(): Boolean

    //获取数据
    abstract fun getResponseData(): T

    //获取响应码
    abstract fun getResponseCode(): Int

    //获取响应消息
    abstract fun getResponseMsg(): String
}
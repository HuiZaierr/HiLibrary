package com.ych.hilibrary.restful

open class HiResponse {

    companion object{
        const val SUCCESS:Int = 0                   //访问成功
        const val CACHE_SUCCESS:Int = 30            //
        const val RC_HAS_ERROR:Int = 5000           //有错误
        const val RC_ACCOUNT_INVALID:Int = 5001     //账号不存在
        const val RC_PWD_INVALID:Int = 5002         //密码错误
        const val RC_NEED_LOGIN:Int = 5003          //请先登录
        const val RC_NOT_PURCHASED:Int = 5004       //未购买本课程，或用户ID有误
        const val RC_CHECK_SERVER_ERROR:Int = 5005  //检验服务报错
        const val RC_USER_NAME_EXISTS:Int = 5006    //此用户名被占用
        const val RC_HTML_INVALID:Int = 8001        //请输入HTML
        const val RC_CONFIG_INVALID:Int = 8002      //请输入配置

        const val RC_USER_FORBID:Int = 6001         //用户身份非法，如有疑问可进入课程官方群联系管理员
        const val RC_AUTH_TOKEN_EXPIRED:Int = 4030  //访问Token过期，请重新设置
        const val RC_AUTH_TOKEN_INVALID:Int = 4031  //访问Token不正确，请重新设置
    }
}
package com.ych.retrofit.requestbody

import java.io.Serializable

class RequestBodyModel<T>(
    var body: T? = null,
    var header: Header = Header()
) : Serializable {
    class Header(
        var errorMessage: String = "", // string
        var responseCode: Int = 0, // 0
        var systemTime: Int = 0, // 0
        var token: String? = "", // string
        var ua: Ua = Ua()
    ) : Serializable {
        class Ua(
            var appVersion: String = "", // string
            var channel: String = "", // string
            var city: String = "", // string
            var clientIp: String = "", // string
            var height: Int = 0, // 0
            var imei: String? = "", // string
            var language: String = "", // string
            var model: String = "", // string
            var osVersion: String = "", // string
            var platform: String = "", // string
            var province: String = "", // string
            var width: Int = 0 // 0
        ) : Serializable
    }
}
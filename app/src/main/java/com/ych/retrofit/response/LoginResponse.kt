package com.ych.retrofit.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//@Parcelize
data class LoginResponse(
    var institutionName: String = "",
    var address: String = "", // string
    var area: String = "", // string
    var birthdayStr: String = "", // string
    var description: String = "", // string
    var email: String = "", // string
    var gender: Int = 0, // 1
    var groupId: Int = 0, // 0
    var icon: String = "", // string
    var id: String = "", // string
    var idcard: String = "", // string
    var institutionId: Int = 0, // 0
    var institutionIdArray: List<Int> = listOf(),
    var lastIp: String = "", // string
    var lastTime: String = "", // 2022-01-07T05:21:29.006Z
    var menuIndex: String = "", // string
    var mobile: String = "", // string
    var name: String = "", // string
    var password: String = "", // string
    var role: List<Role> = listOf(),
    var roleId: Int = 0, // 1
    var status: Int = 0, // 0
    var token: String = "", // string
    var username: String = "" // string
   ):BaseResponse() {
}

@Parcelize
data class Role(
    var capability: String = "", // string
    var description: String = "", // string
    var id: Int = 0, // 0
    var institutionId: Int = 0, // 0
    var institutionIdArray: List<Int> = listOf(),
    var institutionName: String = "", // string
    var menuIds: List<Int> = listOf(),
    var roleName: String = "", // string
    var status: Int = 0 // 0
): Parcelable
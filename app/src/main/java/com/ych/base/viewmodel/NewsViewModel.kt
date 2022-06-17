package com.ych.base.viewmodel


import androidx.lifecycle.MutableLiveData
import com.ych.base.ext.request
import com.ych.base.network.base.apiService
import com.ych.base.network.requestbody.getParams
import com.ych.base.network.response.LoginResponse


class NewsViewModel:BaseViewModel() {

    val loginData by lazy {
        MutableLiveData<LoginResponse>()
    }

    fun login(username: String?, password: String?) {
        request({
            val hashMapOf = hashMapOf<String, Any?>("username" to username, "password" to password)
            apiService.login(getParams(hashMapOf))
        },success = {
            loginData.postValue(it)
        },isShowDialog = true)
    }

}
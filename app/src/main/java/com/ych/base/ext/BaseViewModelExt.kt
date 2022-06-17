package com.ych.base.ext

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.ych.base.network.base.exception.AppException
import com.ych.base.network.base.exception.ExceptionHandle
import com.ych.base.network.response.base.BaseResponse
import com.ych.base.viewmodel.BaseViewModel
import kotlinx.coroutines.*

/**
 * TODO：VieModel的扩展方法。
 */
/**
 *  调用携程
 * @param block 操作耗时操作任务
 * @param success 成功回调
 * @param error 失败回调 可不给
 */
fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,   //网络请求
    success: (T) -> Unit,                   //请求成功
    error: (AppException) -> Unit = {
        ToastUtils.showLong(it.errorMsg)
    },     //请求失败
    isShowDialog: Boolean = false           //是否显示loading弹窗
) {
    viewModelScope.launch {
        runCatching {
            if (isShowDialog) loadingChange.showDialog.postValue("网络请求中...")
            //请求体
            block()
        }.onSuccess {
            //网络请求成功 关闭弹窗
            if (isShowDialog){
                loadingChange.dismissDialog.postValue(false)
            }
            runCatching {
                //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                executeResponse(it) { t -> success(t) }
            }.onFailure { e ->
                //失败回调
                error(ExceptionHandle.handleException(e))
            }
        }.onFailure {
            //网络请求异常 关闭弹窗
            if (isShowDialog){
                loadingChange.dismissDialog.postValue(false)
            }
            //失败回调
            error(ExceptionHandle.handleException(it))
        }
    }
}


/**
 * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
 */
suspend fun <T> executeResponse(
    response: BaseResponse<T>,
    success: suspend CoroutineScope.(T) -> Unit
) {
    coroutineScope {
        when {
            response.isSucces() -> {
                success(response.getResponseData())
            }
            response.isLogin() -> {
                ToastUtils.showLong("去登录")
            }
            else -> {
                throw AppException(
                    response.getResponseCode(),
                    response.getResponseMsg(),
                    response.getResponseMsg()
                )
            }
        }
    }
}

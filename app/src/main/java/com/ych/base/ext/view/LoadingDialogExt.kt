package com.ych.base.ext.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ych.hi_library.R


//loading框
private var loadingDialog: Dialog? = null

/**
 * 打开等待框
 */
fun AppCompatActivity.showLoadingExt() {
    if (!this.isFinishing) {
        if (loadingDialog == null) {
            loadingDialog = Dialog(this)
            loadingDialog!!.run {
                setContentView(R.layout.dialog_loading)
                show()
            }
        }
        loadingDialog?.show()
    }
}

/**
 * 关闭等待框
 */
fun AppCompatActivity.dismissLoadingExt() {
    loadingDialog?.dismiss()
    loadingDialog = null
}


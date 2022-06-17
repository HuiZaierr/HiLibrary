package com.ych.base.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ych.base.ext.getVmClazz
import com.ych.base.ext.notNull
import com.ych.base.ext.view.dismissLoadingExt
import com.ych.base.ext.view.showLoadingExt
import com.ych.base.viewmodel.BaseViewModel

abstract class BaseVmDbActivity <VM: BaseViewModel,DB:ViewDataBinding>:AppCompatActivity(){

    lateinit var mViewModel: VM
    lateinit var mBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBind()
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        //创建ViewModel
        mViewModel = createViewModel()
        registerUiChange()
        initView(savedInstanceState)
        createObserver()
    }

    private fun initDataBind() {
        mBinding = DataBindingUtil.setContentView(this,layoutId())
    }

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    abstract fun layoutId(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 注册UI 事件
     */
    private fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observe(this, Observer {
            showLoading(it)
        })
        //关闭弹窗
        mViewModel.loadingChange.dismissDialog.observe(this, Observer {
            dismissLoading()
        })
    }

    open fun showLoading(message: String = "请求网络中..."){
        showLoadingExt()
    }

    open fun dismissLoading(){
        dismissLoadingExt()
    }

}
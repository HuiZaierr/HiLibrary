package com.ych.base

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ych.base.activity.BaseVmDbActivity
import com.ych.base.viewmodel.NewsViewModel
import com.ych.hi_library.R
import com.ych.hi_library.databinding.ActivityNewsBinding

class NewsActivity : BaseVmDbActivity<NewsViewModel,ActivityNewsBinding>() {

    override fun layoutId(): Int {
        return R.layout.activity_news
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.tvName.text = "张三！！！"
        mBinding.btnLoading.setOnClickListener {
            mViewModel.login("test001","a123456")
        }
        mBinding.chipOne.setOnClickListener {
            Toast.makeText(this, "点击了第1个", Toast.LENGTH_SHORT).show()
        }
        mBinding.chipTwo.setOnClickListener {
            Toast.makeText(this, "点击了第2个", Toast.LENGTH_SHORT).show()
        }
        mBinding.chipThree.setOnClickListener {
            Toast.makeText(this, "点击了第3个", Toast.LENGTH_SHORT).show()
        }
        mBinding.chipOne.setOnCloseIconClickListener {
            mBinding.chipGroup.removeView(it)
        }
    }

    override fun createObserver() {
        mViewModel.loginData.observe(this, Observer {
            mBinding.tvName.text = it.username
        })
    }
}
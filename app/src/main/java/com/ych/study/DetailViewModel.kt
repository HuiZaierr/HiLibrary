package com.ych.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/***
 * TODO：如果在ViewModel构造函数中传递参数，那么需要我们创建Factory来实例化ViewModel。
 *   1.可以通过ViewModelProvider.NewInstanceFactory来实例化
 *   2.可以通过ViewModelProvider.AndroidViewModelFactory来实例化
 */
class DetailViewModel(val goodsId:String):ViewModel() {

    companion object{
        private class DetailViewModelFactory(val goodsId:String):ViewModelProvider.NewInstanceFactory(){
            /**
             * TODO：重写creat方法
             */
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val constructor = modelClass.getConstructor(String::class.java)
                if (constructor!=null){
                    return constructor.newInstance(goodsId)
                }
                return super.create(modelClass)
            }
        }
    }
}
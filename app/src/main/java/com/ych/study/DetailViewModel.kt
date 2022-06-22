package com.ych.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.ych.base.network.response.base.BaseResponse

/***
 * TODO：如果在ViewModel构造函数中传递参数，那么需要我们创建Factory来实例化ViewModel。
 *   1.可以通过ViewModelProvider.NewInstanceFactory来实例化
 *   2.可以通过ViewModelProvider.AndroidViewModelFactory来实例化
 */
class DetailViewModel(val goodsId:String):ViewModel() {

    companion object{
        private class DetailViewModelFactory(val goodsId:String?):ViewModelProvider.NewInstanceFactory(){
            /**
             * TODO：重写creat方法
             */
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                //如果构造方法没有传入goodsId,那么会报错。
                try {
                    val constructor = modelClass.getConstructor(String::class.java)
                    if (constructor!=null){
                        return constructor.newInstance(goodsId)
                    }
                }catch (e:Exception){
                    //ignore
                }
                return super.create(modelClass)
            }
        }

        /**
         * TODO；提供一个方法获取DetailViewModel对象。
         */
        fun get(goodsId:String?,viewModelStoreOwner: ViewModelStoreOwner):DetailViewModel{
            return ViewModelProvider(viewModelStoreOwner,DetailViewModelFactory(goodsId)).get(DetailViewModel::class.java)
        }
    }
}
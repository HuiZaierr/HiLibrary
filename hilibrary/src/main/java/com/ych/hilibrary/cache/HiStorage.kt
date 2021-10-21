package com.ych.hilibrary.cache

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

/**
 * TODO：公共的数据存数，将所有数据都转为二进制数据进行保存。在反序列化的时候将二进制数据转为Object
 *     作用：为页面实现数据缓存的能力，加快首次页面渲染速度。
 */
object HiStorage{

    fun <T> savaCache(key:String,body:T){
        val cache = Cache()
        cache.key = key
        //将我们需要保存的数据转换为二进制数据
        cache.data = toByteArray(body)
        CacheDatabase.get().cacheDao.saveCache(cache)
    }

    fun <T> getCache(key: String):T?{
        var cache = CacheDatabase.get().cacheDao.getCache(key)
        return (if (cache?.data!=null){
                toObject(cache.data)
              }else null)as? T
    }

    fun deleteCache(key:String){
        var cache = Cache()
        cache.key = key
         CacheDatabase.get().cacheDao.deleteCache(cache)
    }


    /**
     * 将数据转换为ByteArray
     */
    private fun <T> toByteArray(body: T): ByteArray? {
        var baos:ByteArrayOutputStream? = null
        var oos:ObjectOutputStream? = null
        try {
            baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(body)
            oos.flush()
            return baos.toByteArray()
        }catch (ex:Exception){
            ex.printStackTrace()
        }finally {
            baos?.close()
            oos?.close()
        }
        return ByteArray(0)
    }


    /**
     * TODO:将二进制数组转换为Object对象
     */
    private fun toObject(data:ByteArray?):Any?{
        var bais:ByteArrayInputStream? = null
        var ois:ObjectInputStream? = null
        try {
            bais = ByteArrayInputStream(data)
            ois = ObjectInputStream(bais)
            return ois.readObject()
        }catch (ex:Exception){
            ex.printStackTrace()
        }finally {
            bais?.close()
            ois?.close()
        }
        return null
    }
}
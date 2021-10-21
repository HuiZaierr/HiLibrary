package com.ych.hilibrary.cache

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * TODO：数据访问对象，一般定义为interface，也可以定义为abstract类
 */
@Dao
interface CacheDao {
    /**
     * 表示插入一条数据，如果当插入的Cache对象的cache_key已经存在那么就会发生冲突
     *  entity：表示对哪张表进行操作,也可以不指定，它会根据你传入的对象来找到你要操作的那张表
     *  onConflict：表示发生冲突如何处理。
     */
    @Insert(entity = Cache::class,onConflict = OnConflictStrategy.REPLACE)
    fun saveCache(cache: Cache):Long

    @Query("select * from cache where 'key'=:key")
    fun getCache(key:String):Cache?


    @Delete(entity = Cache::class)
    fun deleteCache(cache:Cache)
}
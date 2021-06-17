package com.ych.hilibrary.cache

import android.graphics.Bitmap
import androidx.annotation.NonNull
import androidx.room.*

/**
 * TODO:Room数据库表创建
 *    1.表的创建使用注解@Entity(tableName = "table_cache")设置表名，如果不设置，默认为类名
 *    2.声明表的字段，需要添加主键@PrimaryKey注解。可以设置autoGenerate = true，表示字段值
 *      是否让数据库自动生成。比如int，long可以设置为true
 *
 *    注解解释：
 *     1.@Entity()：声明数据库中的表
 *        参数：
 *          tableName= "表名"
 *     2.@PrimaryKey()：给表的字段添加主键。
 *        参数：
 *          autoGenerate = true :表示字段值是否由数据库自动生成
 *     3.@ColumnInfo()：为表的字段修改信息。
 *        参数：
 *          name = "字段名称" :表示修改字段名称
 *          defaultValue = "设置默认值" ：表示为字段设置默认值
 *     4.@Ignore()：为表字段添加，表示忽略该字段，数据库表中不生成列名。
 *     5.@Embedded：为表字段添加，表示如果相让内嵌对象中的字段也一同映射成数据库表的字段，可以用这个注解
 *                  但是该对象也必须使用@Entity注解，并且拥有一个不为空的主键。
 *
 */
@Entity(tableName = "cache")
class Cache {

    /**
     * autoGenerate：cache_key的值是否有数据库自动生成,因为他是一个字符串需要我们手动设置
     */
    @PrimaryKey(autoGenerate = false)
    @NonNull
    var key:String = ""

    var data:ByteArray? =null
}
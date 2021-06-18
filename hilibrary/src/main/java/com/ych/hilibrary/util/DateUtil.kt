package com.ych.hilibrary.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * TODO：时间格式转换
 */
object DateUtil {

    private const val MD_FORMAT = "MM-dd"
    private const val DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss"

    /**
     * TODO:将Date时间格式转换为MM-dd的格式
     */
    fun getMDDate(date:Date):String{
        val sdf = SimpleDateFormat(MD_FORMAT,Locale.CHINA)
        return sdf.format(date)
    }

    /**
     * todo:将字符串的时间转换为MM-dd的格式
     */
    fun getMDDate(dateString: String):String{
        val sdf = SimpleDateFormat(DEFAULT_FORMAT,Locale.CHINA)
        return getMDDate(sdf.parse(dateString))
    }
}
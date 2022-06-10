package com.ych.hiui.banner

import android.content.Context
import android.widget.FrameLayout
import com.ych.hiui.banner.core.IHiBanner

class HiBanner(context: Context) : FrameLayout(context) {

    var a:Int = 1
    var b:Int = 2


    fun test(){
        var adnResult = a and b              //java的 a & b
        var orResult = a or b                //java的 a | b
        var xorResult = a xor b              //java的 a ^ b
        var rightShift = a shr 2             //java的 a >> 2
        var leftShift = a shl  2             //java的 a << 2
        var unsignedRightShift = a ushr 2    //java的 a << 2
        var strong = 1 as Int                //java的强转 (Int)1
        var typeCheck  = 1 is Int            //java的类型判断 instanceof
    }
}
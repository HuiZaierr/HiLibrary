package com.ych.hilibrary.design_mode.builder

class Pen{
    var color:String = "white"
    var width:Float = 0.7f
    var round:Boolean = false

    fun write(){
        println("当前颜色：$color")
        println("当前画笔宽度：$width")
        println("当前画笔是否为圆角：$round")
    }
}
package com.ych.hilibrary.design_mode.builder

/**
 * TODO：Kotlin实现建造者模式，并且测试对象的扩展方法。
 *     run{}
 *     with{}
 *     apply{}
 *     also{}
 *     let{}
 *     takeIf{}
 *     repeat{}
 *
 */
fun main() {
    val penJava = PenJava.Builder().color("yellow!").width(1f).round(false).build()
    penJava.write()

    println("--------------------")

    //Kotlin通过with方法来进行实现。他首先接收一个对象，和一个对象的方法。
    var pen = Pen()
    with(pen){
        write()
    }

    println("--------------------")

    pen.apply {
        color = "black"
        width = 1.5f
        round = true
        write()
    }

    println("--------------------")

    var numbers = mutableListOf("1","2","3")
    with(numbers,block = {
        println("The first element is ${first()}, the last element is ${last()}")
    })
}
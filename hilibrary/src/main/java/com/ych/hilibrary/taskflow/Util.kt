package com.ych.hilibrary.taskflow

object Util {

    fun  compareTask(task1: Task,task2: Task):Int{
        if (task1.priority < task2.priority){
            return 1
        }
        if (task1.priority > task2.priority){
            return -1
        }
        return 0
    }
}
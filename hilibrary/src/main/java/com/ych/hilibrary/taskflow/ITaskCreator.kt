package com.ych.hilibrary.taskflow


interface ITaskCreator {
    fun createTask(taskName: String): Task
}
package com.ych.taskflow

import com.ych.hilibrary.log.HiLog
import com.ych.hilibrary.taskflow.ITaskCreator
import com.ych.hilibrary.taskflow.Project
import com.ych.hilibrary.taskflow.Task
import com.ych.hilibrary.taskflow.TaskFlowManager

object TaskStarUp {
    const val TAG = "TaskStarUp"

    const val TASK_BLOCK_1 = "block_task_1"
    const val TASK_BLOCK_2 = "block_task_2"
    const val TASK_BLOCK_3 = "block_task_3"

    const val TASK_ASYNC_1 = "async_task_1"
    const val TASK_ASYNC_2 = "async_task_2"
    const val TASK_ASYNC_3 = "async_task_3"


    fun start(){
        HiLog.et(TAG,"start")
        val project = Project.Builder(TAG,createTaskCreator())
            .add(TASK_BLOCK_1)
            .add(TASK_BLOCK_2)
            .add(TASK_BLOCK_3)
            .add(TASK_ASYNC_1).dependOn(TASK_BLOCK_1)
            .add(TASK_ASYNC_2).dependOn(TASK_BLOCK_2)
            .add(TASK_ASYNC_3).dependOn(TASK_BLOCK_3)
            .build()

        //当TASK_BLOCK_1，TASK_BLOCK_2，TASK_BLOCK_3执行完成后才能进入LaunchActivitu
        TaskFlowManager
            .addBlockTask(TASK_BLOCK_1)
            .addBlockTask(TASK_BLOCK_2)
            .addBlockTask(TASK_BLOCK_3)
            .start(project)

        HiLog.et(TAG,"end")
    }



    private fun createTaskCreator(): ITaskCreator {
        return object: ITaskCreator{
            override fun createTask(taskName: String): Task {
                when(taskName){
                    TASK_ASYNC_1 -> return createTask(taskName,true)
                    TASK_ASYNC_2 -> return createTask(taskName,true)
                    TASK_ASYNC_3 -> return createTask(taskName,true)

                    TASK_BLOCK_1 -> return createTask(taskName,true)
                    TASK_BLOCK_2 -> return createTask(taskName,true)
                    TASK_BLOCK_3 -> return createTask(taskName,true)
                }
                return createTask("default",false)
            }

        }

    }

    private fun createTask(taskName: String, isAsync: Boolean): Task {
        return object : Task(taskName,isAsync) {
            override fun run(id: String) {
                Thread.sleep(if (isAsync) 2000 else 1000)
                HiLog.et(TAG,"task $taskName,$isAsync,finished")
            }

        }

    }

}
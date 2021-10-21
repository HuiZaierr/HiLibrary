package com.ych.taskflow

import com.ych.hilibrary.log.HiLog
import com.ych.hilibrary.taskflow.ITaskCreator
import com.ych.hilibrary.taskflow.Project
import com.ych.hilibrary.taskflow.Task
import com.ych.hilibrary.taskflow.TaskFlowManager

/**
 * TODO:开启异步任务，执行。（可以设置任务的依赖关系）
 */
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
             //添加任务，添加依赖（也就是说TASK_BLOCK_1执行完成后才会执行TASK_ASYNC_1）
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


    /**
     * 构建Project是根据TASK名称来构建Task任务
     */
    private fun createTaskCreator(): ITaskCreator {
        return object: ITaskCreator{
            override fun createTask(taskName: String): Task {
                when(taskName){
                    TASK_ASYNC_1 -> return createTask(taskName,true)
                    TASK_ASYNC_2 -> return createTask(taskName,true)
                    TASK_ASYNC_3 -> return createTask(taskName,true)

                    TASK_BLOCK_1 -> return createTask(taskName,false)
                    TASK_BLOCK_2 -> return createTask(taskName,false)
                    TASK_BLOCK_3 -> return createTask(taskName,false)
                }
                return createTask("default",false)
            }

        }

    }

    /**
     * 创建Task任务。根据任务来执行要初始化的任务
     */
    private fun createTask(taskName: String, isAsync: Boolean): Task {
        return object : Task(taskName,isAsync) {
            override fun run(id: String) {
                //我们处理，如果是异步任务让他耗时2s，否则1s
//                Thread.sleep(if (isAsync) 2000 else 1000)
                Thread.sleep(if (isAsync) 100 else 50)
                HiLog.et(TAG,"task $taskName,$isAsync,finished")
            }

        }

    }

}
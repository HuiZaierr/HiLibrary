package com.ych.hilibrary.log;

import com.google.gson.JsonParser;

public abstract class HiLogConfig {
    //最大长度
    public static int MAX_LEN = 512;
    //线程格式化器
    static HiThreadFormatter HI_THREAD_FORMATTER = new HiThreadFormatter();
    //堆栈格式化器
    static HiStackTraceFormatter HI_STACK_TRACE_FORMATTER = new HiStackTraceFormatter();
    public JsonParser injectJsonParser(){
        return null;
    }

    /**
     * 全局的Tag，当我们不设置的时候
     * @return
     */
    public String getGlobalTag(){
        return "HiLog";
    }

    /**
     * 默认是启用的
     * @return
     */
    public boolean enable(){
        return true;
    }

    /**
     * 是否打印线程信息
     * @return
     */
    public boolean includeThread(){
        return false;
    }

    /**
     * 堆栈的深度默认为5
     * @return
     */
    public int stackTraceDepth(){
        return 5;
    }

    public HiLogPrinter[] printers(){
        return null;
    }

    public interface JsonParser{
        String toJson(Object src);
    }
}

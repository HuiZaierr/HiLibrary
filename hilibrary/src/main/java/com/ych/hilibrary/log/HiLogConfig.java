package com.ych.hilibrary.log;

public abstract class HiLogConfig {
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

}

package com.ych.hilibrary.log;

/**
 * TODO：线程的格式化器
 */
public class HiThreadFormatter implements HiLogFormatter<Thread>{
    @Override
    public String format(Thread data) {
        return "Thread："+data.getName();
    }
}

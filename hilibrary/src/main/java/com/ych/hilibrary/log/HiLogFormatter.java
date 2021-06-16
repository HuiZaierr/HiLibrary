package com.ych.hilibrary.log;

public interface HiLogFormatter<T> {

    String format(T data);
}

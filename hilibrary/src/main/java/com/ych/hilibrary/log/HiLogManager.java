package com.ych.hilibrary.log;

/**
 * TODO:它是HiLog的管理类，它是单例，他持有HiLogConfig
 */
public class HiLogManager {

    private HiLogConfig config;
    private static HiLogManager instance;

    private HiLogManager(HiLogConfig config){
        this.config = config;
    }

    public static HiLogManager getInstance(){
        return instance;
    }

    public static void init(HiLogConfig config){
        instance = new HiLogManager(config);
    }

    public HiLogConfig getConfig(){
        return config;
    }
}

package com.ych.hilibrary.log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO:它是HiLog的管理类，它是单例，他持有HiLogConfig
 */
public class HiLogManager {

    private HiLogConfig config;
    private static HiLogManager instance;
    private List<HiLogPrinter> printers = new ArrayList<>();

    private HiLogManager(HiLogConfig config,HiLogPrinter[] printers){
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }

    public static HiLogManager getInstance(){
        return instance;
    }

    public static void init(HiLogConfig config,HiLogPrinter... printers){
        instance = new HiLogManager(config,printers);
    }

    public HiLogConfig getConfig(){
        return config;
    }

    public List<HiLogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(HiLogPrinter printer) {
        printers.add(printer);
    }

    public void removePrinter(HiLogPrinter printer){
        if (printer!=null){
            printers.remove(printer);
        }
    }
}

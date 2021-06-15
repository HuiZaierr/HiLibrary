package com.ych.hilibrary.log;

import android.util.Log;

import androidx.annotation.NonNull;

public class HiLog {

    public static void v(Object... contents){
        log(HiLogType.V,contents);
    }

    public static void vt(String tag,Object... contents){
        log(HiLogType.V,tag,contents);
    }

    public static void d(Object... contents){
        log(HiLogType.D,contents);
    }

    public static void dt(String tag,Object... contents){
        log(HiLogType.D,tag,contents);
    }

    public static void i(Object... contents){
        log(HiLogType.I,contents);
    }

    public static void it(String tag,Object... contents){
        log(HiLogType.I,tag,contents);
    }

    public static void w(Object... contents){
        log(HiLogType.W,contents);
    }

    public static void wt(String tag,Object... contents){
        log(HiLogType.W,tag,contents);
    }

    public static void e(Object... contents){
        log(HiLogType.E,contents);
    }

    public static void et(String tag,Object... contents){
        log(HiLogType.E,tag,contents);
    }

    public static void a(Object... contents){
        log(HiLogType.A,contents);
    }

    public static void at(String tag,Object... contents){
        log(HiLogType.A,tag,contents);
    }

    private static void log(@HiLogType.TYPE int type, Object... contents) {
        log(type,HiLogManager.getInstance().getConfig().getGlobalTag(),contents);
    }

    private static void log(@HiLogType.TYPE int type, @NonNull String tag, Object... contents) {
        log(HiLogManager.getInstance().getConfig(),type,tag,contents);
    }

    private static void log(HiLogConfig config,@HiLogType.TYPE int type, @NonNull String tag, Object... contents) {
        //未开启直接return
        if (!config.enable()){
            return;
        }

        StringBuilder sb = new StringBuilder();
        String body = parseBody(contents);
        sb.append(body);
        Log.println(type,tag,sb.toString());
    }

    private static String parseBody(Object[] contents) {
        StringBuilder sb = new StringBuilder();
        for (Object o : contents) {
            sb.append(o.toString()).append(";");
        }
        if (sb.toString().length()>0){
            sb.deleteCharAt(sb.toString().length() - 1);    //删除最后一个分号
        }
        return sb.toString();
    }
}

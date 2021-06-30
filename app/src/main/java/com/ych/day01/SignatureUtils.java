package com.ych.day01;

public class SignatureUtils {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public static native String signatureParams(String params);

}

package com.self.tools;


import android.util.Log;

/**
 * Created by sh-xiayf on 15-4-27.
 * this file use for process log
 */
public class LogUtils {

    private static final boolean DEBUG = true;

    public static void writeLog(String...args){
        if (args.length != 2){
            return;
        }
        if(!DEBUG){
            return;
        }
        Log.e(args[0],args[1]);
    }

    public static void writeException(Exception e){
        if(!DEBUG){
            return;
        }
        e.printStackTrace();
    }

}

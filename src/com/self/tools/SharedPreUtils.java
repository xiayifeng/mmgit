package com.self.tools;

import android.content.Context;

/**
 * Created by sh-xiayf on 15-4-27.
 */
public class SharedPreUtils {

    private static final String SP_NAME = "sp_info";



    /* take value from  sharedpreference */

    public static String take(Context myContext,String key,String defaultValue){
        return myContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                .getString(key, defaultValue);
    }

    public static int take(Context myContext,String key,int defaultValue){
        return myContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                .getInt(key, defaultValue);
    }

    public static boolean take(Context myContext,String key,boolean defaultValue){
        return myContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                .getBoolean(key, defaultValue);
    }

    public static long take(Context myContext,String key,long defaultValue){
        return myContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                .getLong(key, defaultValue);
    }

    public static float take(Context myContext,String key,float defaultValue){
        return myContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                .getFloat(key, defaultValue);
    }

    /* save value in sharedpreference */

    public static void save(Context myContext,String key, String value){
        myContext.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE)
                .edit().putString(key,value).commit();
    }

    public static void save(Context myContext,String key, boolean value){
        myContext.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE)
                .edit().putBoolean(key, value).commit();
    }

    public static void save(Context myContext,String key, int value){
        myContext.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE)
                .edit().putInt(key, value).commit();
    }

    public static void save(Context myContext,String key, long value){
        myContext.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE)
                .edit().putLong(key, value).commit();
    }

    public static void save(Context myContext,String key, float value){
        myContext.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE)
                .edit().putFloat(key, value).commit();
    }

}

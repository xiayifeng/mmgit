package com.self.tools;

import android.content.Context;

/**
 * Created by sh-xiayf on 15-4-27.
 */
public class FileUtils {

    public String getCachePath(Context myContext){
        try{
            return myContext.getCacheDir().getAbsolutePath();
        }catch (Exception e){
            LogUtils.writeException(e);
            return "/data/data/"+myContext.getPackageName()+"/cache";
        }
    }

    public String getExendCachePath(Context myContext){
        try{
            return myContext.getExternalCacheDir().getAbsolutePath();
        }catch (Exception e){
            LogUtils.writeException(e);
            return getCachePath(myContext);
        }
    }



}

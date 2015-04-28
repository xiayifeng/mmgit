package com.self.tools;

import android.content.Context;

import java.io.*;

/**
 * Created by sh-xiayf on 15-4-27.
 */
public class FileUtils {

    private FileUtils(){

    }

    private static FileUtils instances;

    public static FileUtils getInstances(){
        if (instances == null){
            instances = new FileUtils();
        }

        return instances;
    }

    private String getCachePath(Context myContext){
        try{
            return myContext.getCacheDir().getAbsolutePath();
        }catch (Exception e){
            LogUtils.writeException(e);
            return "/data/data/"+myContext.getPackageName()+"/cache";
        }
    }

    private String getExendCachePath(Context myContext){
        try{
            return myContext.getExternalCacheDir().getAbsolutePath();
        }catch (Exception e){
            LogUtils.writeException(e);
            return getCachePath(myContext);
        }
    }

    public void writeFile(String path,String message){
        File dstFile = new File(path);
        if (dstFile.exists()){
            dstFile.delete();
        }
        try {
            dstFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(dstFile);
            fos.write(message.getBytes("utf-8"));
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String path){
        File dstFile = new File(path);
        if (dstFile.exists()){
            dstFile.delete();
        }
        try {
            FileInputStream fis = new FileInputStream(dstFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int rs = -1;
            byte[] buf = new byte[1024];
            while ((rs = fis.read(buf)) != -1){
                baos.write(buf,0,rs);
            }
            baos.flush();
            fis.close();
            String content = new String(baos.toByteArray());
            return content;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}

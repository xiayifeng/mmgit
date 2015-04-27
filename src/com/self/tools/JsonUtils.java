package com.self.tools;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by sh-xiayf on 15-4-27.
 * this file use for process json
 */
public class JsonUtils {

    /**
     * json 2 bean
     * @param jObject
     * @param clazz
     * @return
     */
    public static Object parseJson(JSONObject jObject,Class<?> clazz){
        try{
            Object obj = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field tmp : fields){
                try{
                    if (tmp.getType().getName().equals("int")){
                        tmp.setInt(obj,jObject.getInt(tmp.getName()));
                    }else if(tmp.getType().getName().equals("boolean")){
                        tmp.setBoolean(obj,jObject.getBoolean(tmp.getName()));
                    }else {
                        tmp.set(obj,jObject.get(tmp.getName()));
                    }
                }catch (Exception e){
                    LogUtils.writeException(e);
                }
            }
            return obj;
        }catch (Exception e){
            LogUtils.writeException(e);
        }
        return null;
    }

    /**
     * string 2 bean
     * @param json
     * @param clazz
     * @return
     */
    public static Object parseJson(String json,Class<?> clazz){
        try{
            JSONObject jObject = new JSONObject(json);
            return parseJson(jObject,clazz);
        }catch (Exception e){
            LogUtils.writeException(e);
        }
        return null;
    }

    /**
     * json 2 string
     * @param obj
     * @return
     */
    public static String parseJson2String(Object obj){
        try{
            JSONObject jObject = new JSONObject();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field tmp : fields){
                tmp.setAccessible(true);
                jObject.put(tmp.getName(),tmp.get(obj));
            }
            return jObject.toString();
        }catch (Exception e){
            LogUtils.writeException(e);
        }
        return null;
    }

}

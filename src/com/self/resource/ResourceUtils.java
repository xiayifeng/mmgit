package com.self.resource;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import com.self.tools.LogUtils;

import java.lang.reflect.Field;

/**
 * Created by sh-xiayf on 15-4-27.
 * this file use for process resources
 */
public class ResourceUtils {

    private ResourceUtils(){
        this.mResources = myContext.getResources();
        this.PACKAGE_NAME = myContext.getPackageName();
    }

    private static String PACKAGE_NAME;
    private static Resources mResources;
    private Context myContext;


    private static ResourceUtils instances;

    public static ResourceUtils getInstances(){
        if (instances == null){
            instances = new ResourceUtils();
        }

        return instances;
    }

    public int getResID(ResType mResType,String resName) {
        switch (mResType){
            case id:
                return mResources.getIdentifier(resName,"id",PACKAGE_NAME);

            case string:
                return mResources.getIdentifier(resName,"string",PACKAGE_NAME);

            case layout:
                return mResources.getIdentifier(resName,"layout",PACKAGE_NAME);

            case drawable:
                return mResources.getIdentifier(resName,"drawable",PACKAGE_NAME);

            case color:
                return mResources.getIdentifier(resName,"color",PACKAGE_NAME);

            default:
                return -1;
        }
    }

    public View getParentView(Context myContext,String name){
        int layoutID = getResID(ResType.layout,name);
        if(layoutID <= 0){
            return null;
        }

        return LayoutInflater.from(myContext).inflate(mResources.getLayout(layoutID),null);
    }

    public void initViews(View parentView,Object obj){
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field tmp : fields){
            tmp.setAccessible(true);
            try{
                tmp.set(obj,parentView.findViewById(getResID(ResType.id,tmp.getName())));
            } catch (Exception e){
                LogUtils.writeException(e);
            }
        }
    }

    public String getString(String name){
        int stringID = getResID(ResType.string,name);
        if(stringID <= 0){
            return null;
        }

        return mResources.getString(stringID);
    }

    public int getColor(String name){
        int colorID = getResID(ResType.color,name);
        if (colorID <= 0){
            return -1;
        }

        return mResources.getColor(colorID);
    }

    public Drawable getDrawable(String name){
        int drawableID = getResID(ResType.drawable,name);
        if (drawableID <= 0){
            return null;
        }

        return mResources.getDrawable(drawableID);
    }

}

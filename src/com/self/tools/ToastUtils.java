package com.self.tools;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by sh-xiayf on 15-4-27.
 */
public class ToastUtils {

    public static void showToast(final Context myContext, final String message,Handler uiHandler){
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(myContext,message,Toast.LENGTH_SHORT).show();
            }
        });
    }

}

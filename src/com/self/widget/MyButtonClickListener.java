package com.self.widget;

import android.view.View;

/**
 * Created by sh-xiayf on 15-4-28.
 */
public class MyButtonClickListener implements View.OnClickListener{

    public interface MyProxyListener{
        void onUserfulClick(View v);
        void onUnUserfulClick(View v);
    }

    private MyProxyListener callback;

    public MyButtonClickListener(MyProxyListener myProxyListener){
        this.callback = myProxyListener;
    }

    private long lastclicktime = 0;

    private static long USERFUL_TIME = 1000;

    @Override
    public void onClick(View view) {
        long currentClickTime = System.currentTimeMillis();
        if (currentClickTime - lastclicktime > USERFUL_TIME){
            lastclicktime = currentClickTime;
            callback.onUserfulClick(view);
        }else{
            callback.onUnUserfulClick(view);
        }
    }

}

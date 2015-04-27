package com.self.net;

/**
 * Created by sh-xiayf on 15-4-27.
 * this file for user request
 */
public class HttpProcess {

    private HttpNet mHttpNet;

    public HttpProcess(){
        mHttpNet = new HttpNet();
    }

    public void requestGet(final RequestListener listener, final String...args){
        if(args.length != 2){
            listener.onCompleted(null);
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                listener.onCompleted(mHttpNet.requestGet(args[0], args[1]));
            }
        }).start();
    }


    public void requestPost(final RequestListener listener, final String...args){
        if (args.length != 2){
            listener.onCompleted(null);
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                listener.onCompleted(mHttpNet.requestPost(args[0],args[1]));
            }
        }).start();
    }


}

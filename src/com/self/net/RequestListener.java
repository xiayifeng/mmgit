package com.self.net;

import java.io.InputStream;

/**
 * Created by sh-xiayf on 15-4-27.
 * interface for call back
 */
public interface RequestListener {

    public void onCompleted(InputStream response);
}

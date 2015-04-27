package com.self.net;

import android.text.TextUtils;
import com.self.tools.LogUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by sh-xiayf on 15-4-27.
 * this file use for http request
 */
public class HttpNet {

    private static final int HTTP_TIMEOUT = 30 * 1000;


    public InputStream requestGet(String url,Object params){

        try {
            String finalurl = url + getGetString(params);

            HttpClient mHttpClient = getHttpClient2();

            HttpParams mHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(mHttpParams,HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(mHttpParams,HTTP_TIMEOUT);
            HttpProtocolParams.setContentCharset(mHttpParams,"utf-8");

            HttpGet mHttpGet = new HttpGet(finalurl);
            mHttpGet.setParams(mHttpParams);


            HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet);
            if (mHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return mHttpResponse.getEntity().getContent();
            }
        } catch (Exception e) {
            LogUtils.writeException(e);
        }

        return null;
    }

    public InputStream requestPost(String url,String params){

        try {
            HttpClient mHttpClient = getHttpClient2();
            mHttpClient.getParams().setParameter("http.protocol.content-charset", HTTP.UTF_8);

            HttpParams mHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(mHttpParams,HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(mHttpParams,HTTP_TIMEOUT);
            HttpProtocolParams.setContentCharset(mHttpParams,"utf-8");
            HttpProtocolParams.setHttpElementCharset(mHttpParams, HTTP.UTF_8);

            HttpPost mHttpPost = new HttpPost(url);
            mHttpPost.setParams(mHttpParams);
            mHttpPost.addHeader("Content-Type", "application/json;charset=utf-8");

            mHttpPost.setEntity(new StringEntity(params,"utf-8"));
            HttpResponse mHttpResponse = mHttpClient.execute(mHttpPost);
            if (mHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                return mHttpResponse.getEntity().getContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getGetString(Object params){
        StringBuilder sb = new StringBuilder();
        boolean isfirst = true;

            Field[] fields = params.getClass().getDeclaredFields();
            for (Field tmp : fields){
                try{
                    if (isfirst){

                    }else{
                        sb.append("&");
                    }
                    tmp.setAccessible(true);
                    String obj = (String) tmp.get(params);
                    if (TextUtils.isEmpty(obj)){
                        sb.append(tmp.getName()).append("=").append(obj);
                    }
                }catch (Exception e){
                    LogUtils.writeException(e);
                }
            }
        return sb.toString();
    }

    private HttpClient getHttpClient2(){
        try {
            KeyStore trusStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trusStore.load(null,null);
            SSLSocketFactory sf = new MySSLSocketFactory(trusStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            HttpConnectionParams.setConnectionTimeout(params, 30000);
            HttpConnectionParams.setSoTimeout(params, 30000);
            ConnManagerParams.setTimeout(params, 30000);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    class MySSLSocketFactory extends SSLSocketFactory{

        SSLContext sslContext = SSLContext.getInstance("TLS");

        public MySSLSocketFactory(KeyStore truststore)
                throws NoSuchAlgorithmException, KeyManagementException,
                KeyStoreException, UnrecoverableKeyException {
            super(truststore);
            TrustManager tm = new X509TrustManager() {

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {

                }

                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {

                }
            };

            sslContext.init(null, new TrustManager[]{tm}, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port,
                                   boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }

    }

}

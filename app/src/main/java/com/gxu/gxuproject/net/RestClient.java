package com.gxu.gxuproject.net;

/**
 * Created by lcw on 2015/8/15 0015.
 */
import com.loopj.android.http.*;
public class RestClient {
    //private static final String BASE_URL ="http://fukangyun.com/twsadmin/index.php/home/index/";
    //private static final String BASE_URL ="http://192.168.56.1/gxuexam/index.php/Home/Interface/";
    private static final String BASE_URL ="http://210.36.22.72/ZSJS/index.php/Admin/Interface/";
    private static AsyncHttpClient client = new AsyncHttpClient();
    static {
        client.setTimeout(30000);
    }
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }



    public static void postUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {

        String url = BASE_URL + relativeUrl;
        return url;
    }
}

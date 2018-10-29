package com.gank.chen.http;

import android.util.Log;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author chen
 * @date 2017/12/17
 */

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        //chain里包含了request和response
        Request request = chain.request();
        //请求发起的时间
        long t1 = System.nanoTime();
        Log.i("gank", String.format("发送请求 %s %n%s",
                request.url(), (chain.request().body()) == null ? "" : Objects.requireNonNull(chain.request().body()).toString()));

        Response response = chain.proceed(request);
        //收到响应的时间
        long t2 = System.nanoTime();


        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Log.i("gank", String.format(Locale.getDefault(), "接收响应: [%s] %n返回json:%n【%s】 %.1fms",
                response.request().url(),
                responseBody.string(),
                (t2 - t1) / 1e6d));
        return response;
    }
}

package com.gank.chen.http;

import android.content.Context;
import android.text.TextUtils;

import com.gank.chen.common.ConstantMap;
import com.gank.chen.common.MyApplication;
import com.gank.chen.http.exception.FuncFlowableException;
import com.gank.chen.http.exception.FuncObservableException;
import com.gank.chen.http.exception.HandleBooleanFuc;
import com.gank.chen.http.exception.HandleFuc;
import com.gank.chen.http.exception.HandleGankFuc;
import com.gank.chen.mvp.model.BaseGankModel;
import com.gank.chen.mvp.model.BaseModel;
import com.gank.chen.util.LogUtil;
import com.gank.chen.util.NetworkHelper;
import com.gank.chen.util.SharePreferenceUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DefaultObserver;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author chen
 * @date 2017/12/17
 */

public class ApiRetrofit {
    private static final int DEFAULT_TIMEOUT = 20;
    private ApiUtil apiUtil;
    static final String BASE_URL = ApiConfig.HOST;
    static final String BASE_WAN_URL = ApiConfig.HOST_DUANZI;

    public ApiUtil getApi() {
        return apiUtil;
    }

    private Interceptor setPublicParameterInterceptor() {
        //公共参数
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                String method = originalRequest.method();
                Headers headers = originalRequest.headers();
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
                        .addQueryParameter("from", "android")
                        .addQueryParameter("version", "1.0.0")
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    private Interceptor setheaderinterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
//                        .header("Authorization", "MzY2NTkgMiBIZ2pZVVJ0UA==")
                        .header("loginUserName", "")
                        .header("loginUserPassword", "")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }

    public class AddCookiesInterceptor implements Interceptor {


        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            String userName = SharePreferenceUtil.getString(ConstantMap.LOGINUSERNAME, "");
            String userPassword = SharePreferenceUtil.getString(ConstantMap.LOGINUSERPASSWORD, "");
            if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPassword)) {
                builder.addHeader("Cookie", userName);
                builder.addHeader("Cookie", userPassword);
            }
            return chain.proceed(builder.build());
        }
    }

    ApiRetrofit() {
        File httpCacheDirectory = new File(MyApplication.mContext.getCacheDir(), "okhttp");
        // 10 MiB
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);


        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                //错误重连
                .retryOnConnectionFailure(true)
//                .addInterceptor(setPublicParameterInterceptor())
                .addInterceptor(setheaderinterceptor())
                .addInterceptor(new BaseUrlInterceptor())
                .addInterceptor(new LoggingInterceptor())
//                .addNetworkInterceptor(rewriteCacheControlInterceptor)
                .addInterceptor(rewriteCacheControlInterceptor)
                .addInterceptor(new AddCookiesInterceptor())

                .cache(cache).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiUtil = retrofit.create(ApiUtil.class);
    }

    private static class BaseUrlInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            //获取request
            Request request = chain.request();
            //从request中获取原有的HttpUrl实例oldHttpUrl
            HttpUrl oldHttpUrl = request.url();
            //获取request的创建者builder
            Request.Builder builder = request.newBuilder();
            //从request中获取headers，通过给定的键url_name
            List<String> headerValues = request.headers("use_host");
            if (headerValues != null && headerValues.size() > 0) {
                //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
                builder.removeHeader("use_host");
                //匹配获得新的BaseUrl
                String headerValue = headerValues.get(0);
                HttpUrl newBaseUrl = null;
                if ("gank".equals(headerValue)) {
                    newBaseUrl = HttpUrl.parse(BASE_URL);
                } else if ("wan".equals(headerValue)) {
                    newBaseUrl = HttpUrl.parse(BASE_WAN_URL);
                } else {

                    newBaseUrl = oldHttpUrl;
                }
                //重建新的HttpUrl，修改需要修改的url部分
                HttpUrl newFullUrl = oldHttpUrl
                        .newBuilder()
                        //更换网络协议
                        .scheme(newBaseUrl.scheme())
                        //更换主机名
                        .host(newBaseUrl.host())
                        //更换端口
                        .port(newBaseUrl.port())
//                        .removePathSegment(0)//移除第一个参数
                        .build();
                //重建这个request，通过builder.url(newFullUrl).build()；
                // 然后返回一个response至此结束修改
                return chain.proceed(builder.url(newFullUrl).build());
            }
            return chain.proceed(request);
        }
    }

    /**
     * cache
     */
    private Interceptor rewriteCacheControlInterceptor = chain -> {
        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
        cacheBuilder.maxStale(7, TimeUnit.DAYS);
        CacheControl cacheControl = cacheBuilder.build();
        Request request = chain.request();
        if (!NetworkHelper.isNetworkAvailable(MyApplication.mContext)) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetworkHelper.isNetworkAvailable(MyApplication.mContext)) {
            int maxAge = 0;
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28;
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };

    public static <T> void setFlowableSubscribe(Flowable<BaseGankModel<T>> observable, FlowableSubscriber<T> observer) {
        observable.compose(ApiScheduler.getFlowableScheduler())
                .onErrorResumeNext(new FuncFlowableException<>())
                .map(new HandleGankFuc<>())
                .subscribe(observer);

    }

    public static <T> void setObservableSubscribe(Observable<BaseModel<T>> observable, Observer<T> observer) {
        observable.compose(ApiScheduler.getObservableScheduler())
                .onErrorResumeNext(new FuncObservableException<>())
                .map(new HandleFuc<>())
                .subscribe(observer);
    }

    public static void setObservableBooleanSubscribe(Observable<BaseModel> observable, Observer<Boolean> observer) {
        observable.compose(ApiScheduler.getObservableScheduler())
                .onErrorResumeNext(new FuncObservableException<>())
                .map(new HandleBooleanFuc())
                .subscribe(observer);
    }

//    public static <T> void setSpecialObservableSubscribe(Observable<T> observable, Observer<T> observer) {
//        observable.compose(ApiScheduler.getObservableScheduler())
//                .onErrorResumeNext(new FuncObservableException<>())
//                .subscribe(observer);
//    }

}

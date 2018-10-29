package com.gank.chen.http;

import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * @author chen
 * @date 2017/12/17
 */

public class ApiScheduler {
    public static <T>FlowableTransformer<T,T> getFlowableScheduler(){
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static <T>ObservableTransformer<T,T> getObservableScheduler(){
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

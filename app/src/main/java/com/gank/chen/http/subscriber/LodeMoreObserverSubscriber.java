package com.gank.chen.http.subscriber;

import android.content.Context;

import com.gank.chen.http.exception.ApiException;
import com.gank.chen.util.NetworkHelper;
import com.gank.chen.util.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by chen on 2017/12/17.
 */

public abstract class LodeMoreObserverSubscriber<T> implements Observer<T> {
    private Context context;
    private Disposable disposable;

    public LodeMoreObserverSubscriber(Context context) {
        this.context = context;
    }


    @Override
    public void onNext(T t) {
        if (t == null) {
            return;
        }
        onSuccess(t);
    }

    @Override
    public void onError(Throwable t) {
        if (!NetworkHelper.isNetworkAvailable(context)) {
            ToastUtils.showToast(context, "当前无网络连接，请先设置网络!");
        } else {
            ApiException.handleException(context, t);
            ToastUtils.showToast(context, t.getMessage());
        }
        disposable.dispose();
    }

    @Override
    public void onComplete() {
        disposable.dispose();
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    /**
     * 加载成功
     *
     * @return
     */
    public abstract void onSuccess(T t);
}

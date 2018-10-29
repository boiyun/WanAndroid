package com.gank.chen.http.subscriber;

import android.content.Context;
import android.os.SystemClock;

import com.gank.chen.http.exception.ApiException;
import com.gank.chen.util.NetworkHelper;
import com.gank.chen.util.ToastUtils;
import com.gank.chen.widget.StateView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author chen
 * @date 2017/12/17
 */

public abstract class ApiSubscriberObserver<T> implements Observer<T> {
    private Context context;
    private StateView stateView;
    private Disposable disposable;

    public ApiSubscriberObserver(Context context, StateView stateView) {
        this.context = context;
        this.stateView = stateView;
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        stateView.showLoading();
    }

    @Override
    public void onNext(T t) {
        if (t == null) {
            stateView.showEmpty();
            return;
        }
        stateView.showContent();
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
        stateView.showRetry();
        disposable.dispose();
    }

    @Override
    public void onComplete() {
        disposable.dispose();

    }

    /**
     * 加载成功
     *
     * @return
     */
    public abstract void onSuccess(T t);
}

package com.gank.chen.http.subscriber;

import android.content.Context;

import com.gank.chen.http.exception.ApiException;
import com.gank.chen.util.NetworkHelper;
import com.gank.chen.util.ToastUtils;
import com.gank.chen.widget.StateView;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author chen
 * @date 2017/12/17
 */

public abstract class ApiSubscriberFlowable<T> extends ResourceSubscriber<T> {
    private Context context;
    private StateView stateView;

    public ApiSubscriberFlowable(Context context, StateView stateView) {
        this.context = context;
        this.stateView = stateView;
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        dispose();
    }

    @Override
    public void onComplete() {
        dispose();
    }
    /**
     * 加载成功
     *
     * @return
     */
    public abstract void onSuccess(T t);
}

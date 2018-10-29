package com.gank.chen.http.subscriber;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gank.chen.http.exception.ApiException;
import com.gank.chen.util.NetworkHelper;
import com.gank.chen.util.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author chenbo
 */
public abstract class SubscriberObserverProgress<T> implements Observer<T> {
    private Context context;
    private Disposable disposable;
    private MaterialDialog dialog;

    public SubscriberObserverProgress(Context context) {
        this.context = context;

    }


    @Override
    public void onError(Throwable t) {
        if (!NetworkHelper.isNetworkAvailable(context)) {
            ToastUtils.showToast(context, "当前无网络连接，请先设置网络!");
        } else {
            ApiException.handleException(context, t);
            ToastUtils.showToast(context, ApiException.handleException(context, t).getMessage());
        }
        dialog.dismiss();
        disposable.dispose();
    }

    @Override
    public void onComplete() {
        dialog.dismiss();
        disposable.dispose();
    }


    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        dialog = new MaterialDialog.Builder(context)
                .progress(true, 0)
                .content("请稍后")
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .show();

    }

    @Override
    public void onNext(T t) {
        if (t == null) {
            return;
        }
        onSuccess(t);
    }

    /**
     * 加载成功
     *
     * @return
     */
    public abstract void onSuccess(T t);
}

package com.gank.chen.http.subscriber;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gank.chen.http.exception.ApiException;
import com.gank.chen.util.NetworkHelper;
import com.gank.chen.util.ToastUtils;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @author chen
 * @date 2017/12/17
 */

public abstract class SubscriberFlowableProgress<T> extends DisposableSubscriber<T> {
    private Context context;
    private MaterialDialog dialog;

    public SubscriberFlowableProgress(Context context) {
        this.context = context;
    }


    @Override
    protected void onStart() {
        super.onStart();
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

    @Override
    public void onError(Throwable t) {
        if (!NetworkHelper.isNetworkAvailable(context)) {
            ToastUtils.showToast(context, "当前无网络连接，请先设置网络!");
        } else {
            ApiException.handleException(context, t);
            ToastUtils.showToast(context, t.getMessage());
        }
        dialog.dismiss();
        dispose();
    }

    @Override
    public void onComplete() {
        dialog.dismiss();
        dispose();
    }

    /**
     * 加载成功
     *
     * @return
     */
    public abstract void onSuccess(T t);
}

package com.gank.chen.mvp.presenter;

import android.content.Context;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.http.ApiRetrofit;
import com.gank.chen.http.subscriber.SubscriberObserverProgress;
import com.gank.chen.mvp.model.RegisterModel;
import com.gank.chen.mvp.view.ImpMainActivity;
import com.gank.chen.mvp.view.OnLoadSuccessViewImp;

import java.util.HashMap;
import java.util.Map;

/**
 * Creat by chen on 2018/10/18
 * Describe:
 * @author chenbo
 */
public class MainActivityPresenter extends BasePrestener<ImpMainActivity> {
    public void toLogout(Context context) {
        ApiRetrofit.setObservableBooleanSubscribe(apiUtil.toLogout(), new SubscriberObserverProgress<Boolean>(context) {
            @Override
            public void onSuccess(Boolean aBoolean) {
                getView().onLogoutSucess(aBoolean);
            }
        });
    }
}

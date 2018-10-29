package com.gank.chen.mvp.presenter;

import android.content.Context;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.http.ApiRetrofit;
import com.gank.chen.http.subscriber.SubscriberObserverProgress;
import com.gank.chen.mvp.model.RegisterModel;
import com.gank.chen.mvp.view.OnLoadSuccessViewImp;

import java.util.HashMap;
import java.util.Map;

/**
 * Creat by chen on 2018/10/18
 * Describe:
 */
public class RegisterPresenter extends BasePrestener<OnLoadSuccessViewImp<RegisterModel>> {
    public void RegisterNow(Context context, String phone, String password) {
        Map<String, String> map = new HashMap<>(3);
        map.put("username", phone);
        map.put("password", password);
        map.put("repassword", password);

        ApiRetrofit.setObservableSubscribe(apiUtil.Register(map), new SubscriberObserverProgress<RegisterModel>(context) {
            @Override
            public void onSuccess(RegisterModel t) {
                getView().onLoadSucess(t);
            }
        });
    }
}

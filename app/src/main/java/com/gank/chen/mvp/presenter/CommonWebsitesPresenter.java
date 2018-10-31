package com.gank.chen.mvp.presenter;

import android.content.Context;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.http.ApiRetrofit;
import com.gank.chen.http.subscriber.ApiSubscriberObserver;
import com.gank.chen.http.subscriber.SubscriberObserverProgress;
import com.gank.chen.mvp.model.ArticleModel;
import com.gank.chen.mvp.model.CommonWebsiteModel;
import com.gank.chen.mvp.model.RegisterModel;
import com.gank.chen.mvp.view.OnLoadSuccessViewImp;
import com.gank.chen.widget.StateView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creat by chen on 2018/10/31
 * Describe:
 *
 * @author chenbo
 */
public class CommonWebsitesPresenter extends BasePrestener<OnLoadSuccessViewImp<List<CommonWebsiteModel>>> {
    public void getCommonWebsite(Context context, StateView stateView) {
        ApiRetrofit.setObservableSubscribe(apiUtil.toGetCommonWebsite(), new ApiSubscriberObserver<List<CommonWebsiteModel>>(context, stateView) {
            @Override
            public void onSuccess(List<CommonWebsiteModel> commonWebsiteModels) {
                getView().onLoadSucess(commonWebsiteModels);
            }
        });
    }
}

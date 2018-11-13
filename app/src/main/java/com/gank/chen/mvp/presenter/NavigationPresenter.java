package com.gank.chen.mvp.presenter;

import android.content.Context;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.http.ApiRetrofit;
import com.gank.chen.http.subscriber.ApiSubscriberObserver;
import com.gank.chen.mvp.model.NavigationModel;
import com.gank.chen.mvp.view.ImpNavigationFragment;
import com.gank.chen.widget.StateView;

import java.util.List;

/**
 * Creat by chen on 2018/11/13
 * Describe:
 *
 * @author chenbo
 */
public class NavigationPresenter extends BasePrestener<ImpNavigationFragment> {

    public void getNavigationData(Context context, StateView stateView) {
        ApiRetrofit.setObservableSubscribe(apiUtil.getNavigationData(), new ApiSubscriberObserver<List<NavigationModel>>(context, stateView) {
            @Override
            public void onSuccess(List<NavigationModel> navigationModels) {
                getView().onLoadNavigationSuccess(navigationModels);
            }
        });
    }
}

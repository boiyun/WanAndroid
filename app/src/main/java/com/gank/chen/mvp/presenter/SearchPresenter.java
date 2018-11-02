package com.gank.chen.mvp.presenter;

import android.content.Context;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.http.ApiRetrofit;
import com.gank.chen.http.subscriber.ApiSubscriberObserver;
import com.gank.chen.mvp.model.CommonWebsiteModel;
import com.gank.chen.mvp.view.ImpSearchActivity;
import com.gank.chen.widget.StateView;

import java.util.List;

/**
 * Creat by chen on 2018/11/2
 * Describe:
 *
 * @author chenbo
 */
public class SearchPresenter extends BasePrestener<ImpSearchActivity> {
    /**
     * 获取热搜词
     *
     * @param context   context
     * @param stateView stateView
     */
    public void getHotSearchData(Context context, StateView stateView) {
        ApiRetrofit.setObservableSubscribe(apiUtil.getHotSearchData()
                , new ApiSubscriberObserver<List<CommonWebsiteModel>>(context, stateView) {
                    @Override
                    public void onSuccess(List<CommonWebsiteModel> searchModel) {
                        getView().onLoadSuccess(searchModel);
                    }
                });
    }
}

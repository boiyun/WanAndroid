package com.gank.chen.mvp.presenter;

import android.content.Context;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.http.ApiRetrofit;
import com.gank.chen.http.subscriber.ApiSubscriberObserver;
import com.gank.chen.http.subscriber.LodeMoreObserverSubscriber;
import com.gank.chen.mvp.model.ArticleModel;
import com.gank.chen.mvp.view.PullDownLoadMoreViewImp;
import com.gank.chen.widget.StateView;

/**
 * @author chen
 * @date 2017/12/20
 */

public class CarsListPresenter extends BasePrestener<PullDownLoadMoreViewImp<ArticleModel>> {
    public void getDetailData(Context context, StateView stateView, int page) {
        ApiRetrofit.setObservableSubscribe(apiUtil.getCollectList(page),
                new ApiSubscriberObserver<ArticleModel>(context, stateView) {
                    @Override
                    public void onSuccess(ArticleModel carsListModel) {
                        if (carsListModel.getDatas().size() == 0) {
                            stateView.showEmpty();
                            return;
                        }
                        getView().onLoadSucess(carsListModel);
                    }
                });
    }

    public void getMoreData(Context context, int page) {
        ApiRetrofit.setObservableSubscribe(apiUtil.getCollectList(page),
                new LodeMoreObserverSubscriber<ArticleModel>(context) {
                    @Override
                    public void onSuccess(ArticleModel articleModel) {
                        getView().onLoadMoreSuccess(articleModel);
                    }
                });
    }

}

package com.gank.chen.mvp.presenter;

import android.content.Context;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.http.ApiRetrofit;
import com.gank.chen.http.subscriber.ApiSubscriberObserver;
import com.gank.chen.http.subscriber.LodeMoreObserverSubscriber;
import com.gank.chen.http.subscriber.SubscriberObserverProgress;
import com.gank.chen.mvp.model.ArticleModel;
import com.gank.chen.mvp.view.ImpChaptersListFragment;
import com.gank.chen.mvp.view.ImpProjectsListFragment;
import com.gank.chen.widget.StateView;

/**
 * Created by chen on 2017/12/17
 * 项目列表数据
 *
 * @author chenbo
 */

public class ProjectsListPresenter extends BasePrestener<ImpProjectsListFragment> {

    public void getProjectsList(Context context, StateView stateView, int cid, int page) {
        ApiRetrofit.setObservableSubscribe(apiUtil.toGetProjectList(page, cid), new ApiSubscriberObserver<ArticleModel>(context, stateView) {
            @Override
            public void onSuccess(ArticleModel gankModel) {
                if (gankModel.getDatas() != null && gankModel.getDatas().size() != 0) {
                    getView().onLoadSucess(gankModel);
                } else {
                    stateView.showEmpty();
                }

            }

        });
    }

    public void getMoreProjectsList(Context context, int cid, int page) {
        ApiRetrofit.setObservableSubscribe(apiUtil.toGetProjectList(page, cid), new LodeMoreObserverSubscriber<ArticleModel>(context) {
            @Override
            public void onSuccess(ArticleModel gankModel) {
                getView().onLoadMoreSuccess(gankModel);
            }
        });
    }

}

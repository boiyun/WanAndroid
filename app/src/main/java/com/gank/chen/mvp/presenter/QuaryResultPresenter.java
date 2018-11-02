package com.gank.chen.mvp.presenter;

import android.content.Context;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.http.ApiRetrofit;
import com.gank.chen.http.subscriber.ApiSubscriberObserver;
import com.gank.chen.http.subscriber.LodeMoreObserverSubscriber;
import com.gank.chen.http.subscriber.SubscriberObserverProgress;
import com.gank.chen.mvp.model.ArticleModel;
import com.gank.chen.mvp.view.ImpChaptersListFragment;
import com.gank.chen.mvp.view.OnLoadSuccessViewImp;
import com.gank.chen.mvp.view.PullDownLoadMoreViewImp;
import com.gank.chen.widget.StateView;

/**
 * Creat by chen on 2018/11/2
 * Describe:
 *
 * @author chenbo
 */
public class QuaryResultPresenter extends BasePrestener<ImpChaptersListFragment> {

    public void toGetQuaryResultData(Context context, StateView stateView, int page, String key) {
        ApiRetrofit.setObservableSubscribe(apiUtil.toQuary(page, key)
                , new ApiSubscriberObserver<ArticleModel>(context, stateView) {
                    @Override
                    public void onSuccess(ArticleModel articleModel) {
                        getView().onLoadSucess(articleModel);
                    }
                });

    }

    public void toGetMoreQuaryResultData(Context context, int page, String key) {
        ApiRetrofit.setObservableSubscribe(apiUtil.toQuary(page, key)
                , new LodeMoreObserverSubscriber<ArticleModel>(context) {
                    @Override
                    public void onSuccess(ArticleModel articleModel) {
                        getView().onLoadMoreSuccess(articleModel);
                    }
                });

    }
    /**
     * 收藏文章
     * @param context
     * @param id
     * @param position
     */
    public void onCollect(Context context, int id, int position) {
        ApiRetrofit.setObservableBooleanSubscribe(apiUtil.toCollect(id), new SubscriberObserverProgress<Boolean>(context) {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean) {
                    getView().onCollectSucess(position);
                }
            }
        });
    }

    /**
     * 取消收藏文章
     * @param context
     * @param id
     * @param position
     */
    public void onUnCollect(Context context, int id, int position) {
        ApiRetrofit.setObservableBooleanSubscribe(apiUtil.toUnCollect(id), new SubscriberObserverProgress<Boolean>(context) {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean) {
                    getView().onUnCollectSucess(position);
                }
            }
        });
    }
}

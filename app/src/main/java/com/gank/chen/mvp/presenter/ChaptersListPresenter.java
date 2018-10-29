package com.gank.chen.mvp.presenter;

import android.content.Context;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.http.ApiRetrofit;
import com.gank.chen.http.subscriber.ApiSubscriberFlowable;
import com.gank.chen.http.subscriber.ApiSubscriberObserver;
import com.gank.chen.http.subscriber.LodeMoreFlowableSubscriber;
import com.gank.chen.http.subscriber.LodeMoreObserverSubscriber;
import com.gank.chen.http.subscriber.SubscriberObserverProgress;
import com.gank.chen.mvp.model.ArticleModel;
import com.gank.chen.mvp.model.VideoBean;
import com.gank.chen.mvp.view.ImpChaptersListFragment;
import com.gank.chen.mvp.view.PullDownLoadMoreViewImp;
import com.gank.chen.widget.StateView;

import java.util.List;

/**
 * Created by chen on 2017/12/17
 *
 * @author chenbo
 */

public class ChaptersListPresenter extends BasePrestener<ImpChaptersListFragment> {

    public void getHomeAndroid(Context context, StateView stateView, int id, int page) {
        ApiRetrofit.setObservableSubscribe(apiUtil.toGetChaptersList(id, page), new ApiSubscriberObserver<ArticleModel>(context, stateView) {
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

    public void getMoreAndroid(Context context, int id, int page) {
        ApiRetrofit.setObservableSubscribe(apiUtil.toGetChaptersList(id, page), new LodeMoreObserverSubscriber<ArticleModel>(context) {
            @Override
            public void onSuccess(ArticleModel gankModel) {
                getView().onLoadMoreSuccess(gankModel);
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

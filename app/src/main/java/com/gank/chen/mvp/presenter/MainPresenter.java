package com.gank.chen.mvp.presenter;

import android.content.Context;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.http.ApiRetrofit;
import com.gank.chen.http.ApiScheduler;
import com.gank.chen.http.exception.FuncObservableException;
import com.gank.chen.http.exception.HandleFuc;
import com.gank.chen.http.subscriber.ApiSubscriberObserver;
import com.gank.chen.http.subscriber.LodeMoreObserverSubscriber;
import com.gank.chen.http.subscriber.SubscriberObserverProgress;
import com.gank.chen.mvp.model.ArticleListModel;
import com.gank.chen.mvp.model.ArticleModel;
import com.gank.chen.mvp.model.BannerModel;
import com.gank.chen.mvp.model.BaseModel;
import com.gank.chen.mvp.model.CarsListModel;
import com.gank.chen.mvp.model.MainHomeModel;
import com.gank.chen.mvp.model.MainModel;
import com.gank.chen.mvp.model.MeiZi;
import com.gank.chen.mvp.view.ImpMainHomeFragment;
import com.gank.chen.mvp.view.OnLoadSuccessViewImp;
import com.gank.chen.mvp.view.PullDownLoadMoreViewImp;
import com.gank.chen.widget.StateView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * @author chenbo
 */
public class MainPresenter extends BasePrestener<ImpMainHomeFragment> {
    /**
     * 获取banner和首页推荐数据
     * @param context
     * @param stateView
     * @param page
     */
    public void getMainDatas(Context context, StateView stateView, int page) {
        apiUtil.getBanner().compose(ApiScheduler.getObservableScheduler())
                .onErrorResumeNext(new FuncObservableException<>())
                .map(new HandleFuc<>())
                .zipWith(apiUtil.getArticleList(page)
                                .compose(ApiScheduler.getObservableScheduler())
                                .onErrorResumeNext(new FuncObservableException<>())
                                .map(new HandleFuc<>())
                        , new BiFunction<ArrayList<BannerModel>, ArticleModel, MainHomeModel>() {
                            @Override
                            public MainHomeModel apply(
                                    ArrayList<BannerModel> bannerModels
                                    , ArticleModel articleModelBaseModel) throws Exception {

                                return new MainHomeModel(bannerModels, articleModelBaseModel);
                            }
                        })
                .subscribe(new ApiSubscriberObserver<MainHomeModel>(context, stateView) {
                    @Override
                    public void onSuccess(MainHomeModel mainHomeModelList) {
                        getView().onLoadSucess(mainHomeModelList);
                    }
                });


    }

    /**
     * 获取更多推荐数据
     * @param context
     * @param page
     */
    public void getMoreMainDatas(Context context, int page) {
        apiUtil.getArticleList(page)
                .compose(ApiScheduler.getObservableScheduler())
                .onErrorResumeNext(new FuncObservableException<>())
                .map(new HandleFuc<>())
                .subscribe(new LodeMoreObserverSubscriber<ArticleModel>(context) {
                    @Override
                    public void onSuccess(ArticleModel articleModel) {
                        getView().onLoadMoreSuccess(articleModel);
                    }
                });

    }

    /**
     * 首页收藏文章
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

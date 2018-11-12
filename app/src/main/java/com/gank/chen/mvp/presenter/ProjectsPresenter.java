package com.gank.chen.mvp.presenter;

import android.content.Context;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.http.ApiRetrofit;
import com.gank.chen.http.subscriber.ApiSubscriberObserver;
import com.gank.chen.mvp.model.ChaptersListModel;
import com.gank.chen.mvp.view.ImpProjectsFragment;
import com.gank.chen.widget.StateView;

import java.util.List;

/**
 * Created by chen on 2017/12/17
 *
 * @author chenbo
 */

public class ProjectsPresenter extends BasePrestener<ImpProjectsFragment> {

    public void getProjects(Context context, StateView stateView) {
        ApiRetrofit.setObservableSubscribe(apiUtil.toGetProject(), new ApiSubscriberObserver<List<ChaptersListModel>>(context, stateView) {
            @Override
            public void onSuccess(List<ChaptersListModel> chaptersListModels) {
                getView().onLoadChapterSucess(chaptersListModels);
            }
        });
    }
}

package com.gank.chen.ui.fragment.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gank.chen.R;
import com.gank.chen.adapter.MeiZiPicAdapter;
import com.gank.chen.base.BaseFragment;
import com.gank.chen.base.CreatePresenter;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.MeiZi;
import com.gank.chen.mvp.presenter.MeiZiListPresenter;
import com.gank.chen.mvp.view.PullDownLoadMoreViewImp;
import com.gank.chen.util.RouterUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * @author chenbo
 */
@CreatePresenter(MeiZiListPresenter.class)
@Route(path = RouterUrlManager.MEIZI_FRAGMENT)
public class MeiZiFragment extends BaseFragment<PullDownLoadMoreViewImp<List<MeiZi>>, MeiZiListPresenter> implements PullDownLoadMoreViewImp {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private MeiZiPicAdapter adapter;
    private List<MeiZi> gankModel;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initView() {
        initPageNum(1);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(staggeredGridLayoutManager);

        adapter = new MeiZiPicAdapter(gankModel);
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MeiZi bean = (MeiZi) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putString("picurl", bean.url);
                RouterUtil.goToActivity(RouterUrlManager.MEIZI_DETAIL, bundle);
            }
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(getActivity()).resumeRequests();
                } else {
                    Glide.with(getActivity()).pauseRequests();
                }
            }


        });
    }

    @Override
    public void initData() {
        getPresenter().getGankData(getActivity(), stateView, page);
    }

    @Override
    protected void onLoadMoreData(RefreshLayout refreshLayout) {
        super.onLoadMoreData(refreshLayout);
        getPresenter().getMoreGankData(getActivity(), page);
    }

    @Override
    public void onLoadSucess(Object obj) {
        gankModel = (List<MeiZi>) obj;
        super.onLoadSucess(gankModel);
        adapter.setNewData(gankModel);
    }

    @Override
    public void onLoadMoreSuccess(Object obj) {
        List<MeiZi> gankModel = (List<MeiZi>) obj;
        super.onLoadMoreSuccess(gankModel);
        adapter.addData(gankModel);
    }
}

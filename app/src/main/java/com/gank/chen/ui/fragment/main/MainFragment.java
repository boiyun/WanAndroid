package com.gank.chen.ui.fragment.main;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gank.chen.R;
import com.gank.chen.adapter.MainArticleAdapter;
import com.gank.chen.base.BaseFragment;
import com.gank.chen.base.CreatePresenter;
import com.gank.chen.common.ConstantMap;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.ArticleListModel;
import com.gank.chen.mvp.model.ArticleModel;
import com.gank.chen.mvp.model.BannerModel;
import com.gank.chen.mvp.model.MainHomeModel;
import com.gank.chen.mvp.presenter.MainPresenter;
import com.gank.chen.mvp.view.ImpMainHomeFragment;
import com.gank.chen.mvp.view.PullDownLoadMoreViewImp;
import com.gank.chen.util.GlideImageLoader;
import com.gank.chen.util.RouterUtil;
import com.gank.chen.util.SharePreferenceUtil;
import com.gank.chen.util.SnackbarUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页Fragment
 *
 * @author chenbo
 */
@CreatePresenter(MainPresenter.class)
@Route(path = RouterUrlManager.MAIN_FRAGMENT)
public class MainFragment extends BaseFragment<ImpMainHomeFragment, MainPresenter> implements ImpMainHomeFragment {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    private Banner bannerMain;
    private MainArticleAdapter adapter;
    private List<ArticleListModel> articleListModels;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initData() {
        getPresenter().getMainDatas(getActivity(), stateView, page);
    }

    @Override
    public void initView() {
        rvMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MainArticleAdapter(articleListModels);
        rvMain.setAdapter(adapter);
        View inflate = View.inflate(getActivity(), R.layout.item_main_banner, null);
        bannerMain = inflate.findViewById(R.id.banner_main);
        adapter.addHeaderView(inflate);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("weburl", ((ArticleListModel) (adapter.getData().get(position))).getLink());
                bundle.putString("title", ((ArticleListModel) (adapter.getData().get(position))).getTitle());
                RouterUtil.goToActivity(RouterUrlManager.DETAIL, bundle);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (RouterUtil.checkLoginState(rvMain)) {
                    ArticleListModel data = (ArticleListModel) adapter.getData().get(position);
                    if (data.isCollect()) {
                        getPresenter().onUnCollect(getActivity(), data.getId(), position);
                    } else {
                        getPresenter().onCollect(getActivity(), data.getId(), position);
                    }
                }
            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("MainFragment", "-------> setUserVisibleHint=" + isVisibleToUser);
        if (bannerMain != null) {
            if (isVisibleToUser) {
                bannerMain.startAutoPlay();
            } else {
                bannerMain.stopAutoPlay();
            }
        }
    }

    @Override
    public void onCollectSucess(int position) {
        adapter.getData().get(position).setCollect(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUnCollectSucess(int position) {
        adapter.getData().get(position).setCollect(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadSucess(MainHomeModel mainHomeModels) {
        articleListModels = mainHomeModels.getArticleListModels().getDatas();
        super.onLoadSucess(articleListModels);
        adapter.setNewData(articleListModels);

        if (mainHomeModels.getBannerModels() != null) {
            initBanner(mainHomeModels.getBannerModels());
        }
    }

    @Override
    protected void onLoadMoreData(RefreshLayout refreshLayout) {
        getPresenter().getMoreMainDatas(getActivity(), page);
    }

    @Override
    public void onLoadMoreSuccess(ArticleModel mainHomeModels) {
        List<ArticleListModel> moredatas = mainHomeModels.getDatas();
        super.onLoadMoreSuccess(moredatas);
        adapter.addData(moredatas);
    }


    /**
     * 初始化banner
     *
     * @param bannerModels
     */
    private void initBanner(ArrayList<BannerModel> bannerModels) {
        //设置图片加载器
        bannerMain.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<String> list = new ArrayList<>();
        list.clear();
        for (BannerModel banner1 : bannerModels) {
            list.add(banner1.getImagePath());
        }

        bannerMain.setImages(list);
        //        //设置轮播时间
        bannerMain.setDelayTime(5000);

        //设置banner动画效果
        bannerMain.setBannerAnimation(Transformer.Default);
        //banner设置方法全部调用完毕时最后调用
        bannerMain.start();
        bannerMain.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("weburl", bannerModels.get(position).getUrl());
                bundle.putString("title", bannerModels.get(position).getTitle());
                RouterUtil.goToActivity(RouterUrlManager.DETAIL, bundle);
            }
        });
    }

}

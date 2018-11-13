package com.gank.chen.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.gank.chen.mvp.presenter.ChaptersListPresenter;
import com.gank.chen.mvp.view.ImpChaptersListFragment;
import com.gank.chen.util.RouterUtil;
import com.gank.chen.util.SharePreferenceUtil;
import com.gank.chen.util.SnackbarUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * 公众号列表页面
 *
 * @author chenbo
 */
@CreatePresenter(ChaptersListPresenter.class)
@Route(path = RouterUrlManager.CHAPTERSLIST_FRAGMENT)
public class ChaptersListFragment extends BaseFragment<ImpChaptersListFragment, ChaptersListPresenter>
        implements ImpChaptersListFragment {

    @BindView(R.id.recyclerview_home_pic)
    RecyclerView recyclerviewHomePic;

    private MainArticleAdapter adapter;
    private List<ArticleListModel> videoBeanList;
    private int id;


    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    public void initData() {
        assert getArguments() != null;
        String type = "id";
        id = getArguments().getInt(type);
        getPresenter().getHomeAndroid(getActivity(), stateView, id, page);
    }

    @Override
    public void initView() {
        initPageNum(1);
        recyclerviewHomePic.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MainArticleAdapter(videoBeanList);
        recyclerviewHomePic.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleListModel bean = (ArticleListModel) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putString("weburl", bean.getLink());
                bundle.putString("title", bean.getTitle());
                RouterUtil.goToActivity(RouterUrlManager.DETAIL, bundle);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String userPhone = SharePreferenceUtil.getString(ConstantMap.USER_PHONE, "");
                if (!TextUtils.isEmpty(userPhone)) {
                    ArticleListModel data = (ArticleListModel) adapter.getData().get(position);

                    if (data.isCollect()) {
                        getPresenter().onUnCollect(getActivity(), data.getId(), position);
                    } else {
                        getPresenter().onCollect(getActivity(), data.getId(), position);
                    }
                } else {
                    SnackbarUtils.with(recyclerviewHomePic).setMessage("请先登录哦~")
                            .setAction("去登录", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    RouterUtil.goToActivity(RouterUrlManager.LOGIN);
                                }
                            })
                            .showWarning();
                }

            }
        });

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
    public void onLoadSucess(ArticleModel articleModel) {
        super.onLoadSucess(articleModel.getDatas());
        videoBeanList = articleModel.getDatas();
        adapter.setNewData(videoBeanList);
    }

    @Override
    protected void onLoadMoreData(RefreshLayout refreshLayout) {
        super.onLoadMoreData(refreshLayout);
        getPresenter().getMoreAndroid(getActivity(), id, page);
    }

    @Override
    public void onLoadMoreSuccess(ArticleModel articleModel) {
        super.onLoadMoreSuccess(articleModel.getDatas());
        adapter.addData(articleModel.getDatas());
    }
}

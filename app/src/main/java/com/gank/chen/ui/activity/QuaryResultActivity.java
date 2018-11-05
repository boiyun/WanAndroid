package com.gank.chen.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gank.chen.R;
import com.gank.chen.adapter.MainArticleAdapter;
import com.gank.chen.base.BaseActivity;
import com.gank.chen.base.CreatePresenter;
import com.gank.chen.common.ConstantMap;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.ArticleListModel;
import com.gank.chen.mvp.model.ArticleModel;
import com.gank.chen.mvp.presenter.QuaryResultPresenter;
import com.gank.chen.mvp.view.ImpChaptersListFragment;
import com.gank.chen.util.RouterUtil;
import com.gank.chen.util.SharePreferenceUtil;
import com.gank.chen.util.SnackbarUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 搜索结果页面
 *
 * @author chenbo
 */
@CreatePresenter(QuaryResultPresenter.class)
@Route(path = RouterUrlManager.QUARY_RESULT)
public class QuaryResultActivity extends BaseActivity<ImpChaptersListFragment, QuaryResultPresenter>
        implements ImpChaptersListFragment {

    @BindView(R.id.rv_quary_result)
    RecyclerView rvQuaryResult;
    private List<ArticleListModel> articleListModels = new ArrayList<>();
    private MainArticleAdapter adapter;
    private String key;

    @Override
    public void initData(Bundle savedInstanceState) {
        getPresenter().toGetQuaryResultData(activity, stateView, page, key);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_quary_result;
    }

    @Override
    public void initView() {
        key = getIntent().getStringExtra("key");
        initToolBar(key);
        rvQuaryResult.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new MainArticleAdapter(articleListModels);
        rvQuaryResult.setAdapter(adapter);
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
                        getPresenter().onUnCollect(activity, data.getId(), position);
                    } else {
                        getPresenter().onCollect(activity, data.getId(), position);
                    }
                } else {
                    SnackbarUtils.with(rvQuaryResult).setMessage("请先登录哦~")
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
    protected void onLoadMoreData(RefreshLayout refreshLayout) {
        super.onLoadMoreData(refreshLayout);
        getPresenter().toGetMoreQuaryResultData(activity, page, key);
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
        articleListModels = articleModel.getDatas();
        adapter.setNewData(articleListModels);
    }

    @Override
    public void onLoadMoreSuccess(ArticleModel articleModel) {
        super.onLoadMoreSuccess(articleModel.getDatas());
        adapter.addData(articleModel.getDatas());
    }
}

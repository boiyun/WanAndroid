package com.gank.chen.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gank.chen.R;
import com.gank.chen.adapter.CommonWebsiteAdapter;
import com.gank.chen.base.BaseActivity;
import com.gank.chen.base.CreatePresenter;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.ArticleModel;
import com.gank.chen.mvp.model.CommonWebsiteModel;
import com.gank.chen.mvp.presenter.CommonWebsitesPresenter;
import com.gank.chen.mvp.view.OnLoadSuccessViewImp;
import com.gank.chen.util.RouterUtil;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Creat by chen on 2018/10/31
 * Describe:
 *
 * @author chenbo
 */
@CreatePresenter(CommonWebsitesPresenter.class)
@Route(path = RouterUrlManager.COMMON_WEBSITES)
public class CommonWebsitesActivity extends BaseActivity<OnLoadSuccessViewImp<List<CommonWebsiteModel>>, CommonWebsitesPresenter>
        implements OnLoadSuccessViewImp {
    @BindView(R.id.recyclerview_common_web)
    RecyclerView recyclerviewCommonWeb;
    private CommonWebsiteAdapter adapter;
    private List<CommonWebsiteModel> modelList = new ArrayList<>();

    @Override
    public void initData(Bundle savedInstanceState) {
        getPresenter().getCommonWebsite(activity, stateView);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_commonwebsites;
    }

    @Override
    public void initView() {
        initToolBar("常用网站");
        smartRefresh.setEnableRefresh(false);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(activity);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);

        recyclerviewCommonWeb.setLayoutManager(layoutManager);
        adapter = new CommonWebsiteAdapter(modelList);
        recyclerviewCommonWeb.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            CommonWebsiteModel commonWebsiteModel = (CommonWebsiteModel) adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putString("weburl", commonWebsiteModel.getLink());
            bundle.putString("title", commonWebsiteModel.getName());
            RouterUtil.goToActivity(RouterUrlManager.DETAIL, bundle);
        });
    }

    @Override
    public void onLoadSucess(Object obj) {
        List<CommonWebsiteModel> commonWebsiteModel = (List<CommonWebsiteModel>) obj;
        modelList = commonWebsiteModel;
        super.onLoadSucess(commonWebsiteModel);
        adapter.setNewData(commonWebsiteModel);

    }

    @Override
    protected boolean useLoadMore() {
        return false;
    }
}

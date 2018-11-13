package com.gank.chen.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gank.chen.R;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.ArticleListModel;
import com.gank.chen.mvp.model.CommonWebsiteModel;
import com.gank.chen.mvp.model.NavigationModel;
import com.gank.chen.util.CommenUtil;
import com.gank.chen.util.RouterUtil;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

/**
 * @author chenbo
 */
public class NavigationAdapter extends BaseQuickAdapter<NavigationModel, BaseViewHolder> {


    public NavigationAdapter(List<NavigationModel> data) {
        super(R.layout.item_navigation, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NavigationModel item) {
        RecyclerView recyclerView = helper.getView(R.id.rv_item_navigation);
        List<ArticleListModel> articles = item.getArticles();
        helper.setText(R.id.tv_item_navigation, item.getName());

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);

        recyclerView.setLayoutManager(layoutManager);

        NavigationItemAdapter navigationItemAdapter = new NavigationItemAdapter(articles);
        navigationItemAdapter.setNewData(articles);
        navigationItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ArticleListModel> articles = (List<ArticleListModel>) adapter.getData();
                Bundle bundle = new Bundle();
                bundle.putString("weburl", articles.get(position).getLink());
                bundle.putString("title", articles.get(position).getTitle());
                RouterUtil.goToActivity(RouterUrlManager.DETAIL, bundle);
            }
        });
        recyclerView.setAdapter(navigationItemAdapter);
    }

}

package com.gank.chen.adapter;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gank.chen.R;
import com.gank.chen.mvp.model.ArticleListModel;

import java.util.List;

/**
 * @author chenbo
 */
public class ProjectsListAdapter extends BaseQuickAdapter<ArticleListModel, BaseViewHolder> {


    public ProjectsListAdapter(List<ArticleListModel> data) {
        super(R.layout.item_project_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleListModel item) {
        helper.setText(R.id.tv_title, item.getTitle());
    }

}

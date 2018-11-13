package com.gank.chen.adapter;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_des, item.getDesc())
                .setText(R.id.tv_user_name, item.getAuthor())
                .setText(R.id.tv_time, item.getNiceDate())
                .addOnClickListener(R.id.iv_like);

        Glide.with(mContext).load(item.getEnvelopePic()).into((ImageView) helper.getView(R.id.iv_project_pic));

        if (item.isCollect()) {
            helper.setImageResource(R.id.iv_like, R.mipmap.icon_like);
        } else {
            helper.setImageResource(R.id.iv_like, R.mipmap.icon_unlike);
        }
    }

}

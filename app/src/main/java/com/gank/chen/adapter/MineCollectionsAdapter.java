package com.gank.chen.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gank.chen.R;
import com.gank.chen.mvp.model.ArticleListModel;

import java.util.List;

/**
 * @author chenbo
 */
public class MineCollectionsAdapter extends BaseQuickAdapter<ArticleListModel, BaseViewHolder> {


    public MineCollectionsAdapter(List<ArticleListModel> data) {
        super(R.layout.item_home_pic_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleListModel item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_user_name, item.getAuthor())
                .setText(R.id.tv_type, item.getNiceDate())
                .setImageResource(R.id.iv_like, R.mipmap.icon_like)
                .addOnClickListener(R.id.iv_like);

    }

}

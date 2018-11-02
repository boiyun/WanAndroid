package com.gank.chen.adapter;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gank.chen.R;
import com.gank.chen.mvp.model.ArticleListModel;
import com.gank.chen.util.CommenUtil;

import java.util.List;

/**
 * @author chenbo
 */
public class MainArticleAdapter extends BaseQuickAdapter<ArticleListModel, BaseViewHolder> {


    public MainArticleAdapter(List<ArticleListModel> data) {
        super(R.layout.item_home_artical_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleListModel item) {
        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()))
                .setText(R.id.tv_user_name, item.getAuthor())
                .setText(R.id.tv_type, item.getNiceDate())
                .setVisible(R.id.iv_new, item.isFresh())
                .addOnClickListener(R.id.iv_like);
        if (item.isCollect()) {
            helper.setImageResource(R.id.iv_like, R.mipmap.icon_like);
        } else {
            helper.setImageResource(R.id.iv_like, R.mipmap.icon_unlike);
        }
    }

}

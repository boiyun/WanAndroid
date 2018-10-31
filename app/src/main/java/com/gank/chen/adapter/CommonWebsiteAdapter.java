package com.gank.chen.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gank.chen.R;
import com.gank.chen.mvp.model.CommonWebsiteModel;
import com.gank.chen.util.CommenUtil;

import java.util.List;

/**
 * @author chenbo
 */
public class CommonWebsiteAdapter extends BaseQuickAdapter<CommonWebsiteModel, BaseViewHolder> {


    public CommonWebsiteAdapter(List<CommonWebsiteModel> data) {
        super(R.layout.item_common_website, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonWebsiteModel item) {
        helper.setText(R.id.tv_title, item.getName())
                .setBackgroundColor(R.id.tv_title, CommenUtil.randomTagColor());

    }

}

package com.gank.chen.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gank.chen.R;
import com.gank.chen.adapter.viewholder.MeiZiViewHolder;
import com.gank.chen.mvp.model.MeiZi;

import java.util.List;

/**
 * @author chen
 * @date 2017/12/20
 */

public class MeiZiPicAdapter extends BaseQuickAdapter<MeiZi, MeiZiViewHolder> {


    public MeiZiPicAdapter(@Nullable List<MeiZi> data) {
        super(R.layout.item_meizi, data);
    }

    @Override
    protected void convert(MeiZiViewHolder helper, MeiZi item) {
        Glide.with(mContext).load(item.url)
                .asBitmap()
                .into(helper.itemIvMeizi);
    }
}

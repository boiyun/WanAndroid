package com.gank.chen.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.gank.chen.R;

/**
 *
 * @author pc
 * @date 2018/2/26
 */

public class MeiZiViewHolder extends BaseViewHolder {
    public ImageView itemIvMeizi;

    MeiZiViewHolder(View view) {
        super(view);
        itemIvMeizi =  view.findViewById(R.id.item_iv_meizi);
    }
}

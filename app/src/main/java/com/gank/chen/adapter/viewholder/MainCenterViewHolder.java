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

public class MainCenterViewHolder extends BaseViewHolder {
    public ImageView ivMainItemCenterPic;
    public TextView tvMainItemCenterName;

    MainCenterViewHolder(View view) {
        super(view);
        ivMainItemCenterPic = view.findViewById(R.id.iv_main_item_center_pic);
        tvMainItemCenterName =  view.findViewById(R.id.tv_main_item_center_name);
    }
}

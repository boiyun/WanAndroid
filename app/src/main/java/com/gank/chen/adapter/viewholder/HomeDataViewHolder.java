package com.gank.chen.adapter.viewholder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.gank.chen.R;

/**
 *
 * @author ChenBo
 * @date 2018/5/11
 */

public class HomeDataViewHolder extends BaseViewHolder {
    TextView tvUserName;
    TextView tvType;
    TextView tvTitle;
    RelativeLayout rlItemMain;

    public HomeDataViewHolder(View view) {
        super(view);
        rlItemMain = view.findViewById(R.id.rl_item_main);
        tvUserName =  view.findViewById(R.id.tv_user_name);
        tvType =  view.findViewById(R.id.tv_type);
        tvTitle =  view.findViewById(R.id.tv_title);
    }
}

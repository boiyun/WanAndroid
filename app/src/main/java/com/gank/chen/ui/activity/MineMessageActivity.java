package com.gank.chen.ui.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gank.chen.R;
import com.gank.chen.base.BaseNoNetActivity;
import com.gank.chen.common.RouterUrlManager;

/**
 * Creat by chen on 2018/10/29
 * Describe:
 * @author chenbo
 */
@Route(path = RouterUrlManager.MINE_MESSAGE)
public class MineMessageActivity extends BaseNoNetActivity {
    @Override
    public int getViewLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        initToolBar("我的消息", false);
    }
}

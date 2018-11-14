package com.gank.chen.ui.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gank.chen.R;
import com.gank.chen.base.BaseNoNetActivity;
import com.gank.chen.common.RouterUrlManager;

/**
 * @author chenbo
 */
@Route(path = RouterUrlManager.TODO_ACTIVITY)
public class TodoActivity extends BaseNoNetActivity {

    @Override
    public int getViewLayoutId() {
        return R.layout.activity_todo;

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        initToolBar("ToDo", false);
    }

}

package com.gank.chen.ui.activity;


import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gank.chen.R;
import com.gank.chen.base.BaseNoNetActivity;
import com.gank.chen.common.RouterUrlManager;

import butterknife.BindView;

/**
 * @author chenbo
 */
@Route(path = RouterUrlManager.ABOUT)
public class AboutUsActivity extends BaseNoNetActivity {

    @BindView(R.id.my_webview)
    WebView myX5WebView;

    @Override
    public int getViewLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        initToolBar("关于", false);
        String url = "file:///android_asset/about.html";
        myX5WebView.loadUrl(url);
//        smartRefresh.setRefreshHeader(new TaurusHeader(this));
//        smartRefresh.setPrimaryColorsId(R.color.colorPrimary, R.color.colorPrimaryDark);
//        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.finishRefresh(1000);
//            }
//        });
//        smartRefresh.setEnableLoadMore(false);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_share, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
}

package com.gank.chen.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gank.chen.R;
import com.gank.chen.base.BaseNoNetActivity;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.util.CommenUtil;
import com.gank.chen.util.LogUtil;
import com.gank.chen.util.SnackbarUtils;
import com.gank.chen.widget.MyX5WebView;

import butterknife.BindView;

/**
 * 详情页面
 *
 * @author chenbo
 */
@Route(path = RouterUrlManager.DETAIL)
public class DetailActivity extends BaseNoNetActivity {
    @BindView(R.id.webview_detail)
    MyX5WebView webviewDetail;

    private String weburl;
    private String title;

    @Override
    public int getViewLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView() {
        weburl = getIntent().getStringExtra("weburl");
        title = getIntent().getStringExtra("title");
        initToolBar(title, false);
        webviewDetail.setShowProgress(true);
        LogUtil.e("url", weburl);
    }

    @Override
    public void initData() {
        webviewDetail.loadUrl(weburl);
//        webviewDetail.loadUrl("http://m.youku.com/video/id_XODEzMjU1MTI4.html");
//        webviewDetail.loadUrl("https://www.bilibili.com/video/av34675831/?spm_id_from=333.334.chief_recommend.16");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reload:
                webviewDetail.reload();
                break;
            case R.id.action_copyaddress:
                CommenUtil.copyText(this, weburl);
                SnackbarUtils.with(webviewDetail).setMessage("地址已复制到剪切板").show();
                break;
            case R.id.action_openformout:
                Uri uri = Uri.parse(weburl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.action_share:
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, title + '\n' + weburl);
                startActivity(Intent.createChooser(textIntent, "分享到"));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

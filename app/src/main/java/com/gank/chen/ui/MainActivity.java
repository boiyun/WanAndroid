package com.gank.chen.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.gank.chen.R;
import com.gank.chen.adapter.MainPageAdapter;
import com.gank.chen.base.BaseNoNetActivity;
import com.gank.chen.base.CreatePresenter;
import com.gank.chen.common.Cockroach;
import com.gank.chen.common.ConstantMap;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.Event;
import com.gank.chen.mvp.presenter.MainActivityPresenter;
import com.gank.chen.mvp.view.ImpMainActivity;
import com.gank.chen.ui.fragment.main.MainFragment;
import com.gank.chen.ui.fragment.main.MeiZiFragment;
import com.gank.chen.util.CommenUtil;
import com.gank.chen.util.GlideCircleTransform;
import com.gank.chen.util.RouterUtil;
import com.gank.chen.util.SharePreferenceUtil;
import com.gank.chen.util.SnackbarUtils;
import com.gank.chen.util.ToastUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.observers.DefaultObserver;

/**
 * @author chenbo
 */
@Route(path = RouterUrlManager.MAIN)
@CreatePresenter(MainActivityPresenter.class)
public class MainActivity extends BaseNoNetActivity<ImpMainActivity, MainActivityPresenter> implements NavigationView.OnNavigationItemSelectedListener
        , View.OnClickListener
        , ImpMainActivity {


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.bottomnavigation)
    BottomNavigationView bottomnavigation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigationview)
    NavigationView navigationview;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    private long exitTime = 0;
    private ImageView ivUserheader;
    private TextView tvUsername;
    private static final int REQUEST_CODE_CHOOSE = 1000;
    List<Uri> mSelected;
    private MaterialDialog dialog;


    @Override
    public int getViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        String userPhone = SharePreferenceUtil.getString(ConstantMap.USER_PHONE, "");
        String user = SharePreferenceUtil.getString(ConstantMap.USER_HEADER_ICON, "");
        if (!TextUtils.isEmpty(user)) {
            Glide.with(activity).load(Uri.parse(user)).transform(new GlideCircleTransform(activity)).into(ivUserheader);
        } else {
            ivUserheader.setImageResource(R.mipmap.default_headimg_male);
        }
        if (!TextUtils.isEmpty(userPhone)) {
            tvUsername.setText(userPhone);
            tvUsername.setClickable(false);
        } else {
            tvUsername.setText("立即登录");
            tvUsername.setClickable(true);
        }
    }

    @Override
    public void initView() {
        toolbar.setTitle(getResources().getString(R.string.tab_home));
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerlayout, toolbar, 0, 0);

        actionBarDrawerToggle.syncState();
        drawerlayout.addDrawerListener(actionBarDrawerToggle);

        navigationview.setNavigationItemSelectedListener(this);
        navigationview.setItemIconTintList(null);

        View headerView = navigationview.getHeaderView(0);
        ivUserheader = headerView.findViewById(R.id.iv_userheader);
        tvUsername = headerView.findViewById(R.id.tv_username);
        tvUsername.setOnClickListener(this);
        ivUserheader.setOnClickListener(this);
        bottomnavigation.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.tab_one:
                    clickTab(0);
                    //返回true,否则tab按钮不变色,未被选中
                    return true;
                case R.id.tab_two:
                    clickTab(1);
                    return true;
                case R.id.tab_three:
                    clickTab(2);
                    return true;
                default:
                    break;
            }
            return false;
        });

        MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager(), initFragment());
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changePage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private List initFragment() {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(RouterUtil.getFragment(RouterUrlManager.MAIN_FRAGMENT));
        mFragments.add(RouterUtil.getFragment(RouterUrlManager.CHAPTERS_FRAGMENT));
        mFragments.add(RouterUtil.getFragment(RouterUrlManager.MEIZI_FRAGMENT));
        return mFragments;
    }

    private void clickTab(int item) {
        //为防止隔页切换时,滑过中间页面的问题,去除页面切换缓慢滑动的动画效果
        viewpager.setCurrentItem(item, false);
        supportInvalidateOptionsMenu();
    }

    private void changePage(int position) {
        switch (position) {
            case 0:
                bottomnavigation.setSelectedItemId(R.id.tab_one);
                getSupportActionBar().setTitle(getResources().getString(R.string.tab_home));
                supportInvalidateOptionsMenu();
                break;
            case 1:
                bottomnavigation.setSelectedItemId(R.id.tab_two);
                getSupportActionBar().setTitle(getResources().getString(R.string.tab_chapter));
                supportInvalidateOptionsMenu();
                break;
            case 2:
                bottomnavigation.setSelectedItemId(R.id.tab_three);
                getSupportActionBar().setTitle(getResources().getString(R.string.tab_meizi));
                supportInvalidateOptionsMenu();
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            int anInt = 2000;
            if ((System.currentTimeMillis() - exitTime) > anInt) {
                SnackbarUtils.with(bottomnavigation).setMessage("再按一次退出程序").show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cockroach.uninstall();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mine_favor:
                RouterUtil.goToActivity(RouterUrlManager.MINE_COLLECTION);
                break;
            case R.id.mine_message:
                RouterUtil.goToActivity(RouterUrlManager.COMMON_WEBSITES);
                break;
            case R.id.mine_setting:
                RouterUtil.goToActivity(RouterUrlManager.SETTING);
                break;
            case R.id.mine_share:
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, "WanAndroid" + '\n' + "https://fir.im/e39b");
                startActivity(Intent.createChooser(textIntent, "分享到"));
                drawerlayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.mine_about:
                RouterUtil.goToActivity(RouterUrlManager.ABOUT);
                break;
            case R.id.mine_exit:
                String userPhone = SharePreferenceUtil.getString(ConstantMap.USER_PHONE, "");
                if (!TextUtils.isEmpty(userPhone)) {
                    dialog = new MaterialDialog.Builder(this)
                            .title("提示")
                            .content("确定要退出账号吗？")
                            .positiveText("确定")
                            .negativeText("取消")
                            .onPositive((dialog, which) -> getPresenter().toLogout(activity))
                            .onNegative((dialog, which) -> dialog.dismiss()).show();
                } else {
                    SnackbarUtils.with(bottomnavigation).setMessage("请先登录哦~")
                            .setAction("去登录", v -> RouterUtil.goToActivity(RouterUrlManager.LOGIN))
                            .showWarning();
                }
                drawerlayout.closeDrawer(GravityCompat.START);
                break;
            default:
                drawerlayout.closeDrawer(GravityCompat.START);
                break;
        }

        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_username:
                RouterUtil.goToActivity(RouterUrlManager.LOGIN);
                break;
            case R.id.iv_userheader:
                checkPermission(new DefaultObserver<Boolean>() {
                                    @Override
                                    public void onNext(Boolean aBoolean) {
                                        if (aBoolean) {
                                            Matisse.from(activity)
                                                    .choose(MimeType.allOf())
                                                    .countable(true)
                                                    // 图片选择的最多数量
                                                    .maxSelectable(1)
                                                    .capture(true)
                                                    .captureStrategy(new CaptureStrategy(true, "com.gank.chen.fileprovider"))
                                                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                                    // 缩略图的比例
                                                    .thumbnailScale(0.85f)
                                                    .imageEngine(new GlideEngine())
                                                    .forResult(REQUEST_CODE_CHOOSE);
                                        } else {
                                            ToastUtils.showToast(activity, "需要您允许申请的权限哦");
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                }, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == -1) {
            mSelected = Matisse.obtainResult(data);
            Uri uri = mSelected.get(0);
            Glide.with(this).load(uri).transform(new GlideCircleTransform(this)).into(ivUserheader);
            SharePreferenceUtil.setString(ConstantMap.USER_HEADER_ICON, uri.toString());
        }
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void onEvent(Event event) {
        super.onEvent(event);
        if (event.getCode() == Event.EVENT_LOGIN_SUCCESS) {
            String data = (String) event.getData();
            tvUsername.setText(data);
            tvUsername.setClickable(false);
        }
    }

    @Override
    public void onLogoutSucess(Boolean boo) {
        if (boo) {
            SharePreferenceUtil.clear();
            initData();
            if (dialog != null) {
                dialog.dismiss();
            }
            drawerlayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hot, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mine_hot:
                RouterUtil.goToActivity(RouterUrlManager.COMMON_WEBSITES);
                break;
            case R.id.mine_search:
                RouterUtil.goToActivity(RouterUrlManager.SEARCH_ACTIVITY);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int currentItem = viewpager.getCurrentItem();
        menu.findItem(R.id.mine_hot).setVisible(currentItem == 0);
        menu.findItem(R.id.mine_search).setVisible(currentItem !=2);
        return super.onPrepareOptionsMenu(menu);
    }

}

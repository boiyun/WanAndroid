package com.gank.chen.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gank.chen.R;
import com.gank.chen.base.factory.IPresenterFactory;
import com.gank.chen.base.factory.PresenterFactory;
import com.gank.chen.base.proxy.IPresenterProxy;
import com.gank.chen.base.proxy.PresenterProxy;
import com.gank.chen.mvp.model.Event;
import com.gank.chen.util.RxBus;
import com.gank.swipebacklibrary.swipebacklayout.BGASwipeBackHelper;
import com.jaeger.library.StatusBarUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by chen on 2017/12/17
 *
 * @author chenbo
 */

public abstract class BaseNoNetActivity<V extends BaseView, P extends BasePrestener<V>> extends AppCompatActivity
        implements IPresenterProxy<V, P>, BGASwipeBackHelper.Delegate {
    protected Activity activity;
    private PresenterProxy<V, P> mProxy = new PresenterProxy<>(PresenterFactory.<V, P>createFactory(getClass()));
    private Unbinder bind;
    private RxPermissions rxPermissions;
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    private CompositeDisposable mDisposables;
    protected BGASwipeBackHelper mSwipeBackHelper;

    public interface OnRightClickListener {
        /**
         * 右侧点击
         */
        void rightClick();
    }

    @Override
    public void setPresenterFactory(IPresenterFactory<V, P> presenterfactory) {
        mProxy.setPresenterFactory(presenterfactory);
    }

    @Override
    public IPresenterFactory<V, P> getPresenterFactory() {
        return mProxy.getPresenterFactory();
    }

    @Override
    public P getPresenter() {
        return mProxy.getPresenter();
    }

    /**
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(getViewLayoutId());
        initSwipeBackFinish();
        setStatusBar();
        super.onCreate(savedInstanceState);
        activity = this;
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(activity);
        }
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
        try {
            mProxy.onCreate((V) this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initBind();
        initView();
        initData();
        if (useEventBus()) {
            //注册eventbus
            Disposable disposable = RxBus.getDefault()
                    .register(Event.class, new Consumer<Event>() {
                        @Override
                        public void accept(Event event) {
                            int eventCode = event.getCode();
                            switch (eventCode) {
                                case Event.EVENT_CLOSE_ALL_ACTIVITY:

                                    break;
                                default:
                                    onEvent(event);
                                    break;
                            }
                        }
                    });
            addDispose(disposable);
        }
    }

    /**
     * RxJava 添加订阅
     */
    protected void addDispose(Disposable disposable) {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        //将所有disposable放入,集中处理
        mDisposables.add(disposable);
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }


    public void checkPermission(Observer<Boolean> observer, String... permissions) {
        rxPermissions.request(permissions)
                .subscribe(observer);
    }

    public void requstPermission(Activity context, Observer<Boolean> observer, String... permissions) {
        rxPermissions.shouldShowRequestPermissionRationale(context, permissions)
                .subscribe(observer);
    }

    /**
     * 统一初始化titlebar
     */
    protected Toolbar initToolBar(String title, boolean rightImgIsShow) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> back());
        return toolbar;
    }

    /**
     * 统一初始化titlebar右侧图片
     */
    protected Toolbar initToolBarRightImg(String title, int rightId, final OnRightClickListener listener) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> back());

        return toolbar;
    }


    /**
     * 统一初始化titlebar右侧文字
     */
    protected Toolbar initToolBarRightTxt(String title, String right, final OnRightClickListener listener) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> back());
        return toolbar;
    }

    protected void back() {
        if (activity != null) {
            activity.finish();
        }
    }

    protected void initBind() {
        bind = ButterKnife.bind(activity);

    }

    /**
     * 获取布局id
     *
     * @return
     */
    public abstract int getViewLayoutId();

    /**
     * 初始化数据
     *
     * @return
     */
    public abstract void initData();

    /**
     * 初始化view
     *
     * @return
     */
    public abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
        bind.unbind();
        RxBus.getDefault().unregister(mDisposables);
    }


    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected void setStatusBarColor(@ColorInt int color) {
        setStatusBarColor(color, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     * @param statusBarAlpha 透明度
     */
    public void setStatusBarColor(@ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        StatusBarUtil.setColorForSwipeBack(this, color, statusBarAlpha);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary),0);
    }

    /**
     * 子类自己实现，是否实用Rxbus,默认不使用
     *
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * 子类自己实现，处理接收到到Rxbus
     *
     * @param event
     */
    protected void onEvent(Event event) {

    }

}

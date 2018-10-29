package com.gank.chen.base.proxy;

import android.os.Bundle;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.base.BaseView;
import com.gank.chen.base.factory.IPresenterFactory;

/**
 * @author chen
 * @date 2017/12/17
 */

public class PresenterProxy<V extends BaseView, P extends BasePrestener<V>> implements IPresenterProxy<V, P> {


    /**
     * 获取onSaveInstanceState中bundle的key
     */
    private static final String PRESENTER_KEY = "presenter_key";
    /**
     * Presenter工厂类
     */
    private IPresenterFactory<V, P> mFactory;
    private P mPresenter;
    private Bundle mBundle;
    private boolean mIsBindView;

    public PresenterProxy(IPresenterFactory<V, P> mFactory) {
        this.mFactory = mFactory;
    }

    /**
     * 设置Presenter的工厂类,这个方法只能在创建Presenter之前调用,也就是调用getPresenter()之前，如果Presenter已经创建则不能再修改
     *
     * @param presenterfactory IWanPresenterFactory类型
     */
    @Override
    public void setPresenterFactory(IPresenterFactory<V, P> presenterfactory) {
        if (mPresenter != null) {
            throw new IllegalArgumentException("setPresenterFactory() can only be called before getPresenter() be called");
        }
        this.mFactory = presenterfactory;
    }

    /**
     * 获取Presenter工厂类
     *
     * @return
     */
    @Override
    public IPresenterFactory<V, P> getPresenterFactory() {
        return mFactory;
    }

    @Override
    public P getPresenter() {
        if (mFactory != null) {
            if (mPresenter == null) {
                mPresenter = mFactory.createPresenter();
            }
        }
        return mPresenter;
    }

    /**
     * 绑定Presenter和view
     *
     * @param mvpView
     */
    public void onCreate(V mvpView) {
        getPresenter();
        if (mPresenter != null && !mIsBindView && mvpView != null) {
            mPresenter.onBindView(mvpView);
            mIsBindView = true;
        }
    }

    /**
     * 销毁Presenter持有的View
     */
    private void onUnbindView() {
        if (mPresenter != null && mIsBindView) {
            mPresenter.onUnbindView();
            mIsBindView = false;
        }
    }

    /**
     * 销毁Presenter
     */
    public void onDestroy() {
        if (mPresenter != null) {
            onUnbindView();
            mPresenter.onDestroyPersenter();
            mPresenter = null;
        }
    }

    /**
     * 意外销毁的时候调用
     *
     * @return Bundle，存入回调给Presenter的Bundle和当前Presenter的id
     */
    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        getPresenter();
        if (mPresenter != null) {
            Bundle presenterBundle = new Bundle();
            //回调Presenter
            mPresenter.onSaveInstanceState(presenterBundle);
            bundle.putBundle(PRESENTER_KEY, presenterBundle);
        }
        return bundle;
    }

    /**
     * 意外关闭恢复Presenter
     *
     * @param savedInstanceState 意外关闭时存储的Bundler
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mBundle = savedInstanceState;
    }
}

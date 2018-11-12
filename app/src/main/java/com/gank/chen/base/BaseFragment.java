package com.gank.chen.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gank.chen.R;
import com.gank.chen.base.factory.IPresenterFactory;
import com.gank.chen.base.factory.PresenterFactory;
import com.gank.chen.base.proxy.IPresenterProxy;
import com.gank.chen.base.proxy.PresenterProxy;
import com.gank.chen.mvp.model.Event;
import com.gank.chen.mvp.view.PullDownLoadMoreViewImp;
import com.gank.chen.util.RouterUtil;
import com.gank.chen.util.RxBus;
import com.gank.chen.widget.StateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author chen
 * @date 2017/12/17
 */

public abstract class BaseFragment<V extends BaseView, P extends BasePrestener<V>> extends Fragment
        implements IPresenterProxy<V, P>, BaseFailView {
    private static final String TAG = "BaseFragment";
    private static final String PRESENTER_SAVE_KEY_FRAGMENT = "presenter_save_key_fragment";
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private PresenterProxy<V, P> mProxy = new PresenterProxy<>(PresenterFactory.<V, P>createFactory(getClass()));
    private Unbinder unbinder;
    public StateView stateView;
    private View view;
    /**
     * Fragment的View加载完毕的标记
     */
    private boolean isViewCreated;
    /**
     * Fragment对用户可见的标记
     */
    private boolean isDataLoaded;
    private RxPermissions rxPermissions;

    public int pageNum;
    public int page;
    private CompositeDisposable mDisposables;
    public SmartRefreshLayout smartRefresh;

    public void setStateView(View baseview) {
        stateView = StateView.inject(baseview);
        stateView.setOnRetryClickListener(this::initData);
    }

    public void setStateView(View baseview, boolean hasActionBar) {
        stateView = StateView.inject(baseview, hasActionBar);
        stateView.setOnRetryClickListener(this::initData);
    }

    public void initPageNum(int pageNums) {
        pageNum = pageNums;
        page = pageNum;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getViewLayoutId(), container, false);

        pageNum = 0;
        page = pageNum;
        unbinder = ButterKnife.bind(this, view);
        mProxy.onCreate((V) this);
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(getActivity());
        }
        setStateView(view.getRootView());
        smartRefresh = view.findViewById(R.id.smart_refresh);
        if (smartRefresh != null) {
            smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    onLoadMoreData(refreshLayout);
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    onRefreshData(refreshLayout);
                }
            });
            if (!useLoadMore()) {
                smartRefresh.setEnableLoadMore(false);
            }
        }

        initView();
        Log.e(TAG, "-------> onCreateView");
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
        return view;

    }

    public void checkPermission(Observer<Boolean> observer, String... permissions) {
        rxPermissions.request(permissions)
                .subscribe(observer);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
        prepareRequestData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        prepareRequestData();
        Log.e(TAG, "----@---> setUserVisibleHint");
    }

    public boolean prepareRequestData() {
        return prepareRequestData(false);
    }

    public boolean prepareRequestData(boolean forceUpdate) {
        if (getUserVisibleHint() && isViewCreated && (!isDataLoaded || forceUpdate)) {
            initData();
            isDataLoaded = true;
            return true;
        }
        return false;
    }

    /**
     * 布局
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
     * 初始化布局
     *
     * @return
     */
    public abstract void initView();

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
        unbinder.unbind();
        Log.e(TAG, "-------> onDestroy");
        RxBus.getDefault().unregister(mDisposables);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "-------> onDestroyView");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY_FRAGMENT, mProxy.onSaveInstanceState());
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

    /**
     * 子类不必实现，自动调用initData刷新数据
     *
     * @param refreshLayout
     */
    protected void onRefreshData(RefreshLayout refreshLayout) {
        page = pageNum;
        initData();
    }

    /**
     * 子类自己实现，onLoadSucess
     *
     * @param mlist
     */
    protected void onLoadSucess(List mlist) {
        smartRefresh.finishRefresh();
        page++;
    }

    /**
     * 子类自己实现，onLoadMoreData
     *
     * @param refreshLayout
     */
    protected void onLoadMoreData(RefreshLayout refreshLayout) {

    }

    /**
     * 子类自己实现，是否使用加载更多,默认使用
     *
     * @return
     */
    protected boolean useLoadMore() {
        return true;
    }

    /**
     * 子类自己实现，加载更多成功后的操作
     *
     * @param mlist
     */
    protected void onLoadMoreSuccess(List mlist) {
        page++;
        if (smartRefresh != null) {
            if (mlist == null) {
                return;
            }
            if (mlist.size() == 0) {
                smartRefresh.finishLoadMoreWithNoMoreData();
            } else {
                smartRefresh.finishLoadMore();
            }
        }
    }

    /**
     * 若无特殊需求，子类可以不处理加载更多失败后的操作。
     *
     * @param msg
     */
    @Override
    public void onLoadMoreFail(String msg) {
        smartRefresh.finishLoadMore();
        loadMoreFail(msg);
    }

    /**
     * 子类自己实现，loadMoreFail
     *
     * @param msg
     */
    protected void loadMoreFail(String msg) {

    }
}

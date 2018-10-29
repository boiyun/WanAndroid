package com.gank.chen.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gank.chen.mvp.model.Event;
import com.gank.chen.util.RxBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author chen
 * @date 2017/12/17
 */

public abstract class BaseNoNetFragment extends Fragment {
    private Unbinder unbinder;
    private View view;
    /**
     * Fragment的View加载完毕的标记
     */
    private boolean isViewCreated;
    /**
     * Fragment对用户可见的标记
     */
    private boolean isDataLoaded;
    private CompositeDisposable mDisposables;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getViewLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
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
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        RxBus.getDefault().unregister(mDisposables);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
}

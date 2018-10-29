package com.gank.chen.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.gank.chen.http.ApiFactory;
import com.gank.chen.http.ApiUtil;

/**
 * @author chen
 * @date 2017/12/17
 */

public abstract class BasePrestener<V extends BaseView> {
    private V baseView;
    private static final String TAG = "ChenGank";
    protected ApiUtil apiUtil;

    public BasePrestener() {
        apiUtil = ApiFactory.getApiUtil();
    }

    public void onCreatepersenter(@Nullable Bundle savedState) {
    }

    public void onBindView(V view) {
        this.baseView = view;
    }

    public void onUnbindView() {
        this.baseView = null;
    }

    public void onDestroyPersenter() {
        Log.e(TAG, "-------> onDestroyPersenter");
    }

    public void onSaveInstanceState(Bundle outState) {
        Log.e(TAG, "-------> onSaveInstanceState");
    }

    public V getView() {
        return baseView;
    }
}

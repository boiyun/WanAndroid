package com.gank.chen.mvp.view;

import com.gank.chen.base.BaseView;

/**
 * @author chen
 * @date 2017/12/17
 */

public interface OnLoadSuccessViewImp<T> extends BaseView {
    /**
     * 加载成功
     *
     * @param obj
     */
    void onLoadSucess(T obj);
}

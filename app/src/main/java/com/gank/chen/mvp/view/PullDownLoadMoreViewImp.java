package com.gank.chen.mvp.view;

import com.gank.chen.base.BaseFailView;
import com.gank.chen.base.BaseView;

/**
 * @author chen
 * @date 2017/12/17
 */

public interface PullDownLoadMoreViewImp<T> extends BaseFailView {
    /**
     * 加载成功
     *
     * @param obj
     */
    void onLoadSucess(T obj);
    /**
     * 加载更多成功
     *
     * @param obj
     */
    void onLoadMoreSuccess(T obj);


}

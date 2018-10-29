package com.gank.chen.base;

/**
 *
 * @author chen
 * @date 2017/12/17
 */

public interface BaseFailView  extends BaseView{
    /**
     * 加载更多失败
     *
     * @param msg
     */
    void onLoadMoreFail(String msg);
}

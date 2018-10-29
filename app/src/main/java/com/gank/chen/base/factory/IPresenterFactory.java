package com.gank.chen.base.factory;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.base.BaseView;

/**
 * @author chen
 * @date 2017/12/17
 */

public interface IPresenterFactory<V extends BaseView, P extends BasePrestener<V>> {
    /**
     * 创建Presenter
     *
     * @return Presenter
     */
    P createPresenter();
}

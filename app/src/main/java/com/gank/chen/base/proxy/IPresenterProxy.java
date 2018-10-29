package com.gank.chen.base.proxy;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.base.BaseView;
import com.gank.chen.base.factory.IPresenterFactory;

/**
 *
 * @author chen
 * @date 2017/12/17
 */

public interface IPresenterProxy<V extends BaseView,P extends BasePrestener<V>> {
    /**
     * 设置创建Presenter的工厂
     * @param presenterfactory 类型
     */
    void setPresenterFactory(IPresenterFactory<V, P> presenterfactory);

    /**
     * 获取Presenter的工厂类
     * @return IPresenterFactory
     */
    IPresenterFactory<V,P> getPresenterFactory();

    /**
     * 获取创建的Presenter
     * @return 指定类型的Presenter
     */
    P getPresenter();
}

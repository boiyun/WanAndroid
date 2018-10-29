package com.gank.chen.base.factory;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.base.BaseView;
import com.gank.chen.base.CreatePresenter;

/**
 *
 * @author chen
 * @date 2017/12/17
 */

public class PresenterFactory<V extends BaseView, P extends BasePrestener<V>>
        implements IPresenterFactory<V, P> {
    /**
     * 需要创建的Presenter的类型
     */
    private final Class<P> mPresenterClass;

    public PresenterFactory(Class<P> presenterclass) {
        this.mPresenterClass = presenterclass;
    }
    public static <V extends BaseView,P extends BasePrestener<V>> PresenterFactory<V,P> createFactory(Class<?> viewClazz){
        CreatePresenter annotation = viewClazz.getAnnotation(CreatePresenter.class);
        Class<P> aClass = null;
        if(annotation != null) {
            aClass = (Class<P>) annotation.value();
        }
        return aClass == null ? null : new PresenterFactory<V,P>(aClass);
    }
    @Override
    public P createPresenter() {
        try {
            return mPresenterClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Presenter create failed!，" +
                    "please check if declaration @CreatePresenter(xxx.class) anotation or not");
        }
    }
}

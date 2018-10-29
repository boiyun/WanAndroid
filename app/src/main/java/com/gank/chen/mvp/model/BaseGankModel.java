package com.gank.chen.mvp.model;

/**
 *
 * @author chen
 * @date 2017/12/17
 */

public class BaseGankModel<T> {
    public boolean error;
    public T results;

    public boolean isFalse() {
        return error;
    }

}

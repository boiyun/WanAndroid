package com.gank.chen.http.exception;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 *
 * @author pc
 * @date 2018/1/31
 */

public class FuncFlowableException<T> implements Function<Throwable, Flowable<T>> {

    @Override
    public Flowable<T> apply(Throwable throwable) {
        return Flowable.error(ApiException.handleException(null,throwable));
    }
}

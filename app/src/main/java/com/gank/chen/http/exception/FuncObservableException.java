package com.gank.chen.http.exception;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 *
 * @author pc
 * @date 2018/1/31
 */

public class FuncObservableException<T> implements Function<Throwable, Observable<T>> {

    @Override
    public Observable<T> apply(Throwable throwable) {
        return Observable.error(ApiException.handleException(null,throwable));
    }
}

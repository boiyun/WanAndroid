package com.gank.chen.http.exception;

import com.gank.chen.mvp.model.BaseGankModel;

import io.reactivex.functions.Function;


/**
 * Creat by chen on 2018/9/30
 * Describe:
 * @author chenbo
 */
public class HandleGankFuc<T> implements Function<BaseGankModel<T>, T> {
    @Override
    public T apply(BaseGankModel<T> response) {
        if (response.isFalse()) {
            throw new ServerException(400, "error");
        }
        return response.results;
    }
}
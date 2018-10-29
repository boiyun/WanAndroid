package com.gank.chen.http.exception;

import com.gank.chen.mvp.model.BaseModel;

import io.reactivex.functions.Function;


/**
 * Creat by chen on 2018/9/30
 * Describe:
 * @author chenbo
 */
public class HandleFuc<T> implements Function<BaseModel<T>, T> {
    @Override
    public T apply(BaseModel<T> response) {
        if (!response.isOk()) {
            throw new ServerException(response.errorCode, response.errorMsg);
        }
        return response.data;
    }
}
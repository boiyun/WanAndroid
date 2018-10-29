package com.gank.chen.http.exception;

import com.gank.chen.mvp.model.BaseModel;

import io.reactivex.functions.Function;


/**
 * Creat by chen on 2018/9/30
 * Describe:
 *
 * @author chenbo
 */
public class HandleBooleanFuc implements Function<BaseModel, Boolean> {
    @Override
    public Boolean apply(BaseModel response) {
        if (!response.isOk()) {
            return false;
        }
        return true;
    }
}
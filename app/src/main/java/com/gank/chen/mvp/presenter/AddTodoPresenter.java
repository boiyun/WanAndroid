package com.gank.chen.mvp.presenter;

import android.content.Context;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.http.ApiRetrofit;
import com.gank.chen.http.subscriber.SubscriberObserverProgress;
import com.gank.chen.mvp.view.ImpAddTodo;

import java.util.Map;

/**
 * Creat by chen on 2018/11/15
 * Describe:
 *
 * @author chenbo
 */
public class AddTodoPresenter extends BasePrestener<ImpAddTodo> {

    public void toAddOneToDo(Context context, Map<String, Object> map) {
        ApiRetrofit.setObservableBooleanSubscribe(apiUtil.toAddToDo(map)
                , new SubscriberObserverProgress<Boolean>(context) {
                    @Override
                    public void onSuccess(Boolean t) {
                        if (t) {
                            getView().onAddSuccess("添加成功");
                        } else {
                            getView().onAddFail("添加失败");
                        }

                    }
                });

    }

    public void toUpdateTheToDo(Context context, int id, Map<String, Object> map) {
        ApiRetrofit.setObservableBooleanSubscribe(apiUtil.toUpdateToDo(id, map), new SubscriberObserverProgress<Boolean>(context) {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean) {
                    getView().onUpdateSuccess();
                } else {
                    getView().onUpdateFail("更新失败");
                }

            }
        });
    }
}

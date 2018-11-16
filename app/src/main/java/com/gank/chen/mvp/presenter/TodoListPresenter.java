package com.gank.chen.mvp.presenter;

import android.content.Context;

import com.gank.chen.base.BasePrestener;
import com.gank.chen.http.ApiRetrofit;
import com.gank.chen.http.subscriber.ApiSubscriberObserver;
import com.gank.chen.http.subscriber.SubscriberObserverProgress;
import com.gank.chen.mvp.model.TodoList;
import com.gank.chen.mvp.view.ImpTodoList;
import com.gank.chen.widget.StateView;

/**
 * Creat by chen on 2018/11/15
 * Describe:
 *
 * @author chenbo
 */
public class TodoListPresenter extends BasePrestener<ImpTodoList> {

    public void getTodoList(Context context, StateView stateView, int type) {
        ApiRetrofit.setObservableSubscribe(apiUtil.toGetToDoList(type), new ApiSubscriberObserver<TodoList>(context, stateView) {
            @Override
            public void onSuccess(TodoList todoList) {
                if (todoList.getTodoList() == null || todoList.getTodoList().size() == 0) {
                    stateView.showEmpty();
                } else {
                    getView().onLoadDataSucess(todoList.getTodoList());
                }

            }
        });
    }

    public void getNotTodoList(Context context, StateView stateView, int type) {
        ApiRetrofit.setObservableSubscribe(apiUtil.toGetToDoList(type), new ApiSubscriberObserver<TodoList>(context, stateView) {
            @Override
            public void onSuccess(TodoList todoList) {
                if (todoList.getDoneList() == null || todoList.getDoneList().size() == 0) {
                    stateView.showEmpty();
                } else {
                    getView().onLoadDataSucess(todoList.getDoneList());
                }
            }
        });
    }

    public void deleteTheToDo(Context context, int id) {
        ApiRetrofit.setObservableBooleanSubscribe(apiUtil.toDeleteToDo(id), new SubscriberObserverProgress<Boolean>(context) {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean) {
                    getView().onDeleteSuccess("删除成功");
                } else {
                    getView().onDeleteFail("删除失败");
                }


            }
        });
    }

    public void toChangeStatus(Context context, int id, int status, int position) {
        ApiRetrofit.setObservableBooleanSubscribe(apiUtil.toDoneToDo(id, status), new SubscriberObserverProgress<Boolean>(context) {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean) {
                    getView().onChangeStatusSuccess("标记成功");
                } else {
                    getView().onChangeStatusFail("标记失败");
                }
            }
        });
    }
}

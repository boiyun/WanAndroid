package com.gank.chen.mvp.view;

import com.gank.chen.base.BaseView;
import com.gank.chen.mvp.model.TodoListData;

import java.util.List;

/**
 * @author chen
 */

public interface ImpAddTodo extends BaseView {
    /**
     * 更新成功
     */
    void onUpdateSuccess();

    /**
     * 更新失败
     *
     * @param msg
     */
    void onUpdateFail(String msg);

    /**
     * 添加成功
     */
    void onAddSuccess(String msg);

    /**
     * 添加失败
     */
    void onAddFail(String msg);

}

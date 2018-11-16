package com.gank.chen.mvp.view;

import com.gank.chen.base.BaseView;
import com.gank.chen.mvp.model.ArticleModel;
import com.gank.chen.mvp.model.TodoListData;

import java.util.List;

/**
 * @author chen
 * @date 2017/12/17
 */

public interface ImpTodoList extends BaseView {

    /**
     * 加载成功
     */
    void onLoadDataSucess(List<TodoListData> todoList);

    /**
     * 删除成功
     */
    void onDeleteSuccess(String msg);

    /**
     * 删除失败
     */
    void onDeleteFail(String msg);

    /**
     * 标记成功
     */
    void onChangeStatusSuccess(String msg);

    /**
     * 标记失败
     */
    void onChangeStatusFail(String msg);
//
//    /**
//     * 标记成功
//     */
//    void onChangeStatusFromNotDo2ToDoSuccess(String msg);
//
//    /**
//     * 标记失败
//     */
//    void onChangeStatusFromNotDo2ToDoFail(String msg);
}

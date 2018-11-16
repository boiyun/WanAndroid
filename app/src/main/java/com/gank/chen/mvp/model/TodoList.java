package com.gank.chen.mvp.model;

import java.util.List;

/**
 * Creat by chen on 2018/11/15
 * Describe:
 *
 * @author chenbo
 */
public class TodoList {
    private List<TodoListData> doneList;
    private List<TodoListData> todoList;

    public List<TodoListData> getDoneList() {
        return doneList;
    }

    public void setDoneList(List<TodoListData> doneList) {
        this.doneList = doneList;
    }

    public List<TodoListData> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<TodoListData> todoList) {
        this.todoList = todoList;
    }
}

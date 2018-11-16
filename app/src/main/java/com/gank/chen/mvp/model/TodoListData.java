package com.gank.chen.mvp.model;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gank.chen.adapter.TodoListAdapter;

import java.util.List;

/**
 * Creat by chen on 2018/11/15
 * Describe:
 *
 * @author chenbo
 */
public class TodoListData extends AbstractExpandableItem<TodoItemData> implements MultiItemEntity {
    private long date;
    private List<TodoItemData> todoList;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<TodoItemData> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<TodoItemData> todoList) {
        this.todoList = todoList;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return TodoListAdapter.TYPE_LEVEL_0;
    }
}

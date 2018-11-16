package com.gank.chen.mvp.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gank.chen.adapter.TodoListAdapter;

/**
 * Creat by chen on 2018/11/15
 * Describe:
 *
 * @author chenbo
 */
public class TodoItemData implements MultiItemEntity {
//     "completeDate": 1542124800000,
//             "completeDateStr": "2018-11-14",
//             "content": "提醒我一下",
//             "date": 1542124800000,
//             "dateStr": "2018-11-14",
//             "id": 4134,
//             "status": 1,
//             "title": "下一站枣园",
//             "type": 0,
//             "userId": 11772

    private long completeDate;
    private long date;
    private String completeDateStr;
    private String content;
    private String title;
    private String dateStr;
    private int id;
    private int status;
    private int type;
    private int userId;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public long getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(long completeDate) {
        this.completeDate = completeDate;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getCompleteDateStr() {
        return completeDateStr;
    }

    public void setCompleteDateStr(String completeDateStr) {
        this.completeDateStr = completeDateStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int getItemType() {
        return TodoListAdapter.TYPE_LEVEL_1;
    }
}

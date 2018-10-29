package com.gank.chen.mvp.model;

/**
 * Creat by chen on 2018/10/19
 * Describe:
 */
public class ChaptersModel {
//     "children": [],
//             "courseId": 13,
//             "id": 408,
//             "name": "鸿洋",
//             "order": 190000,
//             "parentChapterId": 407,
//             "userControlSetTop": false,
//             "visible": 1

    private int id;
    private int courseId;
    private int order;
    private int parentChapterId;
    private int visible;
    private boolean userControlSetTop;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

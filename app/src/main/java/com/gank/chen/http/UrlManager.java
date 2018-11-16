package com.gank.chen.http;

/**
 * Creat by chen on 2018/10/17
 * Describe:
 *
 * @author chenbo
 */
public class UrlManager {
    public static final String HOST = "http://gank.io/";
    public static final String HOST_Wan = "http://www.wanandroid.com/";

    /**
     * 注册
     */
    public static final String REGISTER = "user/register";
    /**
     * 登录
     */
    public static final String LOGIN = "user/login";
    /**
     * 退出
     */
    public static final String LOGOUT = "user/logout/json";
    /**
     * 获取banner
     */
    public static final String BANNER = "banner/json";
    /**
     * 首页文章列表
     */
    public static final String ARTICLE_LIST = "article/list/{page}/json";
    /**
     * 收藏文章的列表
     */
    public static final String COLLECT_LIST = "lg/collect/list/{page}/json";
    /**
     * 收藏站内文章
     */
    public static final String COLLECT = "lg/collect/{id}/json";
    /**
     * 取消收藏站内文章(文章列表)
     */
    public static final String UN_COLLECT = "lg/uncollect_originId/{id}/json";
    /**
     * 取消收藏站内文章(我的收藏)
     */
    public static final String UN_COLLECT_FROM_MINE = "lg/uncollect/{id}/json";
    /**
     * 获取公众号列表
     */
    public static final String CHAPTERS = "wxarticle/chapters/json";
    /**
     * 获取项目列表
     */
    public static final String PROJECT = "project/tree/json";
    /**
     * 查看某个公众号历史数据
     */
    public static final String CHAPTERS_LIST = "wxarticle/list/{id}/{page}/json";
    /**
     * 查看项目列表数据
     */
    public static final String PROJECTS_LIST = "project/list/{page}/json";
    /**
     * 获取常用网站
     */
    public static final String COMMON_WEBSITE = "friend/json";
    /**
     * 热搜词
     */
    public static final String HOT_KEY = "hotkey/json";
    /**
     * 搜索
     */
    public static final String QUERY = "article/query/{page}/json";
    /**
     * 导航数据
     */
    public static final String NAVIGATION = "navi/json";
    /**
     * ToDo数据列表
     */
    public static final String TODO_LIST = "lg/todo/list/{type}/json";
    /**
     * 新增一条Todo
     */
    public static final String TODO_ADD = "lg/todo/add/json";
    /**
     * 更新一条Todo内容
     */
    public static final String TODO_UPDATE = "lg/todo/update/{id}/json";
    /**
     * 删除一条Todo
     */
    public static final String TODO_DELETE = "lg/todo/delete/{id}/json";
    /**
     * 仅更新完成状态Todo
     */
    public static final String TODO_DONE = "lg/todo/done/{id}/json";
    /**
     * 未完成Todo列表
     */
    public static final String TODO_NOT_DO = "lg/todo/listnotdo/{type}/json/{page}";
    /**
     * 已完成Todo列表
     */
    public static final String TODO_LIST_DONE = "lg/todo/listdone/{type}/json/{page}";
}

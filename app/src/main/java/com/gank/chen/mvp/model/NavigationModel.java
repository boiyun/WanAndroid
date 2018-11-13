package com.gank.chen.mvp.model;

import java.util.List;

/**
 * Creat by chen on 2018/11/13
 * Describe:
 */
public class NavigationModel {
    //     "cid": 272,
//             "name": "常用网站"
//    articles

    private int cid;
    private String name;
    private List<ArticleListModel> articles;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArticleListModel> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleListModel> articles) {
        this.articles = articles;
    }
}

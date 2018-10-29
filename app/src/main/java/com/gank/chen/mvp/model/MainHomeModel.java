package com.gank.chen.mvp.model;

import java.util.ArrayList;

/**
 * @author chenbo
 */
public class MainHomeModel {

    private ArrayList<BannerModel> bannerModels;
    private ArticleModel articleListModels;

    public MainHomeModel(ArrayList<BannerModel> bannerModels, ArticleModel articleListModels) {
        this.bannerModels = bannerModels;
        this.articleListModels = articleListModels;
    }

    public ArrayList<BannerModel> getBannerModels() {
        return bannerModels;
    }

    public ArticleModel getArticleListModels() {
        return articleListModels;
    }

}

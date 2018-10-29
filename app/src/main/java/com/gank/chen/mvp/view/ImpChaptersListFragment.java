package com.gank.chen.mvp.view;

import com.gank.chen.base.BaseView;
import com.gank.chen.mvp.model.ArticleModel;
import com.gank.chen.mvp.model.ChaptersListModel;

import java.util.List;

/**
 * @author chen
 * @date 2017/12/17
 */

public interface ImpChaptersListFragment extends BaseView {
    /**
     * 收藏成功
     *
     * @param position
     */
    void onCollectSucess(int position);

    /**
     * 取消收藏成功
     *
     * @param position
     */
    void onUnCollectSucess(int position);

    /**
     * 加载成功
     *
     * @param obj
     */
    void onLoadSucess(ArticleModel obj);

    /**
     * 加载更多成功
     *
     * @param obj
     */
    void onLoadMoreSuccess(ArticleModel obj);
}

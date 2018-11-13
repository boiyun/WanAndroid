package com.gank.chen.mvp.view;

import com.gank.chen.base.BaseView;
import com.gank.chen.mvp.model.ChaptersListModel;
import com.gank.chen.mvp.model.NavigationModel;

import java.util.List;

/**
 * @author chen
 * @date 2017/12/17
 */

public interface ImpNavigationFragment extends BaseView {

    /**
     * 加载导航数据成功
     *
     * @param navigationModelslist
     */
    void onLoadNavigationSuccess(List<NavigationModel> navigationModelslist);
}

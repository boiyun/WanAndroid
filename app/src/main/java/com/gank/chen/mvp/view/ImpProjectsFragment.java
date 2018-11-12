package com.gank.chen.mvp.view;

import com.gank.chen.base.BaseView;
import com.gank.chen.mvp.model.ChaptersListModel;

import java.util.List;

/**
 * @author chen
 * @date 2017/12/17
 */

public interface ImpProjectsFragment extends BaseView {

    /**
     * 加载公众号列表成功
     *
     * @param chaptersListModel
     */
    void onLoadChapterSucess(List<ChaptersListModel> chaptersListModel);
}

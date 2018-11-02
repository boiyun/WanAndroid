package com.gank.chen.mvp.view;

import com.gank.chen.base.BaseView;
import com.gank.chen.mvp.model.CommonWebsiteModel;

import java.util.List;

/**
 * @author chen
 * @date 2017/12/17
 */

public interface ImpSearchActivity extends BaseView {
    /**
     * 加载热搜词成功
     *
     * @param searchModelList
     */
    void onLoadSuccess(List<CommonWebsiteModel> searchModelList);

    /**
     * 加载历史搜索词成功
     *
     * @param boo
     */
    void onLoadHistorySuccess(Boolean boo);
}

package com.gank.chen.ui.fragment.main;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gank.chen.R;
import com.gank.chen.adapter.ChaptersPageAdapter;
import com.gank.chen.base.BaseFragment;
import com.gank.chen.base.CreatePresenter;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.ChaptersListModel;
import com.gank.chen.mvp.presenter.ChaptersPresenter;
import com.gank.chen.mvp.view.ImpChaptersFragment;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * Creat by chen on 2018/10/19
 * Describe:公众号页面
 *
 * @author chenbo
 */
@CreatePresenter(ChaptersPresenter.class)
@Route(path = RouterUrlManager.CHAPTERS_FRAGMENT)
public class ChaptersFragment extends BaseFragment<ImpChaptersFragment, ChaptersPresenter>
        implements ImpChaptersFragment {
    @BindView(R.id.order_tablayout)
    TabLayout orderTablayout;
    @BindView(R.id.order_viewPager)
    ViewPager orderViewPager;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_chapters;
    }

    @Override
    public void initData() {
        getPresenter().getChaptersList(getActivity(), stateView);
    }

    @Override
    public void initView() {
        orderViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(orderTablayout));
        orderTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                orderViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void onLoadChapterSucess(List<ChaptersListModel> chaptersListModel) {

        ChaptersPageAdapter adapter = new ChaptersPageAdapter(getChildFragmentManager(), chaptersListModel);
        orderTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < chaptersListModel.size(); i++) {
            orderTablayout.addTab(orderTablayout.newTab().setText(chaptersListModel.get(i).getName()));
        }
        orderViewPager.setAdapter(adapter);
    }
}

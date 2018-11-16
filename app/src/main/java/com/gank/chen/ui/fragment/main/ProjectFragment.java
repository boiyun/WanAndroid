package com.gank.chen.ui.fragment.main;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gank.chen.R;
import com.gank.chen.adapter.ProjectsPageAdapter;
import com.gank.chen.base.BaseFragment;
import com.gank.chen.base.CreatePresenter;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.ChaptersListModel;
import com.gank.chen.mvp.presenter.ProjectsPresenter;
import com.gank.chen.mvp.view.ImpProjectsFragment;
import com.gank.chen.widget.ColorFlipPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * Creat by chen on 2018/10/19
 * Describe:公众号页面
 *
 * @author chenbo
 */
@CreatePresenter(ProjectsPresenter.class)
@Route(path = RouterUrlManager.PROJECT_FRAGMENT)
public class ProjectFragment extends BaseFragment<ImpProjectsFragment, ProjectsPresenter>
        implements ImpProjectsFragment {
    @BindView(R.id.order_tablayout)
    MagicIndicator orderTablayout;
    @BindView(R.id.order_viewPager)
    ViewPager orderViewPager;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_chapters;
    }

    @Override
    public void initData() {
        getPresenter().getProjects(getActivity(), stateView);
    }

    @Override
    public void initView() {
//        orderViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(orderTablayout));
//        orderTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                orderViewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }


    @Override
    public void onLoadChapterSucess(List<ChaptersListModel> chaptersListModel) {

        ProjectsPageAdapter adapter = new ProjectsPageAdapter(getChildFragmentManager()
                , chaptersListModel);
        orderViewPager.setAdapter(adapter);

        orderTablayout.setBackgroundColor(getActivity().getResources().getColor(R.color.color_ffffff));
        CommonNavigator commonNavigator7 = new CommonNavigator(getActivity());
        commonNavigator7.setScrollPivotX(0.65f);
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return chaptersListModel == null ? 0 : chaptersListModel.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(chaptersListModel.get(index).getName());
                simplePagerTitleView.setNormalColor(getActivity().getResources().getColor(R.color.color_757575));
                simplePagerTitleView.setSelectedColor(getActivity().getResources().getColor(R.color.color_212121));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setLineWidth(UIUtil.dip2px(context, 20));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(getResources().getColor(R.color.colorPrimary));
                return indicator;
            }
        });
        orderTablayout.setNavigator(commonNavigator7);

        ViewPagerHelper.bind(orderTablayout, orderViewPager);

    }
}

package com.gank.chen.ui.fragment.main;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gank.chen.R;
import com.gank.chen.adapter.ChaptersPageAdapter;
import com.gank.chen.base.BaseFragment;
import com.gank.chen.base.CreatePresenter;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.ChaptersListModel;
import com.gank.chen.mvp.presenter.ChaptersPresenter;
import com.gank.chen.mvp.view.ImpChaptersFragment;

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
@CreatePresenter(ChaptersPresenter.class)
@Route(path = RouterUrlManager.CHAPTERS_FRAGMENT)
public class ChaptersFragment extends BaseFragment<ImpChaptersFragment, ChaptersPresenter>
        implements ImpChaptersFragment {
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
        getPresenter().getChaptersList(getActivity(), stateView);
    }

    @Override
    public void initView() {
    }


    @Override
    public void onLoadChapterSucess(List<ChaptersListModel> chaptersListModel) {

        ChaptersPageAdapter adapter = new ChaptersPageAdapter(getChildFragmentManager(), chaptersListModel);
        orderViewPager.setAdapter(adapter);


        orderTablayout.setBackgroundColor(getActivity().getResources().getColor(R.color.color_ffffff));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.25f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return chaptersListModel == null ? 0 : chaptersListModel.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(chaptersListModel.get(index).getName());
                simplePagerTitleView.setNormalColor(getActivity().getResources().getColor(R.color.color_757575));
                simplePagerTitleView.setSelectedColor(getActivity().getResources().getColor(R.color.color_212121));
                simplePagerTitleView.setTextSize(14);
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
                indicator.setYOffset(UIUtil.dip2px(context, 3));
                indicator.setColors(getActivity().getResources().getColor(R.color.colorPrimary));
                return indicator;
            }
        });
        orderTablayout.setNavigator(commonNavigator);
        ViewPagerHelper.bind(orderTablayout, orderViewPager);

    }
}

package com.gank.chen.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gank.chen.R;
import com.gank.chen.adapter.HomePageAdapter;
import com.gank.chen.base.BaseNoNetActivity;
import com.gank.chen.common.ConstantMap;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.Event;
import com.gank.chen.util.RouterUtil;
import com.gank.chen.util.RxBus;
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

import butterknife.BindView;

/**
 * @author chenbo
 */
@Route(path = RouterUrlManager.TODOLIST_ACTIVITY, extras = ConstantMap.LOGIN_EXTRA)
public class ToDoListActivity extends BaseNoNetActivity {

    @BindView(R.id.order_tablayout)
    MagicIndicator orderTablayout;
    @BindView(R.id.order_viewPager)
    ViewPager orderViewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_todo_add)
    FloatingActionButton fabTodoAdd;

    private int TYPE;

    @Override
    public int getViewLayoutId() {
        return R.layout.activity_todo_list;
    }

    @Override
    public void initData() {
        String[] homeTab = getResources().getStringArray(R.array.order_tab);

        HomePageAdapter adapter = new HomePageAdapter(getSupportFragmentManager(), homeTab);

        orderViewPager.setAdapter(adapter);


        CommonNavigator commonNavigator7 = new CommonNavigator(this);
        commonNavigator7.setScrollPivotX(0.65f);
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return homeTab.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(homeTab[index]);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.color_60white));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.color_ffffff));
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
                indicator.setColors(getResources().getColor(R.color.color_ffffff));
                return indicator;
            }
        });
        orderTablayout.setNavigator(commonNavigator7);

        ViewPagerHelper.bind(orderTablayout, orderViewPager);
    }

    @Override
    public void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> back());
        fabTodoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", TYPE);
                RouterUtil.goToActivity(RouterUrlManager.ADD_TODO_ACTIVITY, bundle);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_one:
                TYPE = ConstantMap.TODO_THIS_ONE;
                RxBus.getDefault().post(new Event<>(Event.TODO_TYPE, TYPE));
                break;
            case R.id.action_work:
                TYPE = ConstantMap.TODO_WORK;
                RxBus.getDefault().post(new Event<>(Event.TODO_TYPE, TYPE));
                break;
            case R.id.action_study:
                TYPE = ConstantMap.TODO_STUDY;
                RxBus.getDefault().post(new Event<>(Event.TODO_TYPE, TYPE));
                break;
            case R.id.action_life:
                TYPE = ConstantMap.TODO_LIFE;
                RxBus.getDefault().post(new Event<>(Event.TODO_TYPE, TYPE));
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}

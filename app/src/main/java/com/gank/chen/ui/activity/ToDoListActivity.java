package com.gank.chen.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gank.chen.R;
import com.gank.chen.adapter.HomePageAdapter;
import com.gank.chen.base.BaseNoNetActivity;
import com.gank.chen.common.ConstantMap;
import com.gank.chen.common.RouterUrlManager;

import butterknife.BindView;

/**
 * @author chenbo
 */
@Route(path = RouterUrlManager.TODOLIST_ACTIVITY, extras = ConstantMap.LOGIN_EXTRA)
public class ToDoListActivity extends BaseNoNetActivity {

    @BindView(R.id.order_tablayout)
    TabLayout orderTablayout;
    @BindView(R.id.order_viewPager)
    ViewPager orderViewPager;

    @Override
    public int getViewLayoutId() {
        return R.layout.activity_mine_order;
    }

    @Override
    public void initData() {
        String[] homeTab = getResources().getStringArray(R.array.order_tab);

        HomePageAdapter adapter = new HomePageAdapter(getSupportFragmentManager(), homeTab);
        orderTablayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < homeTab.length; i++) {
            orderTablayout.addTab(orderTablayout.newTab().setText(homeTab[i]));
        }
        orderViewPager.setAdapter(adapter);
    }

    @Override
    public void initView() {
        initToolBar("todo", false);
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


}

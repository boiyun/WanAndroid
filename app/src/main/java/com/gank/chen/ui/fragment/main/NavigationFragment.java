package com.gank.chen.ui.fragment.main;


import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gank.chen.R;
import com.gank.chen.adapter.NavigationAdapter;
import com.gank.chen.base.BaseFragment;
import com.gank.chen.base.CreatePresenter;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.NavigationModel;
import com.gank.chen.mvp.presenter.NavigationPresenter;
import com.gank.chen.mvp.view.ImpNavigationFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * 导航页面
 *
 * @author chenbo
 */
@CreatePresenter(NavigationPresenter.class)
@Route(path = RouterUrlManager.NAVIGATION_FRAGMENT)
public class NavigationFragment extends BaseFragment<ImpNavigationFragment, NavigationPresenter>
        implements ImpNavigationFragment {

    @BindView(R.id.vtl_navigation)
    VerticalTabLayout vtlNavigation;
    @BindView(R.id.view_navigation)
    View viewNavigation;
    @BindView(R.id.rv_navigation)
    RecyclerView rvNavigation;

    private boolean needScroll;
    private int index;
    private boolean isClickTab;
    private LinearLayoutManager manager;
    private List<NavigationModel> navigationModelslist = new ArrayList<>();
    private NavigationAdapter adapter;

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    public void initData() {
        getPresenter().getNavigationData(getActivity(), stateView);
    }

    @Override
    public void initView() {
        manager = new LinearLayoutManager(getActivity());
        rvNavigation.setLayoutManager(manager);
        adapter = new NavigationAdapter(navigationModelslist);
        rvNavigation.setAdapter(adapter);
    }


    @Override
    public void onLoadNavigationSuccess(List<NavigationModel> navigationModelslist) {
        vtlNavigation.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return navigationModelslist == null ? 0 : navigationModelslist.size();
            }

            @Override
            public ITabView.TabBadge getBadge(int i) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int i) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int i) {
                return new TabView.TabTitle.Builder()
                        .setContent(navigationModelslist.get(i).getName())
                        .setTextColor(ContextCompat.getColor(getActivity(), R.color.color_ff4081),
                                ContextCompat.getColor(getActivity(), R.color.color_212121))
                        .build();
            }

            @Override
            public int getBackground(int i) {
                return -1;
            }
        });
        adapter.setNewData(navigationModelslist);
        leftRightLinkage();
    }

    private void leftRightLinkage() {
        rvNavigation.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (needScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    scrollRecyclerView();
                }
                rightLinkageLeft(newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (needScroll) {
                    scrollRecyclerView();
                }
            }
        });

        vtlNavigation.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tabView, int i) {
                isClickTab = true;
                selectTag(i);
            }

            @Override
            public void onTabReselected(TabView tabView, int i) {
            }
        });
    }

    private void scrollRecyclerView() {
        needScroll = false;
        int indexDistance = index - manager.findFirstVisibleItemPosition();
        if (indexDistance >= 0 && indexDistance < rvNavigation.getChildCount()) {
            int top = rvNavigation.getChildAt(indexDistance).getTop();
            rvNavigation.smoothScrollBy(0, top);
        }
    }

    private void rightLinkageLeft(int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (isClickTab) {
                isClickTab = false;
                return;
            }
            int firstPosition = manager.findFirstVisibleItemPosition();
            if (index != firstPosition) {
                index = firstPosition;
                setChecked(index);
            }
        }
    }

    private void selectTag(int i) {
        index = i;
        rvNavigation.stopScroll();
        smoothScrollToPosition(i);
    }

    private void setChecked(int position) {
        if (isClickTab) {
            isClickTab = false;
        } else {
            if (vtlNavigation == null) {
                return;
            }
            vtlNavigation.setTabSelected(index);
        }
        index = position;
    }

    private void smoothScrollToPosition(int currentPosition) {
        int firstPosition = manager.findFirstVisibleItemPosition();
        int lastPosition = manager.findLastVisibleItemPosition();
        if (currentPosition <= firstPosition) {
            rvNavigation.smoothScrollToPosition(currentPosition);
        } else if (currentPosition <= lastPosition) {
            int top = rvNavigation.getChildAt(currentPosition - firstPosition).getTop();
            rvNavigation.smoothScrollBy(0, top);
        } else {
            rvNavigation.smoothScrollToPosition(currentPosition);
            needScroll = true;
        }
    }
}

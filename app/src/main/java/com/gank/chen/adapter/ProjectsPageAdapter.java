package com.gank.chen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.gank.chen.mvp.model.ChaptersListModel;
import com.gank.chen.ui.fragment.FragmentFactory;

import java.util.List;

/**
 * @author chen
 * @date 2017/12/16
 */

public class ProjectsPageAdapter extends FragmentStatePagerAdapter {
    private List<ChaptersListModel> mhomeTab;

    public ProjectsPageAdapter(FragmentManager fm, List<ChaptersListModel> fragments) {
        super(fm);
        this.mhomeTab = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.creatProjectFragment(mhomeTab.get(position));
    }

    @Override
    public int getCount() {
        return mhomeTab.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mhomeTab.get(position).getName();
    }
}

package com.gank.chen.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.gank.chen.mvp.model.ChaptersListModel;
import com.gank.chen.ui.fragment.FragmentFactory;

import java.util.List;

/**
 * @author chen
 * @date 2017/12/16
 */

public class ChaptersPageAdapter extends FragmentStatePagerAdapter {
    private List<ChaptersListModel> mhomeTab;

    public ChaptersPageAdapter(FragmentManager fm, List<ChaptersListModel> fragments) {
        super(fm);
        this.mhomeTab = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.creatChaptersFragment(mhomeTab.get(position));
    }

    @Override
    public int getCount() {
        return mhomeTab.size();
    }

//    @Override
////    public int getItemPosition(@NonNull Object object) {
////        return PagerAdapter.POSITION_NONE;
////    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mhomeTab.get(position).getName();
    }
}

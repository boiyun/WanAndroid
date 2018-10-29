package com.gank.chen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.gank.chen.ui.fragment.FragmentFactory;

/**
 *
 * @author chen
 * @date 2017/12/16
 */

public class HomePageAdapter extends FragmentStatePagerAdapter {
    private String[] mhomeTab;

    public HomePageAdapter(FragmentManager fm, String[] fragments) {
        super(fm);
        this.mhomeTab = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return mhomeTab.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mhomeTab[position];
    }
}

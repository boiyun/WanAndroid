package com.gank.chen.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.ChaptersListModel;
import com.gank.chen.util.RouterUtil;

import java.util.List;

/**
 * @author chenbo
 * @date 2018/1/27
 */

public class FragmentFactory {
    public static Fragment creatChaptersFragment(ChaptersListModel mhomeTab) {
        Fragment fragment = null;
        if (mhomeTab != null) {
            fragment = RouterUtil.getFragment(RouterUrlManager.CHAPTERSLIST_FRAGMENT);
            Bundle bundle = new Bundle();
            bundle.putInt("id", mhomeTab.getId());
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    public static Fragment creatProjectFragment(ChaptersListModel mhomeTab) {
        Fragment fragment = null;
        if (mhomeTab != null) {
            fragment = RouterUtil.getFragment(RouterUrlManager.PROJECTLIST_FRAGMENT);
            Bundle bundle = new Bundle();
            bundle.putInt("id", mhomeTab.getId());
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    public static Fragment creatTodoFragment(int position) {
        Fragment fragment = RouterUtil.getFragment(RouterUrlManager.TODOLIST_FRAGMENT);
        Bundle bundle = new Bundle();
        bundle.putInt("doneOrNot", position);
        fragment.setArguments(bundle);
        return fragment;
    }
}

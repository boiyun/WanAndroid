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
    public static Fragment creatFragment( ChaptersListModel mhomeTab) {
        Fragment fragment = null;
        if (mhomeTab != null) {
            fragment = RouterUtil.getFragment(RouterUrlManager.ANDROID_FRAGMENT);
            Bundle bundle = new Bundle();
            bundle.putInt("id", mhomeTab.getId());
            fragment.setArguments(bundle);
        }

//        switch (position) {
//            case 0:
//                fragment = RouterUtil.getFragment(RouterUrlManager.ANDROID_FRAGMENT);
//                Bundle bundle = new Bundle();
//                bundle.putString("id", "Android");
//                fragment.setArguments(bundle);
//                break;
//            case 1:
//                fragment = RouterUtil.getFragment(RouterUrlManager.ANDROID_FRAGMENT);
//                Bundle bundle1 = new Bundle();
//                bundle1.putString("id", "iOS");
//                fragment.setArguments(bundle1);
//                break;
//            case 2:
//                fragment = RouterUtil.getFragment(RouterUrlManager.ANDROID_FRAGMENT);
//                Bundle bundle2 = new Bundle();
//                bundle2.putString("id", "前端");
//                fragment.setArguments(bundle2);
//                break;
//            case 3:
//                fragment = RouterUtil.getFragment(RouterUrlManager.ANDROID_FRAGMENT);
//                Bundle bundle3 = new Bundle();
//                bundle3.putString("id", "休息视频");
//                fragment.setArguments(bundle3);
//                break;
//            default:
//                break;
//        }
        return fragment;
    }

}

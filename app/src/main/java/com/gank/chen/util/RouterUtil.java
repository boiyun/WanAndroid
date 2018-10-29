package com.gank.chen.util;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gank.chen.common.BaseLoginInterceptor;
import com.gank.chen.common.ConstantMap;

/**
 * Creat by chen on 2018/10/10
 * Describe:
 *
 * @author chenbo
 */
public class RouterUtil {
    /**
     * 通过url跳转页面
     *
     * @param url
     */
    public static void goToActivity(String url) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ConstantMap.IS_LOGIN, SharePreferenceUtil.getBoolean(ConstantMap.IS_LOGIN, false));
        ARouter.getInstance().build(url).with(bundle).setProvider(new BaseLoginInterceptor()).navigation();
    }

    /**
     * 通过url,bundle跳转页面
     *
     * @param url
     * @param bundle
     */
    public static void goToActivity(String url, Bundle bundle) {
        bundle.putBoolean(ConstantMap.IS_LOGIN, SharePreferenceUtil.getBoolean(ConstantMap.IS_LOGIN, false));
        ARouter.getInstance().build(url).with(bundle).setProvider(new BaseLoginInterceptor()).navigation();
    }

    /**
     * startActivityForResult
     *
     * @param
     */
    public static void goToActivity(String url, Activity activity, int requestcode) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ConstantMap.IS_LOGIN, SharePreferenceUtil.getBoolean(ConstantMap.IS_LOGIN, false));
        ARouter.getInstance().build(url).with(bundle).setProvider(new BaseLoginInterceptor()).navigation(activity, requestcode);
    }

    /**
     * 获取Fragment
     *
     * @return
     */
    public static Fragment getFragment(String url) {
        return (Fragment) ARouter.getInstance().build(url).navigation();
    }
}

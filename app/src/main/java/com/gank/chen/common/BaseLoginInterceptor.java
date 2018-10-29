package com.gank.chen.common;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.gank.chen.util.RouterUtil;

/**
 * Creat by chen on 2018/10/10
 * Describe:登录的拦截器
 *
 * @author chenbo
 */
@Interceptor(priority = 8, name = ConstantMap.LOGIN_INTERCEPTOR)
public class BaseLoginInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (postcard.getExtra() == ConstantMap.LOGIN_EXTRA) {
            //判断是否登录。
            boolean isLogin = postcard.getExtras().getBoolean(ConstantMap.IS_LOGIN);
            if (isLogin) {
                callback.onContinue(postcard);
            } else {
                //如果没有登录，那么跳转到登录界面。
                RouterUtil.goToActivity(RouterUrlManager.LOGIN);
            }
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {

    }
}

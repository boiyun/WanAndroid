package com.gank.chen.common;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gank.chen.BuildConfig;
import com.gank.chen.R;
import com.gank.chen.http.ApiScheduler;
import com.gank.swipebacklibrary.swipebacklayout.BGASwipeBackHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.smtt.sdk.QbSdk;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;


/**
 * @author chen
 * @date 2017/12/17
 */

public class MyApplication extends Application {
    public static Context mContext;
    private static String fileStrongPath;
    List<Class<? extends View>> mlist = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        mlist.add(Banner.class);
        BGASwipeBackHelper.init(this, mlist);
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
        initX5WebView();
        Cockroach.install(new Cockroach.ExceptionHandler() {
            @Override
            public void handlerException(Thread thread, Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
                            Log.e("AndroidRuntime", "--->CockroachException:" + thread + "<---", throwable);
//                        throw new RuntimeException("..."+(i++));
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
    }

    @SuppressLint("CheckResult")
    private void initX5WebView() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) {
                if (!QbSdk.isTbsCoreInited()) {
                    QbSdk.preInit(mContext, null);
                }
                QbSdk.initX5Environment(mContext, null);
                e.onNext(true);
            }
        }).compose(ApiScheduler.getObservableScheduler())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Log.i("X5WebView", "onNext");
                        }

                    }
                });

    }

    public static Context getmContext() {
        return mContext;
    }

    public static String getFileUrl() {
        if (fileStrongPath == null) {
            fileStrongPath = Environment.getExternalStorageDirectory().getPath() + "/Gank/file/";
        }
        return fileStrongPath;

    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的基本设置构建器
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout refreshLayout) {
                //取消内容不满一页时开启上拉加载功能
                refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
                //是否在刷新的时候禁止列表的操作
                refreshLayout.setDisableContentWhenRefresh(true);
                //是否在加载的时候禁止列表的操作
                refreshLayout.setDisableContentWhenLoading(true);
            }
        });
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //全局设置主题颜色
                layout.setPrimaryColorsId(R.color.color_60white, R.color.color_212121);
                //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
                return new ClassicsHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
}

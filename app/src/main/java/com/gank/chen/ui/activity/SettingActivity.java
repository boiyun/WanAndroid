package com.gank.chen.ui.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gank.chen.R;
import com.gank.chen.base.BaseActivity;
import com.gank.chen.base.BaseNoNetActivity;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.http.ApiScheduler;
import com.gank.chen.http.subscriber.SubscriberObserverProgress;
import com.gank.chen.util.CommenUtil;
import com.gank.chen.util.DataCleanManager;
import com.gank.chen.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author chenbo
 */
@Route(path = RouterUrlManager.SETTING)
public class SettingActivity extends BaseNoNetActivity {

    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.rl_version)
    RelativeLayout rlVersion;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.rl_clear)
    RelativeLayout rlClear;

    @Override
    public int getViewLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initData() {
        String version = CommenUtil.getVersion(activity);
        tvVersion.setText("V" + version);
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                try {
                    String totalCacheSize = DataCleanManager.getTotalCacheSize(activity);
                    emitter.onNext(totalCacheSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                emitter.onComplete();
            }
        }).compose(ApiScheduler.getObservableScheduler())
                .subscribe(new SubscriberObserverProgress<String>(SettingActivity.this) {
                    @Override
                    public void onSuccess(String s) {
                        tvClear.setText(s);
                    }
                });

    }

    @Override
    public void initView() {
        initToolBar("设置", false);
    }

    @OnClick({R.id.rl_version, R.id.rl_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_version:
                ToastUtils.showToast(activity, "检查更新");
                break;
            case R.id.rl_clear:
                Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> emitter) {
                        emitter.onNext(DataCleanManager.clearAllCache(activity));
                        emitter.onComplete();
                    }
                }).compose(ApiScheduler.getObservableScheduler())
                        .subscribe(new SubscriberObserverProgress<Boolean>(SettingActivity.this) {
                            @Override
                            public void onSuccess(Boolean aLong) {
                                if (aLong) {
                                    tvClear.setText("0K");
                                } else {
                                    ToastUtils.showToast(activity, "清理失败");
                                }

                            }
                        });
                break;
            default:
                break;
        }
    }
}

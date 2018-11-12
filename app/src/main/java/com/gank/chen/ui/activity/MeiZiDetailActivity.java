package com.gank.chen.ui.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gank.chen.R;
import com.gank.chen.base.BaseNoNetActivity;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.util.FileUtils;
import com.gank.chen.util.NativeShareUtil;
import com.gank.chen.util.ToastUtils;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author chenbo
 */
@Route(path = RouterUrlManager.MEIZI_DETAIL)
public class MeiZiDetailActivity extends BaseNoNetActivity {

    @BindView(R.id.iv_meizi_detail)
    PhotoView ivMeiziDetail;
    private Bitmap bitmap;


    @Override
    public int getViewLayoutId() {
        return R.layout.activity_mei_zi_detail;
    }

    @Override
    public void initData() {
        String picurl = getIntent().getStringExtra("picurl");
        Glide.with(activity).load(picurl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                ivMeiziDetail.setImageBitmap(resource);
                bitmap = resource;
            }
        });
    }

    @Override
    public void initView() {
        initToolBar("图片详情", false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_shares:
                checkPermission(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Uri uri = FileUtils.saveBitmapToSDCard(activity, bitmap, "test/demo");
                            NativeShareUtil.shareImage(activity, uri, "分享到");
                        } else {
                            ToastUtils.showToast(activity, "需要您允许申请的权限哦");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

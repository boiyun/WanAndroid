//package com.gank.chen.ui.fragment.main;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.net.Uri;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.gank.chen.R;
//import com.gank.chen.base.BaseFragment;
//import com.gank.chen.base.CreatePresenter;
//import com.gank.chen.common.ConstantMap;
//import com.gank.chen.common.RouterUrlManager;
//import com.gank.chen.mvp.model.Event;
//import com.gank.chen.mvp.model.MeiZi;
//import com.gank.chen.mvp.presenter.MinePresenter;
//import com.gank.chen.mvp.view.OnLoadSuccessViewImp;
//import com.gank.chen.util.GlideCircleTransform;
//import com.gank.chen.util.RouterUtil;
//import com.gank.chen.util.ToastUtils;
//import com.gank.chen.util.SharePreferenceUtil;
//import com.zhihu.matisse.Matisse;
//import com.zhihu.matisse.MimeType;
//import com.zhihu.matisse.engine.impl.GlideEngine;
//import com.zhihu.matisse.internal.entity.CaptureStrategy;
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//import io.reactivex.Observer;
//import io.reactivex.disposables.Disposable;
//
//
///**
// * @author chenbo
// */
//@CreatePresenter(MinePresenter.class)
//public class MineFragment extends BaseFragment<OnLoadSuccessViewImp<List<MeiZi>>, MinePresenter>
//        implements OnLoadSuccessViewImp {
//    @BindView(R.id.ll_mine)
//    LinearLayout ll_mine;
//
//    @BindView(R.id.iv_userheader)
//    ImageView ivUserheader;
//    @BindView(R.id.tv_username)
//    TextView tvUsername;
//    @BindView(R.id.tv_mine_collection)
//    TextView ivMineCollection;
//    @BindView(R.id.tv_mine_gouwu)
//    TextView ivMineGouwu;
//    @BindView(R.id.tv_mine_youhui)
//    TextView ivMineYouhui;
//    @BindView(R.id.tv_mine_collections)
//    TextView tvMineCollection;
//    @BindView(R.id.tv_mine_gouwus)
//    TextView tvMineGouwu;
//    @BindView(R.id.tv_mine_youhuis)
//    TextView tvMineYouhui;
//    @BindView(R.id.ll_collection)
//    LinearLayout llCollection;
//    @BindView(R.id.ll_gouwu)
//    LinearLayout llGouwu;
//    @BindView(R.id.ll_youhui)
//    LinearLayout llYouhui;
////    @BindView(R.id.iv_setting)
////    ImageView ivSetting;
//    List<Uri> mSelected;
//
//    @BindView(R.id.rl_mine_dingdan)
//    RelativeLayout rlMineDingdan;
//    @BindView(R.id.rl_yuyue)
//    RelativeLayout rlYuyue;
//    @BindView(R.id.rl_jiameng)
//    RelativeLayout rlJiameng;
//    private static final int REQUEST_CODE_CHOOSE = 1000;
//
//    @Override
//    public int getViewLayoutId() {
//        return R.layout.fragment_mine;
//    }
//
//    @Override
//    public void initData() {
//
//    }
//
//    @Override
//    public void initView() {
//        String user = SharePreferenceUtil.getString(ConstantMap.USER_HEADER_ICON, "");
//        if (!TextUtils.isEmpty(user)) {
//            Glide.with(getActivity()).load(Uri.parse(user)).transform(new GlideCircleTransform(getActivity())).into(ivUserheader);
//        }
//    }
//
//
//    @Override
//    public void onLoadSucess(Object obj) {
//
//    }
//
//    @OnClick({R.id.iv_userheader, R.id.ll_collection, R.id.ll_gouwu
//            , R.id.ll_youhui
//            , R.id.tv_username, R.id.rl_jiameng
//            , R.id.rl_yuyue, R.id.rl_mine_dingdan})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.iv_userheader:
//                checkPermission(new Observer<Boolean>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Boolean aBoolean) {
//                        if (aBoolean) {
//                            Matisse.from(MineFragment.this)
//                                    .choose(MimeType.allOf())
//                                    .countable(true)
//                                    // 图片选择的最多数量
//                                    .maxSelectable(1)
//                                    .capture(true)
//                                    .captureStrategy(new CaptureStrategy(true, "com.gank.chen.fileprovider"))
//                                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                                    // 缩略图的比例
//                                    .thumbnailScale(0.85f)
//                                    .imageEngine(new GlideEngine())
//                                    .forResult(REQUEST_CODE_CHOOSE);
//                        } else {
//                            ToastUtils.showToast(getActivity(), "需要您允许申请的权限哦");
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
//                break;
//
//            case R.id.ll_youhui:
//                ToastUtils.showToast(getActivity(), "领券中心");
//                break;
//            case R.id.ll_gouwu:
//                ToastUtils.showToast(getActivity(), "购物车");
//                break;
//            case R.id.ll_collection:
//                ToastUtils.showToast(getActivity(), "我的收藏");
//                break;
////            case R.id.iv_setting:
////                RouterUtil.goToActivity(RouterUrlManager.SETTING);
////                break;
//            case R.id.tv_username:
//                RouterUtil.goToActivity(RouterUrlManager.LOGIN);
//                break;
//            case R.id.rl_jiameng:
//                ToastUtils.showToast(getActivity(), "加盟");
//                break;
//            case R.id.rl_yuyue:
//                ToastUtils.showToast(getActivity(), "预约");
//                break;
//            case R.id.rl_mine_dingdan:
//                RouterUtil.goToActivity(RouterUrlManager.MINE_ORDER);
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == -1) {
//            mSelected = Matisse.obtainResult(data);
//            Uri uri = mSelected.get(0);
//            Glide.with(getActivity()).load(uri).transform(new GlideCircleTransform(getActivity())).into(ivUserheader);
//
//            SharePreferenceUtil.setString(ConstantMap.USER_HEADER_ICON, uri.toString());
//        }
//    }
//
//    @Override
//    protected boolean useEventBus() {
//        return true;
//    }
//
//    @Override
//    protected void onEvent(Event event) {
//        super.onEvent(event);
//        if (event.getCode() == Event.EVENT_LOGIN_SUCCESS) {
//            String data = (String) event.getData();
//            tvUsername.setText(data);
//        }
//
//    }
//}

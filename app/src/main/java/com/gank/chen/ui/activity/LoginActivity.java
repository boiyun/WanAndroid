package com.gank.chen.ui.activity;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gank.chen.R;
import com.gank.chen.base.BaseNoNetActivity;
import com.gank.chen.base.CreatePresenter;
import com.gank.chen.common.ConstantMap;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.http.ApiScheduler;
import com.gank.chen.mvp.model.Event;
import com.gank.chen.mvp.model.RegisterModel;
import com.gank.chen.mvp.presenter.LoginPresenter;
import com.gank.chen.mvp.view.OnLoadSuccessViewImp;
import com.gank.chen.util.CommenUtil;
import com.gank.chen.util.RouterUtil;
import com.gank.chen.util.RxBus;
import com.gank.chen.util.SharePreferenceUtil;
import com.gank.chen.util.ToastUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author chenbo
 * @date 2018/5/21
 * 登录页面
 */
@Route(path = RouterUrlManager.LOGIN)
@CreatePresenter(LoginPresenter.class)
public class LoginActivity extends BaseNoNetActivity<OnLoadSuccessViewImp<RegisterModel>, LoginPresenter> implements OnLoadSuccessViewImp {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_change_password)
    TextView loginChangePassword;
    @BindView(R.id.login_register)
    TextView loginRegister;
    @BindView(R.id.til_phone)
    TextInputLayout tilPhone;
    /**
     * 密码栏
     */
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.til_verification_code)
    TextInputLayout tilVerificationCode;
    @BindView(R.id.tv_sendcode)
    TextView tvSendcode;
    /**
     * 点击切换登录方式
     */
    @BindView(R.id.tv_changetype)
    TextView tvChangetype;
    /**
     * 发送验证码栏
     */
    @BindView(R.id.rl_sendcord)
    RelativeLayout rlSendcord;
    private Disposable disposable;

    @Override
    public int getViewLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        initToolBar(getResources().getString(R.string.login), false);
    }

    @OnClick({R.id.login_btn, R.id.login_change_password
            , R.id.login_register
            , R.id.tv_sendcode
            , R.id.tv_changetype})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                checkLoginData();
                break;
            case R.id.login_change_password:
                break;
            case R.id.login_register:
                RouterUtil.goToActivity(RouterUrlManager.REGISTER);
                break;
            case R.id.tv_sendcode:
                sendVode();
                break;
            case R.id.tv_changetype:
                if (getResources().getString(R.string.password_login).contentEquals(tvChangetype.getText())) {
                    tvChangetype.setText(getResources().getString(R.string.verificationcode_login));
                    rlSendcord.setVisibility(View.GONE);
                    tilPassword.setVisibility(View.VISIBLE);
                } else {
                    tvChangetype.setText(getResources().getString(R.string.password_login));
                    rlSendcord.setVisibility(View.VISIBLE);
                    tilPassword.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 检查手机号和密码
     */
    private void checkLoginData() {
        String account = Objects.requireNonNull(tilPhone.getEditText()).getText().toString();
        String password = Objects.requireNonNull(tilPassword.getEditText()).getText().toString();
        String verificationcode = Objects.requireNonNull(tilVerificationCode.getEditText()).getText().toString();
        tilPhone.setErrorEnabled(false);
        tilPassword.setErrorEnabled(false);
//        if (getResources().getString(R.string.password_login).contentEquals(tvChangetype.getText())) {
//            if (validateAccount(account) && validateVerificationCode(verificationcode)) {
//                getPresenter().toLogin(this, account, password);
//
//            }
//        } else {
//            //是验证码登录，所以验证用户名和密码
//            if (validateAccount(account) && validatePassword(password)) {
//                getPresenter().toLogin(this, account, password);
//
//            }
//        }

        if (validateAccount(account) && validatePassword(password)) {
            getPresenter().toLogin(this, account, password);

        }

    }

    /**
     * 显示错误提示，并获取焦点
     *
     * @param textInputLayout
     * @param error
     */
    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        Objects.requireNonNull(textInputLayout.getEditText()).setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    /**
     * 验证用户名
     *
     * @param account
     * @return
     */
    private boolean validateAccount(String account) {
        if (TextUtils.isEmpty(account)) {
            showError(tilPhone, getResources().getString(R.string.phone_not_null));
            return false;
        }
        if (CommenUtil.isMobilePhone(account)) {
            showError(tilPhone, getResources().getString(R.string.phone_not_right));
            return false;
        }
        return true;

    }

    /**
     * 验证密码
     *
     * @param password
     * @return
     */
    private boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            showError(tilPassword, getResources().getString(R.string.password_not_null));
            return false;
        }

        //<6  >18
        if (password.length() < ConstantMap.NUM_SIX || password.length() > ConstantMap.NUM_EIGHTTY) {
            showError(tilPassword, getResources().getString(R.string.password_lengh));
            return false;
        }

        return true;
    }

    /**
     * 验证验证码
     *
     * @param password
     * @return
     */
    private boolean validateVerificationCode(String password) {
        if (TextUtils.isEmpty(password)) {
            showError(tilVerificationCode, getResources().getString(R.string.verificationcode_not_null));
            return false;
        }

        //<1  >6
        if (password.length() < ConstantMap.NUM_ONE || password.length() > ConstantMap.NUM_SIX) {
            showError(tilVerificationCode, getResources().getString(R.string.verificationcode_write_right));
            return false;
        }

        return true;
    }

    /**
     * 发送验证码
     */
    private void sendVode() {
        //总时间
        int countTime = ConstantMap.NUM_SIXTY;
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();

            }

            @Override
            public void onComplete() {
                tvSendcode.setClickable(true);
                tvSendcode.setText(getResources().getString(R.string.get_verificationcode));
            }

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Long aLong) {//每隔一秒执行
                tvSendcode.setText(getResources().getString(R.string.get_again) + "(" + aLong + ")");
            }
        };
        //0延迟  每隔1秒触发
        Observable.interval(ConstantMap.NUM_ZERO, ConstantMap.NUM_ONE, TimeUnit.SECONDS)
                .compose(ApiScheduler.getObservableScheduler())
                //设置循环次数
                .take(countTime + ConstantMap.NUM_ONE)
                //从60-1
                .map(aLong -> countTime - aLong)
                //执行过程中按键为不可点击状态
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        tvSendcode.setClickable(false);
                    }
                })
                .subscribe(observer);

    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void onEvent(Event event) {
        super.onEvent(event);
        if (event.getCode() == Event.EVENT_REGRISTER_SUCCESS) {
            String data = (String) event.getData();
            etPhone.setText(data);
        }

    }

    @Override
    public void onLoadSucess(Object obj) {
        RegisterModel registerModel = (RegisterModel) obj;
        if (registerModel != null) {
            SharePreferenceUtil.setBoolean(ConstantMap.IS_LOGIN, true);
            SharePreferenceUtil.setString(ConstantMap.USER_PHONE, registerModel.getUsername());
            SharePreferenceUtil.setString(ConstantMap.LOGINUSERNAME, ConstantMap.LOGINUSERNAME+registerModel.getUsername());
            SharePreferenceUtil.setString(ConstantMap.LOGINUSERPASSWORD, ConstantMap.LOGINUSERPASSWORD+registerModel.getPassword());
            RxBus.getDefault().post(new Event<>(Event.EVENT_LOGIN_SUCCESS, registerModel.getUsername()));
            finish();
        }
    }
}

package com.gank.chen.ui.activity;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
import com.gank.chen.mvp.presenter.RegisterPresenter;
import com.gank.chen.mvp.view.OnLoadSuccessViewImp;
import com.gank.chen.util.CommenUtil;
import com.gank.chen.util.RxBus;
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
 * 注册页面
 */
@Route(path = RouterUrlManager.REGISTER)
@CreatePresenter(RegisterPresenter.class)
public class RegisterActivity extends BaseNoNetActivity<OnLoadSuccessViewImp<RegisterModel>, RegisterPresenter> implements OnLoadSuccessViewImp {


    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.til_phone)
    TextInputLayout tilPhone;
    @BindView(R.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R.id.tv_sendcode)
    TextView tvSendcode;
    @BindView(R.id.til_verification_code)
    TextInputLayout tilVerificationCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.et_phoneagain)
    EditText etPhoneagain;
    @BindView(R.id.til_passwordagain)
    TextInputLayout tilPasswordagain;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.et_yaoqingma)
    EditText etYaoqingma;
    @BindView(R.id.til_yaoqingma)
    TextInputLayout tilYaoqingma;
    private Disposable disposable;


    @Override
    public int getViewLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        initToolBar(getResources().getString(R.string.regrister), false);
    }

    /**
     * 检查手机号和密码
     */
    private void checkLoginData() {
        String phone = Objects.requireNonNull(tilPhone.getEditText()).getText().toString();
        String password = Objects.requireNonNull(tilPassword.getEditText()).getText().toString();
        String verificationcode = Objects.requireNonNull(tilVerificationCode.getEditText()).getText().toString();
        String passwordagain = Objects.requireNonNull(tilPasswordagain.getEditText()).getText().toString();

        tilPhone.setErrorEnabled(false);
        tilPassword.setErrorEnabled(false);
        tilVerificationCode.setErrorEnabled(false);
        tilPasswordagain.setErrorEnabled(false);

        //验证用户名和密码
        if (validateAccount(phone)
                && validatePassword(password)
//                && validateVerificationCode(verificationcode)
                && validatePasswordAgain(passwordagain, password)) {
            getPresenter().RegisterNow(this, phone, password);

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
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    /**
     * 验证手机号
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

        if (password.length() < ConstantMap.NUM_ONE || password.length() > ConstantMap.NUM_SIX) {
            showError(tilVerificationCode, getResources().getString(R.string.verificationcode_write_right));
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

        if (password.length() < ConstantMap.NUM_SIX || password.length() > ConstantMap.NUM_EIGHTTY) {
            showError(tilPassword, getResources().getString(R.string.password_lengh));
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
    private boolean validatePasswordAgain(String password, String passwordold) {
        if (TextUtils.isEmpty(password)) {
            showError(tilPasswordagain, getResources().getString(R.string.password_not_null));
            return false;
        }

        if (password.length() < ConstantMap.NUM_SIX || password.length() > ConstantMap.NUM_EIGHTTY) {
            showError(tilPasswordagain, getResources().getString(R.string.password_lengh));
            return false;
        }
        if (!password.equals(passwordold)) {
            showError(tilPasswordagain, getResources().getString(R.string.password_are_not_same));
            return false;
        }

        return true;
    }

    @OnClick({R.id.tv_sendcode, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sendcode:
                sendVode();
                break;
            case R.id.tv_register:
                checkLoginData();
                break;
            default:
                break;
        }
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
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }


    @Override
    public void onLoadSucess(Object obj) {
        RegisterModel registerModel = (RegisterModel) obj;
        if (registerModel != null) {
            ToastUtils.showToast(this, getResources().getString(R.string.regrister_success));
            RxBus.getDefault().post(new Event<>(Event.EVENT_REGRISTER_SUCCESS, registerModel.getUsername()));
        }

    }
}

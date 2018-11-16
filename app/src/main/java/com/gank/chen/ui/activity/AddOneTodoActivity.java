package com.gank.chen.ui.activity;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gank.chen.R;
import com.gank.chen.base.BaseNoNetActivity;
import com.gank.chen.base.CreatePresenter;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.Event;
import com.gank.chen.mvp.presenter.AddTodoPresenter;
import com.gank.chen.mvp.view.ImpAddTodo;
import com.gank.chen.util.CommenUtil;
import com.gank.chen.util.RxBus;
import com.gank.chen.util.SnackbarUtils;
import com.gank.chen.util.keyboard.KeyboardVisibilityEvent;
import com.gank.chen.util.keyboard.KeyboardVisibilityEventListener;
import com.gank.chen.util.keyboard.util.UIUtil;
import com.gank.chen.widget.CustomPopupWindow;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chenbo
 */
@CreatePresenter(AddTodoPresenter.class)
@Route(path = RouterUrlManager.ADD_TODO_ACTIVITY)
public class AddOneTodoActivity extends BaseNoNetActivity<ImpAddTodo, AddTodoPresenter> implements ImpAddTodo {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    private boolean keyBoradIsOpen;
    private CustomPopupWindow popupWindow;
    private int type;
    private String title;
    private int id;

    @Override
    public int getViewLayoutId() {
        return R.layout.activity_todo;
    }

    @Override
    public void initData() {

        KeyboardVisibilityEvent.setEventListener(
                activity,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        keyBoradIsOpen = isOpen;

                    }
                });
    }

    @Override
    public void initView() {
        initToolBar("添加ToDo", false);
        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", 0);
            title = getIntent().getStringExtra("title");
            String content = getIntent().getStringExtra("content");
            String time = getIntent().getStringExtra("time");
            id = getIntent().getIntExtra("id", 0);
            if (!TextUtils.isEmpty(title)) {
                etTitle.setText(title);
            }

            if (!TextUtils.isEmpty(content)) {
                etContent.setText(content);
            }

            if (!TextUtils.isEmpty(time)) {
                tvTime.setText(time);
            } else {
                String date = CommenUtil.formatDate(System.currentTimeMillis());
                tvTime.setText(date);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_check_success, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_check:
                if (keyBoradIsOpen) {
                    UIUtil.hideKeyboard(activity);
                }
                if (checkData()) {
                    Map<String, Object> map = new HashMap<>(4);
                    map.put("title", etTitle.getText().toString().trim());
                    map.put("content", etContent.getText().toString().trim());
                    map.put("date", tvTime.getText());
                    map.put("type", type);
                    if (TextUtils.isEmpty(title)) {
                        getPresenter().toAddOneToDo(activity, map);
                    } else {
                        getPresenter().toUpdateTheToDo(activity, id, map);
                    }

                }
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 检查数据
     *
     * @return
     */
    private boolean checkData() {
        if (TextUtils.isEmpty(etTitle.getText().toString().trim())) {
            SnackbarUtils.with(etContent).setMessage("请输入标题").show();
            return false;
        }
        if (TextUtils.isEmpty(etContent.getText().toString().trim())) {
            SnackbarUtils.with(etContent).setMessage("请输入内容").show();
            return false;
        }
        return true;
    }

    @Override
    public void onUpdateSuccess() {
        activity.finish();
        RxBus.getDefault().post(new Event<>(Event.TODO_TYPE, type));
    }

    @Override
    public void onUpdateFail(String msg) {
        SnackbarUtils.with(etContent).setMessage(msg).show();
    }

    @Override
    public void onAddSuccess(String msg) {
        activity.finish();
        RxBus.getDefault().post(new Event<>(Event.TODO_TYPE, type));
    }

    @Override
    public void onAddFail(String msg) {
        SnackbarUtils.with(etContent).setMessage(msg).show();
    }


    @OnClick(R.id.tv_time)
    public void onViewClicked() {
        if (keyBoradIsOpen) {
            UIUtil.hideKeyboard(activity);
        }
        popupWindow = CustomPopupWindow.builder()
                .isOutsideTouch(true)
                .isWrap(true)
                .contentView(CustomPopupWindow.inflateView(activity, R.layout.layout_pop_canlendar))
                .customListener(contentView -> {
                    CalendarView calendarView = contentView.findViewById(R.id.calendarview);
                    calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                        String content = year + "-" + (month + 1) + "-" + dayOfMonth;
                        tvTime.setText(content);
                        popupWindow.dismiss();
                    });

                }).build();

        popupWindow.show();
    }
}

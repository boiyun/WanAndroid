package com.gank.chen.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gank.chen.R;
import com.gank.chen.adapter.ProjectsListAdapter;
import com.gank.chen.adapter.TodoListAdapter;
import com.gank.chen.base.BaseFragment;
import com.gank.chen.base.CreatePresenter;
import com.gank.chen.common.ConstantMap;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.ArticleListModel;
import com.gank.chen.mvp.model.ArticleModel;
import com.gank.chen.mvp.model.Event;
import com.gank.chen.mvp.model.TodoItemData;
import com.gank.chen.mvp.model.TodoListData;
import com.gank.chen.mvp.presenter.TodoListPresenter;
import com.gank.chen.mvp.view.ImpTodoList;
import com.gank.chen.ui.activity.ToDoListActivity;
import com.gank.chen.util.RouterUtil;
import com.gank.chen.util.SnackbarUtils;
import com.gank.chen.widget.CustomPopupWindow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Todo列表页面
 *
 * @author chenbo
 */
@CreatePresenter(TodoListPresenter.class)
@Route(path = RouterUrlManager.TODOLIST_FRAGMENT)
public class TodoListFragment extends BaseFragment<ImpTodoList, TodoListPresenter>
        implements ImpTodoList, View.OnClickListener {

    @BindView(R.id.recyclerview_home_pic)
    RecyclerView recyclerviewHomePic;

    private TodoListAdapter adapter;
    private int position;

    List<MultiItemEntity> needModelList = new ArrayList<>();
    private TodoItemData itemData;
    private int dataType;
    private CustomPopupWindow popupWindow;

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void onEvent(Event event) {
        super.onEvent(event);
        if (event.getCode() == Event.TODO_TYPE) {
            dataType = (int) event.getData();
            handleRequestData(dataType);
        }
    }

    @Override
    public int getViewLayoutId() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    public void initData() {
        assert getArguments() != null;
        String doneOrNot = "doneOrNot";
        position = getArguments().getInt(doneOrNot);
        handleRequestData(ConstantMap.TODO_THIS_ONE);
    }

    public void handleRequestData(int type) {
        if (stateView != null && position == 0) {
            getPresenter().getTodoList(getActivity(), stateView, type);
        } else {
            getPresenter().getNotTodoList(getActivity(), stateView, type);
        }
    }

    @Override
    public void initView() {
        initPageNum(1);
        recyclerviewHomePic.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TodoListAdapter(needModelList);
        recyclerviewHomePic.setAdapter(adapter);
        adapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                itemData = (TodoItemData) adapter.getData().get(position);

                showPopUpWindowMenu();
                return false;
            }
        });
    }


    @Override
    public void onLoadDataSucess(List<TodoListData> todoList) {
        super.onLoadSucess(todoList);
        needModelList.clear();
        for (TodoListData listData : todoList) {
            for (TodoItemData todoItemData : listData.getTodoList()) {
                listData.addSubItem(todoItemData);
            }
            needModelList.add(listData);
        }

        adapter.setNewData(needModelList);
        adapter.expandAll();
    }

    @Override
    public void onDeleteSuccess(String msg) {
        SnackbarUtils.with(recyclerviewHomePic).setMessage(msg).show();
//        adapter.notifyDataSetChanged();

        handleRequestData(dataType);
    }

    @Override
    public void onDeleteFail(String msg) {
        SnackbarUtils.with(recyclerviewHomePic).setMessage(msg).show();
    }

    @Override
    public void onChangeStatusSuccess(String msg) {
        SnackbarUtils.with(recyclerviewHomePic).setMessage(msg).show();
        handleRequestData(dataType);
    }

    @Override
    public void onChangeStatusFail(String msg) {
        SnackbarUtils.with(recyclerviewHomePic).setMessage(msg).show();
    }

//    @Override
//    public void onChangeStatusFromNotDo2ToDoSuccess(String msg) {
//
//    }
//
//    @Override
//    public void onChangeStatusFromNotDo2ToDoFail(String msg) {
//
//    }

    @Override
    protected boolean useLoadMore() {
        return false;
    }

    private void showPopUpWindowMenu() {
        /**
         * 未完成：标记为已完成、编辑、删除；
         * 已完成：标记为未完成、删除
         */

        popupWindow = CustomPopupWindow.builder().contentView(CustomPopupWindow.inflateView(getActivity()
                , R.layout.layout_pop_todo_menu))
                .isWrap(true)
                .isOutsideTouch(true)
                .customListener(new CustomPopupWindow.CustomPopupWindowListener() {
                    @Override
                    public void initPopupView(View contentView) {
                        TextView tvTodoDone = contentView.findViewById(R.id.tv_todo_done);
                        TextView tvTodoDelete = contentView.findViewById(R.id.tv_todo_delete);
                        TextView tvTodoUpdate = contentView.findViewById(R.id.tv_todo_update);
                        tvTodoDone.setOnClickListener(TodoListFragment.this);
                        tvTodoDelete.setOnClickListener(TodoListFragment.this);
                        tvTodoUpdate.setOnClickListener(TodoListFragment.this);

                        tvTodoUpdate.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
                        tvTodoDone.setText(position == 0 ? "标记已完成" : "标记未完成");
                    }
                }).build();
        popupWindow.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_todo_done:
                if (position == 0) {
                    getPresenter().toChangeStatus(getActivity(), itemData.getId(), 1, position);
                } else {
                    getPresenter().toChangeStatus(getActivity(), itemData.getId(), 0, position);
                }
                popupWindow.dismiss();
                break;
            case R.id.tv_todo_delete:
                getPresenter().deleteTheToDo(getActivity(), itemData.getId());
                popupWindow.dismiss();
                break;
            case R.id.tv_todo_update:
                popupWindow.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("content", itemData.getContent());
                bundle.putString("title", itemData.getTitle());
                bundle.putString("time", itemData.getDateStr());
                bundle.putInt("id", itemData.getId());

                RouterUtil.goToActivity(RouterUrlManager.ADD_TODO_ACTIVITY, bundle);

                break;
            default:
                break;
        }
    }
}

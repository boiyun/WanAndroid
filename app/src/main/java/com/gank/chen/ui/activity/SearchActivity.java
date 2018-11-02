package com.gank.chen.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gank.chen.R;
import com.gank.chen.adapter.CommonWebsiteAdapter;
import com.gank.chen.base.BaseActivity;
import com.gank.chen.base.CreatePresenter;
import com.gank.chen.common.RouterUrlManager;
import com.gank.chen.mvp.model.ArticleListModel;
import com.gank.chen.mvp.model.CommonWebsiteModel;
import com.gank.chen.mvp.presenter.SearchPresenter;
import com.gank.chen.mvp.view.ImpSearchActivity;
import com.gank.chen.util.RouterUtil;
import com.gank.chen.util.SnackbarUtils;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 搜索页面
 *
 * @author chenbo
 */
@CreatePresenter(SearchPresenter.class)
@Route(path = RouterUrlManager.SEARCH_ACTIVITY)
public class SearchActivity extends BaseActivity<ImpSearchActivity, SearchPresenter>
        implements ImpSearchActivity {

    @BindView(R.id.rv_hot_search)
    RecyclerView rvHotSearch;
    @BindView(R.id.et_quary)
    EditText etQuary;

    private List<CommonWebsiteModel> modelList = new ArrayList<>();
    private CommonWebsiteAdapter adapter;

    @Override
    protected boolean useLoadMore() {
        return false;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getPresenter().getHotSearchData(activity, stateView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        initToolBar("");
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(activity);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        rvHotSearch.setLayoutManager(layoutManager);
        adapter = new CommonWebsiteAdapter(modelList);
        rvHotSearch.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommonWebsiteModel model = (CommonWebsiteModel) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putString("key", model.getName());
                RouterUtil.goToActivity(RouterUrlManager.QUARY_RESULT,bundle);
            }
        });
    }

    @Override
    public void onLoadSuccess(List<CommonWebsiteModel> searchModelList) {
        super.onLoadSucess(searchModelList);
        modelList = searchModelList;
        adapter.setNewData(modelList);
    }

    @Override
    public void onLoadHistorySuccess(Boolean boo) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mine_quary:
                if (etQuary.getText().toString().trim().length() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("key", etQuary.getText().toString().trim());
                    RouterUtil.goToActivity(RouterUrlManager.QUARY_RESULT,bundle);
                } else {
                    SnackbarUtils.with(etQuary).setMessage("请输入搜索词哦").showWarning();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

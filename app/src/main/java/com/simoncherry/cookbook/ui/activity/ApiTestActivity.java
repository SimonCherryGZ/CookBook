package com.simoncherry.cookbook.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.di.component.DaggerApiTestComponent;
import com.simoncherry.cookbook.di.module.ApiTestModule;
import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;
import com.simoncherry.cookbook.mvp.biz.ApiTestBiz;
import com.simoncherry.cookbook.mvp.contract.ApiTestContract;
import com.simoncherry.cookbook.mvp.presenter.ApiTestPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class ApiTestActivity extends BaseActivity<ApiTestPresenter> implements ApiTestContract.View {

    private final static String TAG = ApiTestActivity.class.getSimpleName();

    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.btn_query_category)
    Button btnQueryCategory;
    @BindView(R.id.btn_query_recipe)
    Button btnQueryRecipe;
    @BindView(R.id.btn_query_detail)
    Button btnQueryDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initComponent() {
        //apiTestPresenter = new ApiTestPresenterImpl(getApplicationContext(), this);
        DaggerApiTestComponent.builder()
                .apiTestModule(new ApiTestModule(new ApiTestBiz()))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_api_test;
    }

    @Override
    public void onQueryCategorySuccess(MobCategoryResult value) {
        tvResult.setText(value.toString());
    }

    @Override
    public void onQueryRecipe(MobRecipeResult value) {
        tvResult.setText(value.toString());
    }

    @Override
    public void onQueryDetail(MobRecipe value) {
        tvResult.setText(value.toString());
    }

    @Override
    public void onQueryEmpty() {
        Toast.makeText(this, "查询结果为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onQueryError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowProgressBar() {
        Logger.t(TAG).i("onShowProgressBar");
        showProgressBar();
    }

    @Override
    public void onHideProgressBar() {
        Logger.t(TAG).i("onHideProgressBar");
        hideProgressBar();
    }

    @OnClick({R.id.btn_query_category, R.id.btn_query_recipe, R.id.btn_query_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query_category:
                mPresenter.queryCategory();
                break;
            case R.id.btn_query_recipe:
                mPresenter.queryRecipe("0010001010", 1, 20);
                break;
            case R.id.btn_query_detail:
                mPresenter.queryDetail("00100010100000031636");
                break;
        }
    }
}

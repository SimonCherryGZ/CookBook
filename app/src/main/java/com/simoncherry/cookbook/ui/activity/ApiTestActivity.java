package com.simoncherry.cookbook.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.biz.ApiTestBiz;
import com.simoncherry.cookbook.contract.ApiTestContract;
import com.simoncherry.cookbook.di.component.DaggerApiTestComponent;
import com.simoncherry.cookbook.di.module.ApiTestModule;
import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;
import com.simoncherry.cookbook.presenter.ApiTestPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ApiTestActivity extends BaseActivity implements ApiTestContract.View {

    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.btn_query_category)
    Button btnQueryCategory;
    @BindView(R.id.btn_query_recipe)
    Button btnQueryRecipe;
    @BindView(R.id.btn_query_detail)
    Button btnQueryDetail;

    @Inject
    ApiTestPresenter apiTestPresenter;

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
                .apiTestModule(new ApiTestModule(new ApiTestBiz(), this))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_api_test;
    }

    @Override
    public void setPresenter(ApiTestContract.Presenter presenter) {
        apiTestPresenter = (ApiTestPresenter) presenter;
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
    public void onQueryFailed() {
        Toast.makeText(this, "value is null", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onQueryError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.btn_query_category, R.id.btn_query_recipe, R.id.btn_query_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query_category:
                apiTestPresenter.queryCategory();
                break;
            case R.id.btn_query_recipe:
                apiTestPresenter.queryRecipe("0010001010", 1, 20);
                break;
            case R.id.btn_query_detail:
                apiTestPresenter.queryDetail("00100010100000031636");
                break;
        }
    }
}

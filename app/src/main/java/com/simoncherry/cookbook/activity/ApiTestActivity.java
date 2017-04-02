package com.simoncherry.cookbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.component.DaggerApiTestComponent;
import com.simoncherry.cookbook.model.MobCategoryResult;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeResult;
import com.simoncherry.cookbook.module.ApiTestModule;
import com.simoncherry.cookbook.presenter.impl.ApiTestPresenterImpl;
import com.simoncherry.cookbook.view.ApiTestView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ApiTestActivity extends AppCompatActivity implements ApiTestView {

    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.btn_query_category)
    Button btnQueryCategory;
    @BindView(R.id.btn_query_recipe)
    Button btnQueryRecipe;
    @BindView(R.id.btn_query_detail)
    Button btnQueryDetail;

    private Unbinder unbinder;
    @Inject
    ApiTestPresenterImpl apiTestPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_test);
        unbinder = ButterKnife.bind(this);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void init() {
        //apiTestPresenter = new ApiTestPresenterImpl(getApplicationContext(), this);
        DaggerApiTestComponent.builder()
                .apiTestModule(new ApiTestModule(getApplicationContext(), this))
                .build()
                .inject(this);
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

    private void startCategoryActivity() {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.btn_query_category, R.id.btn_query_recipe, R.id.btn_query_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query_category:
                //apiTestPresenter.queryCategory();
                startCategoryActivity();
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

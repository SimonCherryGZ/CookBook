package com.simoncherry.cookbook.ui.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.custom.MySuggestionProvider;
import com.simoncherry.cookbook.model.Constant;
import com.simoncherry.cookbook.realm.RealmHelper;
import com.simoncherry.cookbook.util.DataCleanManager;
import com.simoncherry.cookbook.util.DialogUtils;
import com.simoncherry.cookbook.util.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment {

    @BindView(R.id.layout_set_mode)
    RelativeLayout layoutSetMode;
    @BindView(R.id.switch_mode)
    Switch switchMode;
    @BindView(R.id.layout_history_count)
    LinearLayout layoutHistoryCount;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.radio_5)
    RadioButton radio5;
    @BindView(R.id.radio_10)
    RadioButton radio10;
    @BindView(R.id.radio_20)
    RadioButton radio20;
    @BindView(R.id.layout_clear_search)
    LinearLayout layoutClearSearch;
    @BindView(R.id.layout_clear_cache)
    LinearLayout layoutClearCache;
    @BindView(R.id.tv_cache)
    TextView tvCache;

    private final static String TAG = SettingFragment.class.getSimpleName();

    private SPUtils spUtils;


    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initComponent() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Logger.t(TAG).i("onHiddenChanged :" + hidden);
        showCacheSize();
    }

    private void init() {
        initView();
        initData();
    }

    private void initView() {
        switchMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String txt;
                if (isChecked) {
                    txt = "已开启省流量模式";
                } else {
                    txt = "已关闭省流量模式";
                }
                Toast.makeText(mContext, txt, Toast.LENGTH_SHORT).show();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio_5:
                        onChangeHistoryCount(5);
                        break;
                    case R.id.radio_10:
                        onChangeHistoryCount(10);
                        break;
                    case R.id.radio_20:
                        onChangeHistoryCount(20);
                        break;
                }
            }
        });
    }

    private void initData() {
        spUtils = new SPUtils(mContext, Constant.SP_NAME);
        int count = spUtils.getInt(Constant.SP_HISTORY_COUNT, Constant.DEFAULT_HISTORY_COUNT);
        setRadioButtonByCount(count);
    }

    private void setRadioButtonByCount(int count) {
        switch (count) {
            case 5:
                radio5.setChecked(true);
                break;
            case 10:
                radio10.setChecked(true);
                break;
            case 20:
                radio20.setChecked(true);
                break;
            default:
                radio10.setChecked(true);
        }
    }

    private void onChangeHistoryCount(int count) {
        spUtils.put(Constant.SP_HISTORY_COUNT, count);
        RealmHelper.deleteMultiHistoryAsync(count);
//        String txt = "已修改为 " + count + " 条";
//        Toast.makeText(mContext, txt, Toast.LENGTH_SHORT).show();
    }

    private void showCacheSize() {
        try {
            String cacheSize = DataCleanManager.getCacheSize(mActivity.getCacheDir());
            tvCache.setText(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
            if (tvCache != null) {
                String txt = "0.0Byte";
                tvCache.setText(txt);
            }
        }
    }

    private void onChangeNetMode() {
        DialogUtils.showDialog(mContext, "提示", "是否要更改设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(mContext, "已更改设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClearSearchHistory() {
        DialogUtils.showDialog(mContext, "提示", "是否要清除搜索记录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SearchRecentSuggestions suggestions = new SearchRecentSuggestions(mContext,
                        MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
                suggestions.clearHistory();
                Toast.makeText(mContext, "已清除搜索记录", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClearCache() {
        DialogUtils.showDialog(mContext, "提示", "是否要清除缓存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DataCleanManager.cleanInternalCache(mContext);
                showCacheSize();
                Toast.makeText(mContext, "已清除缓存", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.layout_set_mode, R.id.layout_clear_search, R.id.layout_clear_cache})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_set_mode:
                onChangeNetMode();
                break;
            case R.id.layout_clear_search:
                onClearSearchHistory();
                break;
            case R.id.layout_clear_cache:
                onClearCache();
                break;
        }
    }
}

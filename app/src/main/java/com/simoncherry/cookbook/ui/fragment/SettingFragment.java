package com.simoncherry.cookbook.ui.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.util.DataCleanManager;
import com.simoncherry.cookbook.util.DialogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment {

    @BindView(R.id.layout_set_mode)
    LinearLayout layoutSetMode;
    @BindView(R.id.layout_history_count)
    LinearLayout layoutHistoryCount;
    @BindView(R.id.layout_clear_search)
    LinearLayout layoutClearSearch;
    @BindView(R.id.layout_clear_cache)
    LinearLayout layoutClearCache;
    @BindView(R.id.tv_cache)
    TextView tvCache;

    private final static String TAG = SettingFragment.class.getSimpleName();


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

    private void onChangeHistoryCount() {
        final EditText editText = new EditText(mContext);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint("当前数量：10条");

        DialogUtils.showDialog(mContext, "提示", "最近浏览记录的最大数量", editText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String text = editText.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    int count = 0;
                    try {
                        count = Integer.parseInt(text);
                        String msg = "已设置最大数量: " + text;
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                String msg = "输入异常";
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClearSearchHistory() {
        DialogUtils.showDialog(mContext, "提示", "是否要清除搜索记录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
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

    @OnClick({R.id.layout_set_mode, R.id.layout_history_count, R.id.layout_clear_search, R.id.layout_clear_cache})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_set_mode:
                onChangeNetMode();
                break;
            case R.id.layout_history_count:
                onChangeHistoryCount();
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

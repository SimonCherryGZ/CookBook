package com.simoncherry.cookbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.model.MobCategory;

import java.util.List;

/**
 * Created by Simon on 2017/3/29.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{

    private final static String TXT_DEFAULT = "Null";

    private Context mContext;
    private List<MobCategory> mData;

    public CategoryAdapter(Context mContext, List<MobCategory> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutRoot;
        TextView tvName;
        TextView tvCtgId;
        TextView tvParentId;

        public MyViewHolder(View view) {
            super(view);
            layoutRoot = (LinearLayout) view.findViewById(R.id.layout_root);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvCtgId = (TextView) view.findViewById(R.id.tv_ctg_id);
            tvParentId = (TextView) view.findViewById(R.id.tv_parent_id);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext.getApplicationContext())
                .inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        MobCategory mobCategory = mData.get(position);
        if (mobCategory != null) {
            String name = mobCategory.getName();
            String ctgId = mobCategory.getCtgId();
            String parentId = mobCategory.getParentId();
            holder.tvName.setText(name != null ? name : TXT_DEFAULT);
            holder.tvCtgId.setText(ctgId != null ? ctgId : TXT_DEFAULT);
            holder.tvParentId.setText(parentId != null ? parentId : TXT_DEFAULT);
        } else {
            holder.tvName.setText(TXT_DEFAULT);
            holder.tvCtgId.setText(TXT_DEFAULT);
            holder.tvParentId.setText(TXT_DEFAULT);
        }

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }
}

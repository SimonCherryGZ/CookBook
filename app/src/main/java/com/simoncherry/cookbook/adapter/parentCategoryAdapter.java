package com.simoncherry.cookbook.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.model.MobCategory;

import java.util.List;

/**
 * Created by Simon on 2017/3/29.
 */

public class parentCategoryAdapter extends RecyclerView.Adapter<parentCategoryAdapter.MyViewHolder>{

    private final static String TXT_DEFAULT = "Null";

    private Context mContext;
    private List<MobCategory> mData;

    public parentCategoryAdapter(Context mContext, List<MobCategory> mData) {
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
        RelativeLayout layoutRoot;
        TextView tvName;

        public MyViewHolder(View view) {
            super(view);
            layoutRoot = (RelativeLayout) view.findViewById(R.id.layout_root);
            tvName = (TextView) view.findViewById(R.id.tv_name);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext.getApplicationContext())
                .inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final MobCategory mobCategory = mData.get(position);
        if (mobCategory != null) {
            String name = mobCategory.getName();
            String ctgId = mobCategory.getCtgId();
            String parentId = mobCategory.getParentId();
            holder.tvName.setText(name != null ? name.substring(1, 3) : TXT_DEFAULT);

            if (mobCategory.isSelected()) {
                holder.layoutRoot.setBackgroundColor(Color.WHITE);
            } else {
                holder.layoutRoot.setBackgroundColor(mContext.getResources().getColor(R.color.gray_inactive));
            }

            holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (MobCategory item : mData) {
                        if(item.getCtgId().equals(mobCategory.getCtgId())){
                            item.setSelected(true);
                        }
                        else{
                            item.setSelected(false);
                        }
                    }
                    notifyDataSetChanged();
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });

        } else {
            holder.tvName.setText(TXT_DEFAULT);
            holder.layoutRoot.setBackgroundColor(mContext.getResources().getColor(R.color.gray_inactive));
        }
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }
}

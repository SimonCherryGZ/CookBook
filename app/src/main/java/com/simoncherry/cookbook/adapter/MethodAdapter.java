package com.simoncherry.cookbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.model.MobRecipeMethod;

import java.util.List;

/**
 * Created by Simon on 2017/3/31.
 */

public class MethodAdapter extends RecyclerView.Adapter<MethodAdapter.MyViewHolder>{

    private Context mContext;
    private List<MobRecipeMethod> mData;

    public MethodAdapter(Context mContext, List<MobRecipeMethod> mData) {
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
        TextView tvMethod;
        ImageView ivImg;

        public MyViewHolder(View view) {
            super(view);
            tvMethod = (TextView) view.findViewById(R.id.tv_method);
            ivImg = (ImageView) view.findViewById(R.id.iv_img);
        }
    }

    @Override
    public MethodAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext.getApplicationContext())
                .inflate(R.layout.item_method, parent, false));
    }

    @Override
    public void onBindViewHolder(MethodAdapter.MyViewHolder holder, int position) {
        MobRecipeMethod method = mData.get(position);
        if (method != null) {
            String step = method.getStep();
            if (step != null) {
                holder.tvMethod.setText(step);
            } else {
                holder.tvMethod.setText("");
            }

            String url = method.getImg();
            if (url != null && !TextUtils.isEmpty(url)) {
                holder.ivImg.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(url)
                        .centerCrop()
                        .placeholder(R.drawable.default_img)
                        .error(R.drawable.default_img)
                        .into(holder.ivImg);
            } else {
                holder.ivImg.setVisibility(View.GONE);
            }

        } else {
            holder.tvMethod.setText("");
            holder.ivImg.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }
}

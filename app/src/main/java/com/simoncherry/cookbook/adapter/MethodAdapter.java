package com.simoncherry.cookbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.simoncherry.cookbook.databinding.ItemMethodBinding;
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
        private ItemMethodBinding binding;

        public MyViewHolder(ItemMethodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MobRecipeMethod item) {
            binding.setMethod(item);
            binding.executePendingBindings();
        }
    }

    @Override
    public MethodAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMethodBinding binding = ItemMethodBinding.inflate(LayoutInflater.from(mContext.getApplicationContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MethodAdapter.MyViewHolder holder, int position) {
        MobRecipeMethod method = mData.get(position);
        holder.bind(method);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }
}

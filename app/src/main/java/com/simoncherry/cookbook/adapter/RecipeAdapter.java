package com.simoncherry.cookbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simoncherry.cookbook.databinding.ItemRecipeBinding;
import com.simoncherry.cookbook.model.MobRecipe;

import java.util.List;

/**
 * Created by Simon on 2017/3/29.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private Context mContext;
    private List<MobRecipe> mData;

    public RecipeAdapter(Context mContext, List<MobRecipe> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemRecipeBinding binding;

        private MyViewHolder(ItemRecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MobRecipe item) {
            binding.setRecipe(item);
            binding.executePendingBindings();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRecipeBinding binding = ItemRecipeBinding.inflate(LayoutInflater.from(mContext.getApplicationContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.binding.ivThumbnail, position);
                }
            }
        });
        MobRecipe recipe = mData.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }
}

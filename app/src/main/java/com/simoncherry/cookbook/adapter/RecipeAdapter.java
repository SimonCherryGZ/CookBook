package com.simoncherry.cookbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.model.MobRecipe;
import com.simoncherry.cookbook.model.MobRecipeDetail;
import com.simoncherry.cookbook.util.ImageLoaderUtils;

import java.util.List;

/**
 * Created by Simon on 2017/3/29.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    private final static String TXT_DEFAULT = "Null";

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
        RelativeLayout layoutRoot;
        ImageView ivThumbnail;
        TextView tvName;
        TextView tvSummary;
        TextView tvIngredients;

        public MyViewHolder(View view) {
            super(view);
            layoutRoot = (RelativeLayout) view.findViewById(R.id.layout_root);
            ivThumbnail = (ImageView) view.findViewById(R.id.iv_thumbnail);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvSummary = (TextView) view.findViewById(R.id.tv_summary);
            tvIngredients = (TextView) view.findViewById(R.id.tv_ingredients);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext.getApplicationContext())
                .inflate(R.layout.item_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MobRecipe recipe = mData.get(position);
        if (recipe != null) {
            String thumbnail = recipe.getThumbnail();
            if (thumbnail != null) {
                ImageLoaderUtils.getInstance()
                        .loadImage(thumbnail, R.drawable.default_img, R.drawable.default_img, holder.ivThumbnail);
            } else {
                holder.ivThumbnail.setImageResource(R.drawable.default_img);
            }

            String name = recipe.getName();
            holder.tvName.setText(name != null ? name : TXT_DEFAULT);

            MobRecipeDetail detail = recipe.getRecipeDetail();
            if (detail != null) {
                String summary = detail.getSumary();
                holder.tvSummary.setText(summary != null ? summary : TXT_DEFAULT);

                String ingredients = detail.getIngredients();
                //holder.tvIngredients.setText(ingredients != null ? ingredients : TXT_DEFAULT);
                if (ingredients != null && !TextUtils.isEmpty(ingredients)) {
                    ingredients = ingredients.replace("[", "");
                    ingredients = ingredients.replace("]", "");
                    ingredients = ingredients.replace("\"", "");
                    holder.tvIngredients.setText(ingredients);
                } else {
                    holder.tvIngredients.setText(TXT_DEFAULT);
                }
            } else {
                holder.tvSummary.setText(TXT_DEFAULT);
                holder.tvIngredients.setText(TXT_DEFAULT);
            }
        } else {
            holder.ivThumbnail.setImageResource(0);
            holder.tvName.setText(TXT_DEFAULT);
            holder.tvSummary.setText(TXT_DEFAULT);
            holder.tvIngredients.setText(TXT_DEFAULT);
        }

        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.ivThumbnail, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }
}

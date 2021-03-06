package com.simoncherry.cookbook.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simoncherry.cookbook.R;
import com.simoncherry.cookbook.model.RealmCollection;
import com.simoncherry.cookbook.util.ImageLoaderUtils;

import java.util.List;

/**
 * Created by Simon on 2017/4/5.
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.MyViewHolder>{

    private final static String TXT_DEFAULT = "不详";

    private Context mContext;
    private List<RealmCollection> mData;

    public CollectionAdapter(Context mContext, List<RealmCollection> mData) {
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
        private RelativeLayout layoutRoot;
        private ImageView ivThumbnail;
        private TextView tvName;
        private TextView tvSummary;
        private TextView tvIngredients;

        public MyViewHolder(View itemView) {
            super(itemView);
            layoutRoot = (RelativeLayout) itemView.findViewById(R.id.layout_root);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvSummary = (TextView) itemView.findViewById(R.id.tv_summary);
            tvIngredients = (TextView) itemView.findViewById(R.id.tv_ingredients);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_collection, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        RealmCollection recipe = mData.get(position);
        if (recipe == null) {
            holder.ivThumbnail.setImageResource(R.drawable.default_img);
            holder.tvName.setText(TXT_DEFAULT);
            holder.tvSummary.setText(TXT_DEFAULT);
            holder.tvIngredients.setText(TXT_DEFAULT);
        } else {
            ImageLoaderUtils.getInstance()
                    .loadImage(recipe.getThumbnail(), holder.ivThumbnail);
            holder.tvName.setText(recipe.getName());
            holder.tvSummary.setText(recipe.getSummary());
            holder.tvIngredients.setText(recipe.getIngredients());

            holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(holder.ivThumbnail, position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }
}

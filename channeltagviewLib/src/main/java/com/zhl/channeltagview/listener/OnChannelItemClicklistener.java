package com.zhl.channeltagview.listener;

import android.view.View;

import com.zhl.channeltagview.bean.ChannelItem;

import java.util.ArrayList;

/**
 * 描述：频道tag点击事件回调接口
 * Created by zhaohl on 2017-3-7.
 */

public interface OnChannelItemClicklistener {
    /**
     * 已选中频道item点击监听回调
     * @param itemView
     * @param position
     */
    public void onAddedChannelItemClick(View itemView, int position);

    /**
     * 未选中频道item点击监听回调
     * @param itemView
     * @param position
     */
    //public void onUnAddedChannelItemClick(View itemView, int position);
    public void onUnAddedChannelItemClick(View itemView, ArrayList<ChannelItem> checkedChannels);
}

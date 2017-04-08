package com.zhl.channeltagview.bean;

/**
 * 描述：频道bean
 * Created by zhaohl on 2017-3-7.
 */
public class ChannelItem {
    public int id;
    public int iconResid;
    public String title;
    public String value;

    @Override
    public String toString() {
        return "ChannelItem{" +
                "id=" + id +
                ", iconResid=" + iconResid +
                ", title='" + title + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

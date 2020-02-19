package com.naveed.sensortool.Model;

import android.graphics.drawable.Drawable;

public class mainGridViewData {

    Drawable ItemIcon ;
    String ItemTitle;


    public mainGridViewData(){}

    public mainGridViewData(Drawable itemIcon, String itemTitle) {
        ItemIcon = itemIcon;
        ItemTitle = itemTitle;
    }

    public Drawable getItemIcon() {
        return ItemIcon;
    }

    public void setItemIcon(Drawable itemIcon) {
        ItemIcon = itemIcon;
    }

    public String getItemTitle() {
        return ItemTitle;
    }

    public void setItemTitle(String itemTitle) {
        ItemTitle = itemTitle;
    }
}

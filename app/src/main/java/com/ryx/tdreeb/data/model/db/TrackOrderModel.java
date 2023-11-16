package com.ryx.tdreeb.data.model.db;

public class TrackOrderModel {
    String title;
    String subTitle;
    int img;

    public TrackOrderModel(String title, String subTitle, int img) {
        this.title = title;
        this.subTitle = subTitle;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}

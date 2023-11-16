package com.ryx.tdreeb.data.model.api.videoslider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SliderDataModel {
    @SerializedName("slider")
    @Expose
    private List<SliderModel> slider = null;

    public List<SliderModel> getSlider() {
        return slider;
    }

    public void setSlider(List<SliderModel> slider) {
        this.slider = slider;
    }

}
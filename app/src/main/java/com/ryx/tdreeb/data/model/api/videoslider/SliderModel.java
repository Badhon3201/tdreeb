package com.ryx.tdreeb.data.model.api.videoslider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SliderModel {

    @SerializedName("subjectWiseSliderId")
    @Expose
    private Integer subjectWiseSliderId;
    @SerializedName("subjectId")
    @Expose
    private Integer subjectId;
    @SerializedName("sliderName")
    @Expose
    private String sliderName;
    @SerializedName("sliderText")
    @Expose
    private Object sliderText;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getSubjectWiseSliderId() {
        return subjectWiseSliderId;
    }

    public void setSubjectWiseSliderId(Integer subjectWiseSliderId) {
        this.subjectWiseSliderId = subjectWiseSliderId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSliderName() {
        return sliderName;
    }

    public void setSliderName(String sliderName) {
        this.sliderName = sliderName;
    }

    public Object getSliderText() {
        return sliderText;
    }

    public void setSliderText(Object sliderText) {
        this.sliderText = sliderText;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
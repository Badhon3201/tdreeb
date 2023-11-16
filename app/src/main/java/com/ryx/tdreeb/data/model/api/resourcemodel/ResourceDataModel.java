package com.ryx.tdreeb.data.model.api.resourcemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResourceDataModel {

    @SerializedName("result")
    @Expose
    private List<ResourceModel> result = null;

    public List<ResourceModel> getResult() {
        return result;
    }

    public void setResult(List<ResourceModel> result) {
        this.result = result;
    }

}
package com.ryx.tdreeb.data.model.api.addchildmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddChildDataModel {
    @SerializedName("childen")
    @Expose
    private ChildenModel childen;
    @SerializedName("childs")
    @Expose
    private List<ChildenModel> childs = null;

    public ChildenModel getChilden() {
        return childen;
    }

    public void setChilden(ChildenModel childen) {
        this.childen = childen;
    }

    public List<ChildenModel> getChilds() {
        return childs;
    }

    public void setChilds(List<ChildenModel> childs) {
        this.childs = childs;
    }

}
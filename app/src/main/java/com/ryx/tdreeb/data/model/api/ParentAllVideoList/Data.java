
package com.ryx.tdreeb.data.model.api.ParentAllVideoList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("result")
    @Expose
    public List<Result> result = null;

}

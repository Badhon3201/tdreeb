
package com.ryx.tdreeb.data.model.api.ParentAllVideoList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("countryId")
    @Expose
    public Integer countryId;
    @SerializedName("iso")
    @Expose
    public String iso;
    @SerializedName("countryName")
    @Expose
    public String countryName;
    @SerializedName("nationality")
    @Expose
    public String nationality;
    @SerializedName("iso3")
    @Expose
    public String iso3;
    @SerializedName("numCode")
    @Expose
    public String numCode;

}

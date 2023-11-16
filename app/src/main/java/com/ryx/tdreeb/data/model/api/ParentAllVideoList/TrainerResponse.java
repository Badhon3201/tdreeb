
package com.ryx.tdreeb.data.model.api.ParentAllVideoList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class TrainerResponse {

    @SerializedName("trainerId")
    @Expose
    public Integer trainerId;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("userType")
    @Expose
    public String userType;
    @SerializedName("mobileVerified")
    @Expose
    public Boolean mobileVerified;
    @SerializedName("verifiedCode")
    @Expose
    public String verifiedCode;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("userStatus")
    @Expose
    public Object userStatus;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("countryId")
    @Expose
    public Integer countryId;
    @SerializedName("country")
    @Expose
    public Country country;

}

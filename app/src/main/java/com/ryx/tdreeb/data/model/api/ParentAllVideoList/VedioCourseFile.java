
package com.ryx.tdreeb.data.model.api.ParentAllVideoList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class VedioCourseFile {

    @SerializedName("vedioCourseFileId")
    @Expose
    public Integer vedioCourseFileId;
    @SerializedName("key")
    @Expose
    public String key;
    @SerializedName("liveCourseId")
    @Expose
    public Integer liveCourseId;
    @SerializedName("filePath")
    @Expose
    public String filePath;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    @SerializedName("updatedAt")
    @Expose
    public String updatedAt;
    @SerializedName("createdBy")
    @Expose
    public Integer createdBy;
    @SerializedName("updatedBy")
    @Expose
    public Integer updatedBy;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("isDelete")
    @Expose
    public Boolean isDelete;
    @SerializedName("title")
    @Expose
    public String title;

}

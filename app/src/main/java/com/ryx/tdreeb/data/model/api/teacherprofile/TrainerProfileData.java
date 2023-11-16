package com.ryx.tdreeb.data.model.api.teacherprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrainerProfileData {
    @SerializedName("trainer")
    @Expose
    private TrainerProfileModel trainer;

    public TrainerProfileModel getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerProfileModel trainer) {
        this.trainer = trainer;
    }
}

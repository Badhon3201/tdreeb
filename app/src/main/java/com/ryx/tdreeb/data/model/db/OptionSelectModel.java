package com.ryx.tdreeb.data.model.db;

public class OptionSelectModel {

    Boolean isPractses;
    Integer isCorrectAns;
    Integer isGiveAns;

    public Integer getIsCorrectAns() {
        return isCorrectAns;
    }

    public void setIsCorrectAns(Integer isCorrectAns) {
        this.isCorrectAns = isCorrectAns;
    }

    public Integer getIsGiveAns() {
        return isGiveAns;
    }

    public void setIsGiveAns(Integer isGiveAns) {
        this.isGiveAns = isGiveAns;
    }

    public OptionSelectModel() {

    }

    public OptionSelectModel(Boolean isPractses, Integer isCorrectAns, Integer isGiveAns) {
        this.isPractses = isPractses;
        this.isCorrectAns = isCorrectAns;
        this.isGiveAns = isGiveAns;
    }

    public Boolean getPractses() {
        return isPractses;
    }

    public void setPractses(Boolean practses) {
        isPractses = practses;
    }
}

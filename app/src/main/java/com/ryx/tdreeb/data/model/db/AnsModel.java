package com.ryx.tdreeb.data.model.db;

public class AnsModel {

    String totalMark;
    String correctAns;
    String wrongAns;
    String negativeMark;
    String notMark;

    public String getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(String totalMark) {
        this.totalMark = totalMark;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public String getWrongAns() {
        return wrongAns;
    }

    public void setWrongAns(String wrongAns) {
        this.wrongAns = wrongAns;
    }

    public String getNegativeMark() {
        return negativeMark;
    }

    public void setNegativeMark(String negativeMark) {
        this.negativeMark = negativeMark;
    }

    public String getNotMark() {
        return notMark;
    }

    public void setNotMark(String notMark) {
        this.notMark = notMark;
    }

    @Override
    public String toString() {
        return "{" +
                "totalMark='" + totalMark + '\'' +
                ", correctAns='" + correctAns + '\'' +
                ", wrongAns='" + wrongAns + '\'' +
                ", negativeMark='" + negativeMark + '\'' +
                ", notMark='" + notMark + '\'' +
                '}';
    }
}

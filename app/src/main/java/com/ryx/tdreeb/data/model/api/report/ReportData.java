package com.ryx.tdreeb.data.model.api.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportData {
    @SerializedName("reportCard")
    @Expose
    private List<ReportCardModel> reportCard = null;

    public List<ReportCardModel> getReportCard() {
        return reportCard;
    }

    public void setReportCard(List<ReportCardModel> reportCard) {
        this.reportCard = reportCard;
    }

}
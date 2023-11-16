package com.ryx.tdreeb.ui.fragments.reportcard;

import android.content.Context;
import android.os.Build;
import android.text.Html;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.R;
import com.ryx.tdreeb.data.model.api.report.ReportCardModel;

public class ReportCardItemViewModel {

    public final ObservableField<String> image;

    public final ObservableField<String> name;

    public final ObservableField<String> grade;

    public final ObservableField<String> address;

    public final ObservableField<String> subject;

    public final ReportCardModel mReportCardModel;

    public ReportCardItemViewModel(ReportCardModel mReportCardModel, Context context) {
        this.mReportCardModel = mReportCardModel;
        this.image = new ObservableField<>(mReportCardModel.getStudent().getImage());
        this.address = new ObservableField<>(mReportCardModel.getStudent().getLocation().getStreetAddress());
        this.grade = new ObservableField<>(mReportCardModel.getGrade());
        this.name = new ObservableField<>(mReportCardModel.getStudent().getName());
        if (mReportCardModel.getTrainerCourse() != null) {
            this.subject = new ObservableField<>(mReportCardModel.getTrainerCourse().getSubjectName());
        } else {
            this.subject = new ObservableField<>(mReportCardModel.getCourseType());
        }
    }
}
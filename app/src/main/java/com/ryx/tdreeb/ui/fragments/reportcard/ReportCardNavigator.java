package com.ryx.tdreeb.ui.fragments.reportcard;

import com.ryx.tdreeb.data.model.api.report.ReportResponse;

public interface ReportCardNavigator {

    void handleError(Throwable throwable);

    void onSuccessReport(ReportResponse mReportResponse);

}

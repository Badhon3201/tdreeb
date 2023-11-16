package com.ryx.tdreeb.interfaces;

import com.ryx.tdreeb.data.model.api.bookingmodel.SessionModel;

public interface AllRequestCallBack {
    void onClickView(SessionModel mSessionModel);

    void onClickAccept(SessionModel mSessionModel);

    void onClickCancel(SessionModel mSessionModel);
}

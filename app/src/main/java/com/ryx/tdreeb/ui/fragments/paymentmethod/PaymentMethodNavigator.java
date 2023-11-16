package com.ryx.tdreeb.ui.fragments.paymentmethod;

import com.ryx.tdreeb.data.model.api.bookingmodel.BookingResponse;
import com.ryx.tdreeb.data.model.api.livecoursesmodel.LiveCourseResponse;

public interface PaymentMethodNavigator {
    void openThankYou();

    void handleError(Throwable throwable);

    void onSuccessOnlineLiveCourse(BookingResponse mBookingResponse);
}

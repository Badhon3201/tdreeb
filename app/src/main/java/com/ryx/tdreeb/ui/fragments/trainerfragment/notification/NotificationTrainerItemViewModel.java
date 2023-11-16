package com.ryx.tdreeb.ui.fragments.trainerfragment.notification;

import androidx.databinding.ObservableField;

public class NotificationTrainerItemViewModel {

    public final ObservableField<String> image;

    public final ObservableField<String> number_plete;

    public NotificationTrainerItemViewModel(String image, String number_plete) {
        this.image = new ObservableField<>(image);
        this.number_plete = new ObservableField<>(number_plete);
    }
}
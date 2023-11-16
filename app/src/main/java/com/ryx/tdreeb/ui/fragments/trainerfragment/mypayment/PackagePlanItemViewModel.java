package com.ryx.tdreeb.ui.fragments.trainerfragment.mypayment;

import androidx.databinding.ObservableField;

public class PackagePlanItemViewModel {

    public final ObservableField<String> image;

    public final ObservableField<String> number_plete;

    public PackagePlanItemViewModel(String image, String number_plete) {
        this.image = new ObservableField<>(image);
        this.number_plete = new ObservableField<>(number_plete);
    }
}
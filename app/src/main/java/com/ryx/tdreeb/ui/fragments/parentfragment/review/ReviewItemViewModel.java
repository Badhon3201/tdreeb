package com.ryx.tdreeb.ui.fragments.parentfragment.review;

import androidx.databinding.ObservableField;

public class ReviewItemViewModel {
    public final ObservableField<String> image;

    public final ObservableField<String> number_plete;

    public ReviewItemViewModel(String image, String number_plete) {
        this.image = new ObservableField<>(image);
        this.number_plete = new ObservableField<>(number_plete);
    }
}
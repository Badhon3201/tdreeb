package com.ryx.tdreeb.ui.fragments.trainerfragment.myresources.addresource;

import androidx.databinding.ObservableField;

import com.ryx.tdreeb.interfaces.AddResourceCallBack;

import java.io.File;

public class ResourceUploadItemViewModel {

    public final ObservableField<String> name;
    public final ObservableField<String> link;
    public final File file;
    public final int pos;
    public final AddResourceCallBack mAddResourceCallBack;

    public ResourceUploadItemViewModel(File file, int pos, AddResourceCallBack mAddResourceCallBack) {
        this.file = file;
        this.pos = pos;
        this.mAddResourceCallBack = mAddResourceCallBack;
        this.name = new ObservableField<>(file.getName());
        this.link = new ObservableField<>(file.getPath());
    }

    public void onClickClose() {
        if (mAddResourceCallBack != null) {
            mAddResourceCallBack.onClickFileRemoveItem(pos, file);
        }
    }
}
package com.ryx.tdreeb.ui.fragments.trainerfragment.videocourses.addvideocourse;

import android.text.TextWatcher;

import androidx.databinding.ObservableField;

public class VideoAddItemViewModel {
    public final ObservableField<String> title;

    public final ObservableField<String> fileName;

    public final TextWatcher textWatcher;

    public final int pos;

    public final VideoFile mVideoFile;

    public VideoAddItemViewModel(String title, String fileName, TextWatcher textWatcher, int pos, VideoFile mVideoFile) {
        this.title = new ObservableField<>(title);
        this.fileName = new ObservableField<>(fileName);
        this.textWatcher = textWatcher;
        this.pos = pos;
        this.mVideoFile = mVideoFile;
    }

    public void onAddVideo() {
        if (mVideoFile != null) {
            mVideoFile.onClickVideoFile(pos);
        }
    }

    public interface VideoFile {
        void onClickVideoFile(int pos);
    }

}
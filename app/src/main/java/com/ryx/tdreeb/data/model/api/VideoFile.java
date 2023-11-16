package com.ryx.tdreeb.data.model.api;

import java.io.File;

public class VideoFile {
    String title;
    File File;

    public VideoFile() {
    }

    public VideoFile(String title, java.io.File file) {
        this.title = title;
        File = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getFile() {
        return File;
    }

    public void setFile(File file) {
        File = file;
    }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", File=" + File +
                '}';
    }
}

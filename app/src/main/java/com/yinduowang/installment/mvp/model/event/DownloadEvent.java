package com.yinduowang.installment.mvp.model.event;

public class DownloadEvent {

    private int progress;

    public DownloadEvent(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}

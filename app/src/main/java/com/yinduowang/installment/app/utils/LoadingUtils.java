package com.yinduowang.installment.app.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;

import com.tencent.bugly.crashreport.CrashReport;
import com.yinduowang.installment.R;
import com.yinduowang.installment.app.widget.LoadingDialog;

public class LoadingUtils implements Parcelable {

    private static final String TAG_LOADING_UTILS = "request_loading";
    // 延时时间
    private static final int DELAY_TIME = 100;

    private LoadingDialog dialog;

    private Runnable runnable;
    private Handler handler;
    private boolean isDismissing;
    private Activity activity;

    public static void init(Activity activity) {
        try {
            LoadingUtils loadingUtils = new LoadingUtils(activity);
            activity.getIntent().putExtra(TAG_LOADING_UTILS, loadingUtils);
        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
    }

    public static void show(Activity activity) {
        try {
            LoadingUtils loadingUtils = activity.getIntent().getParcelableExtra(TAG_LOADING_UTILS);
            if (activity != null && loadingUtils != null) {
                if (loadingUtils.isDismissing()) {
                    removeCallBacks(loadingUtils);
                    if (!loadingUtils.isShow()) {
                        loadingUtils.show();
                    }
                } else {
                    loadingUtils.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
    }

    private static void removeCallBacks(LoadingUtils loadingUtils) {
        loadingUtils.getHandler().removeCallbacks(loadingUtils.getRunnable());
    }

    public static void dismiss(Activity activity) {
        try {
            LoadingUtils loadingUtils = activity.getIntent().getParcelableExtra(TAG_LOADING_UTILS);
            loadingUtils.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
    }

    /**
     * 立即隐藏
     */
    public static void dismissNow(Activity activity) {
        try {
            LoadingUtils loadingUtils = activity.getIntent().getParcelableExtra(TAG_LOADING_UTILS);
            loadingUtils.dismissNow();
            removeCallBacks(loadingUtils);
        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
    }

    /**
     * 是否显示
     */
    public static boolean isShow(Activity activity) {
        try {
            LoadingUtils loadingUtils = activity.getIntent().getParcelableExtra(TAG_LOADING_UTILS);
            return loadingUtils.isShow();
        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);
            return false;
        }
    }

    private LoadingUtils(Activity activity) {
        this.activity = activity;
        dialog = new LoadingDialog(activity, R.style.dialog_waiting);

        this.activity.getIntent().putExtra(LoadingUtils.TAG_LOADING_UTILS, this);

        runnable = () -> {
            isDismissing = false;
            dismissNow();
        };
        handler = new Handler();
    }

    private void show() {
        if (this.activity != null && dialog != null && !dialog.isShowing()) {
            this.dialog.show();
        }
    }

    private boolean isShow() {
        if (this.activity != null && dialog != null) {
            return this.dialog.isShowing();
        }
        return false;
    }

    private void dismiss() {
        isDismissing = true;
        handler.postDelayed(runnable, DELAY_TIME);
    }

    private void dismissNow() {
        if (this.activity != null && dialog != null) {
            dialog.dismiss();
        }
    }

    protected LoadingUtils(Parcel in) {
    }

    public static final Creator<LoadingUtils> CREATOR = new Creator<LoadingUtils>() {
        @Override
        public LoadingUtils createFromParcel(Parcel in) {
            return new LoadingUtils(in);
        }

        @Override
        public LoadingUtils[] newArray(int size) {
            return new LoadingUtils[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public Handler getHandler() {
        return handler;
    }

    public boolean isDismissing() {
        return isDismissing;
    }
}

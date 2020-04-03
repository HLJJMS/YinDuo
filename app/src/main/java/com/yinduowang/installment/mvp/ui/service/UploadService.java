package com.yinduowang.installment.mvp.ui.service;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.jess.arms.utils.ArmsUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.yinduowang.installment.app.utils.PhoneStatusUtils;
import com.yinduowang.installment.app.utils.ToastUtils;
import com.yinduowang.installment.di.component.DaggerUploadUserInfoComponent;
import com.yinduowang.installment.mvp.contract.UploadUserInfoContract;
import com.yinduowang.installment.mvp.presenter.UploadUserInfoPresenter;

import javax.inject.Inject;

public class UploadService extends JobIntentService implements UploadUserInfoContract.View {

    @Inject
    UploadUserInfoPresenter mPresenter;

    public static final int JOB_ID = 10086;

    public static final int UPLOAD_TYPE_GPS = 0;
    public static final int UPLOAD_TYPE_ADDRESS_BOOK = 1;
    //    public static final int UPLOAD_TYPE_MSM = 2;
//    public static final int UPLOAD_TYPE_APP_LIST = 3;
    public static final int UPLOAD_TYPE_BQSDF = 4;
    public static final int UPLOAD_TYPE_PAGE = 5;
    public static final int UPLOAD_TYPE_ERROR = 6;
    public static final int CHECK_VERSION = 7;


    public static final String UPLOAD_TYPE = "uploadType";
    public static final String UPLOAD_MSG = "msg";
    public static final String UPLOAD_IMEI = "imei";

    public static void uploadUserInfo(Context context, int type) {
        Intent intent = new Intent(context, UploadService.class);
        intent.putExtra(UPLOAD_TYPE, type);
        startService(context, intent);
    }

    private static void startService(Context context, Intent intent) {
        try {
            enqueueWork(context, UploadService.class, JOB_ID, intent);
        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);


        }
    }

    /**
     * UPLOAD_TYPE_BQSDF
     * UPLOAD_TYPE_ERROR
     * UPLOAD_TYPE_PAGE
     */
    public static void uploadUserInfo(Context context, int type, String msg) {
        Intent intent = new Intent(context, UploadService.class);
        intent.putExtra(UPLOAD_TYPE, type);
        intent.putExtra(UPLOAD_MSG, msg);
        if (type == UPLOAD_TYPE_PAGE) {
            try {
                String imei = PhoneStatusUtils.INSTANCE.getIMEI();
                if (!TextUtils.isEmpty(imei)) {
                    intent.putExtra(UPLOAD_IMEI, imei);
                    startService(context, intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
                CrashReport.postCatchedException(e);
                ToastUtils.INSTANCE.makeText(context, "电话权限请求失败，请到设置中给予权限");
            }
        } else {
            startService(context, intent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerUploadUserInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(ArmsUtils.obtainAppComponentFromContext(this))
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        int uploadType = intent.getIntExtra(UPLOAD_TYPE, -1);
        switch (uploadType) {
            case UPLOAD_TYPE_GPS:
                // 上传位置信息
                mPresenter.uploadGPS();
                waitSix();
                break;
            case UPLOAD_TYPE_ADDRESS_BOOK:
                // 上传联系人
                mPresenter.uploadAddressBookToServer();
                waitSix();
                break;
//            case UPLOAD_TYPE_MSM:
//                // 上传短信
//                mPresenter.updataAllSms();
//                waitSix();
//                break;
//            case UPLOAD_TYPE_APP_LIST:
//                // 上传app列表
//                mPresenter.updataAllAppList();
//                waitSix();
//                break;
            case UPLOAD_TYPE_BQSDF:
                // 上传设备指纹信息
//                mPresenter.initBqsDFSDK();
                mPresenter.submitTokenkey(intent.getStringExtra(UPLOAD_MSG));
                waitSix();
                break;
            case UPLOAD_TYPE_PAGE:
                // 上传页面统计
                mPresenter.updataPage(intent.getStringExtra(UPLOAD_MSG), intent.getStringExtra("imei"));
                break;
            case UPLOAD_TYPE_ERROR:
                // 上传报错信息
                mPresenter.updataError(intent.getStringExtra(UPLOAD_MSG));
                break;
            case CHECK_VERSION:
                // 上传报错信息
                mPresenter.checkVersion();
                break;
        }
    }

    private void waitSix() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}

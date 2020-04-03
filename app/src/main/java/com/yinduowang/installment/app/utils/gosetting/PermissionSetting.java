package com.yinduowang.installment.app.utils.gosetting;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.tencent.bugly.crashreport.CrashReport;
import com.yinduowang.installment.R;

/**
 * 跳转到设置 应用对应页面
 */
public class PermissionSetting {

    public static void toPermissionSetting(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 23) {
                if (RomUtils.checkIsMiuiRom()) {
                    MiuiUtils.applyMiuiPermission(context);
                } else if (RomUtils.checkIsMeizuRom()) {
                    MeizuUtils.applyPermission(context);
                } else if (RomUtils.checkIsHuaweiRom()) {
                    HuaweiUtils.applyPermission(context);
                } else if (RomUtils.checkIs360Rom()) {
                    QikuUtils.applyPermission(context);
                } else if (RomUtils.checkIsOppoRom()) {
                    OppoUtils.applyOppoPermission(context);
                } else {
                    RomUtils.getAppDetailSettingIntent(context);
                }
            } else {
                if (RomUtils.checkIsMeizuRom()) {
                    MeizuUtils.applyPermission(context);
                } else {
                    if (RomUtils.checkIsOppoRom() || RomUtils.checkIsVivoRom()
                            || RomUtils.checkIsHuaweiRom() || RomUtils.checkIsSamsunRom()
                    ) {
                        RomUtils.getAppDetailSettingIntent(context);
                    } else if (RomUtils.checkIsMiuiRom()) {
                        MiuiUtils.toPermisstionSetting(context);
                    } else {
                        RomUtils.commonROMPermissionApplyInternal(context);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);
            try {
                Uri packageURI = Uri.parse("package:" + context.getPackageName());
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                context.startActivity(intent);
            } catch (Exception e1) {
                Toast.makeText(context, "跳转设置页面失败", Toast.LENGTH_SHORT).show();
                e1.printStackTrace();
                CrashReport.postCatchedException(e);
            }
        }
    }

    public static void toPermissionSetting(Activity activity, int requestCode) {
        try {
            if (Build.VERSION.SDK_INT < 23) {
                if (RomUtils.checkIsMiuiRom()) {
                    MiuiUtils.applyMiuiPermission(activity, requestCode);
                } else if (RomUtils.checkIsMeizuRom()) {
                    MeizuUtils.applyPermission(activity, requestCode);
                } else if (RomUtils.checkIsHuaweiRom()) {
                    HuaweiUtils.applyPermission(activity, requestCode);
                } else if (RomUtils.checkIs360Rom()) {
                    QikuUtils.applyPermission(activity, requestCode);
                } else if (RomUtils.checkIsOppoRom()) {
                    OppoUtils.applyOppoPermission(activity, requestCode);
                } else {
                    RomUtils.getAppDetailSettingIntent(activity, requestCode);
                }
            } else {
                if (RomUtils.checkIsMeizuRom()) {
                    MeizuUtils.applyPermission(activity, requestCode);
                } else {
                    if (RomUtils.checkIsOppoRom() || RomUtils.checkIsVivoRom()
                            || RomUtils.checkIsHuaweiRom() || RomUtils.checkIsSamsunRom()
                    ) {
                        RomUtils.getAppDetailSettingIntent(activity, requestCode);
                    } else if (RomUtils.checkIsMiuiRom()) {
                        MiuiUtils.toPermisstionSetting(activity, requestCode);
                    } else {
                        RomUtils.commonROMPermissionApplyInternal(activity, requestCode);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);
            try {
                Uri packageURI = Uri.parse("package:" + activity.getPackageName());
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                activity.startActivity(intent);
            } catch (Exception e1) {
                Toast.makeText(activity, "跳转设置页面失败", Toast.LENGTH_SHORT).show();
                e1.printStackTrace();
                CrashReport.postCatchedException(e);
            }
        }
    }

    public static void showFailureWithAskNeverAgainDialog(Context context, String permission) {
        switch (permission) {
            // 麦克风
            case Manifest.permission.RECORD_AUDIO:
                showMsgFailureWithAskNeverAgainDialog(context,context.getString(R.string.RECORD_AUDIO));
                break;
            // 位置
            case Manifest.permission.ACCESS_FINE_LOCATION:
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                showMsgFailureWithAskNeverAgainDialog(context,context.getString(R.string.ACCESS_FINE_LOCATION));
                break;
            // 电话
            case Manifest.permission.READ_PHONE_STATE:
                showMsgFailureWithAskNeverAgainDialog(context,context.getString(R.string.READ_PHONE_STATE));
                break;
            // 拨打电话
            case Manifest.permission.CALL_PHONE:
                showMsgFailureWithAskNeverAgainDialog(context,context.getString(R.string.CALL_PHONE));
                break;
            // 存储
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                showMsgFailureWithAskNeverAgainDialog(context,context.getString(R.string.WRITE_EXTERNAL_STORAGE));
                break;
            // 相机
            case Manifest.permission.CAMERA:
                showMsgFailureWithAskNeverAgainDialog(context,context.getString(R.string.CAMERA));
                break;
            // 通讯录
            case Manifest.permission.READ_CONTACTS:
                showMsgFailureWithAskNeverAgainDialog(context,context.getString(R.string.READ_CONTACTS));
                break;
        }
    }

    public static void showMsgFailureWithAskNeverAgainDialog(Context context, String msg) {
        new QMUIDialog.MessageDialogBuilder(context)
                .setTitle("提示")
                .setMessage(msg)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        PermissionSetting.toPermissionSetting(context);
                    }
                })
                .setCanceledOnTouchOutside(false)
                .setCancelable(false)
                .create(R.style.QMUI_Dialog).show();
    }
}

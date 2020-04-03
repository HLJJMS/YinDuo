package com.yinduowang.installment.app.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.View
import timber.log.Timber

import java.util.*

object PhoneStatusUtils {

    @SuppressLint("MissingPermission")
    fun getIMEI(): String {
        var serial: String?
        val mSalvidor = "35" +
                Build.BOARD.length % 10 + Build.BRAND.length % 10 +

                Build.CPU_ABI.length % 10 + Build.DEVICE.length % 10 +

                Build.DISPLAY.length % 10 + Build.HOST.length % 10 +

                Build.ID.length % 10 + Build.MANUFACTURER.length % 10 +

                Build.MODEL.length % 10 + Build.PRODUCT.length % 10 +

                Build.TAGS.length % 10 + Build.TYPE.length % 10 +

                Build.USER.length % 10 //13 位

        try {
            serial = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Build.getSerial()
            } else {
                Build.SERIAL
            }
            //API>=9 使用serial号
            return UUID(mSalvidor.hashCode().toLong(), serial!!.hashCode().toLong()).toString()
        } catch (exception: Exception) {
            //serial需要一个初始化
            serial = "serial" // 随便一个初始化
        }

        //使用硬件信息拼凑出来的15位号码
        val imei = UUID(mSalvidor.hashCode().toLong(), serial!!.hashCode().toLong()).toString()
        Timber.i("imei:$imei")
        return imei
    }

    /**
     * 获得NavigationBar的高度
     */
    fun getNavigationBarHeight(activity: Activity): Int {
        var result = 0
        val resources = activity.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0 && checkHasNavigationBar(activity)) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun checkHasNavigationBar(activity: Activity): Boolean {
        var hasNavigationBar = false
        val windowManager = activity.windowManager
        val d = windowManager.defaultDisplay

        val realDisplayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            d.getRealMetrics(realDisplayMetrics)
        }

        val realHeight = realDisplayMetrics.heightPixels
        val realWidth = realDisplayMetrics.widthPixels

        val displayMetrics = DisplayMetrics()
        d.getMetrics(displayMetrics)

        val displayHeight = displayMetrics.heightPixels
        val displayWidth = displayMetrics.widthPixels
        hasNavigationBar = realWidth - displayWidth > 0 || realHeight - displayHeight > 0
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            //判断是否隐藏了底部虚拟导航
            var navigationBarIsMin = 0
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                navigationBarIsMin = Settings.System.getInt(activity.contentResolver,
                        "navigationbar_is_min", 0)
            } else {
                navigationBarIsMin = Settings.Global.getInt(activity.contentResolver,
                        "navigationbar_is_min", 0)
            }
            if ("1" == navBarOverride || 1 == navigationBarIsMin) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {
        }

        return hasNavigationBar
    }

    fun getLocation(v: View): IntArray {
        val loc = IntArray(4)
        val location = IntArray(2)
        v.getLocationOnScreen(location)
        loc[0] = location[0]
        loc[1] = location[1]
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        v.measure(w, h)

        loc[2] = v.width
        loc[3] = v.measuredHeight
        return loc
    }

    //获取状态栏高度
    fun getStatusBarHeight(activity: Activity): Int {
        var statusBarHeight1 = -1
        //获取status_bar_height资源的ID
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = activity.resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight1
    }


    /**
     * 获取应用程序名称
     */
    fun getAppName(context: Context): String {

        var packageManager = context.getPackageManager();
        var packageInfo = packageManager.getPackageInfo(
                context.getPackageName(), 0);
        var labelRes = packageInfo.applicationInfo.labelRes;
        return context.getResources().getString(labelRes);
    }

}

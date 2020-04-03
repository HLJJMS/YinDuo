package com.yinduowang.installment.app.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.TouchDelegate
import android.view.View

object ViewUtil {

    /**************
     * 获取sdk路径
     *
     * @return
     */
    val sdCardPath: String
        get() = Environment.getExternalStorageDirectory().absolutePath

    /**
     * 扩大View的触摸和点击响应范围,最大不超过其父View范围
     *
     * @param view
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    fun expandViewTouchDelegate(view: View, top: Int, bottom: Int, left: Int, right: Int) {
        (view.parent as View).post {
            val bounds = Rect()
            view.isEnabled = true
            view.getHitRect(bounds)

            bounds.top -= top
            bounds.bottom += bottom
            bounds.left -= left
            bounds.right += right

            val touchDelegate = TouchDelegate(bounds, view)

            if (View::class.java.isInstance(view.parent)) {
                (view.parent as View).touchDelegate = touchDelegate
            }
        }
    }


    /******
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    fun getStatusBarH(context: Context): Int {
        var statusHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = Integer.parseInt(clazz.getField("status_bar_height").get(`object`).toString())
            statusHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return statusHeight
    }

    //获取当前app的版本号
    fun getAppVersion(context: Context): String {
        try {
            val packageManager = context.packageManager
            val packInfo: PackageInfo
            packInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return ""
        }

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


}

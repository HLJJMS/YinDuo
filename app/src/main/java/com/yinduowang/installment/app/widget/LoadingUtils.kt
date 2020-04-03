package com.yinduowang.installment.app.widget

import android.app.Activity
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import com.tencent.bugly.crashreport.CrashReport
import com.yinduowang.installment.R
import com.yinduowang.installment.app.constant.Constant
import com.yinduowang.installment.app.widget.LoadingDialog

class LoadingUtils() : Parcelable {
    var dialog: LoadingDialog? = null
    var runnable: Runnable? = null
    var handler: Handler? = null
    var isDismissing: Boolean = false
    var activity:Activity?=null


    constructor(activity: Activity) :this()  {
        this.activity = activity
        dialog = LoadingDialog(activity, R.style.dialog_waiting)
        activity.intent.putExtra(Constant.TAG_LOADING_UTILS, this)
        runnable = Runnable {
            isDismissing = false
            dismissNow()
        }
        handler = Handler()
    }

    fun init(activity: Activity) {
        try {
            val loadingUtils = LoadingUtils(activity)
            activity.intent.putExtra(Constant.TAG_LOADING_UTILS, loadingUtils)
        } catch (e: Exception) {
            e.printStackTrace()
            CrashReport.postCatchedException(e)
        }
    }

    fun show(activity: Activity?) {
        try {
            val loadingUtils = activity!!.intent.getParcelableExtra<LoadingUtils>(Constant.TAG_LOADING_UTILS)
            if (loadingUtils != null) {
                if (loadingUtils.isDismissing) {
                    removeCallBacks(loadingUtils)
                    if (!loadingUtils.isShow()) {
                        loadingUtils.show()
                    }
                } else {
                    loadingUtils.show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            CrashReport.postCatchedException(e)
        }
    }

    private fun removeCallBacks(loadingUtils: LoadingUtils) {
        loadingUtils.handler?.removeCallbacks(loadingUtils.runnable)
    }

    fun dismiss(activity: Activity) {
        try {
            val loadingUtils = activity.intent.getParcelableExtra<LoadingUtils>(Constant.TAG_LOADING_UTILS)
            loadingUtils.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
            CrashReport.postCatchedException(e)
        }
    }

    /**
     * 立即隐藏
     */
    fun dismissNow(activity: Activity) {
        try {
            val loadingUtils = activity.intent.getParcelableExtra<LoadingUtils>(Constant.TAG_LOADING_UTILS)
            loadingUtils.dismissNow()
            removeCallBacks(loadingUtils)
        } catch (e: Exception) {
            e.printStackTrace()
            CrashReport.postCatchedException(e)
        }
    }

    /**
     * 是否显示
     */
    fun isShow(activity: Activity): Boolean {
        return try {
            val loadingUtils = activity.intent.getParcelableExtra<LoadingUtils>(Constant.TAG_LOADING_UTILS)
            loadingUtils.isShow()
        } catch (e: Exception) {
            e.printStackTrace()
            CrashReport.postCatchedException(e)
            false
        }
    }

    private fun show() {
        if (dialog != null && !dialog?.isShowing!!) {
            dialog?.show()
        }
    }

    private fun isShow(): Boolean {
        return if (dialog != null) {
            dialog?.isShowing!!
        } else false
    }

    private fun dismiss() {
        isDismissing = true
        handler?.postDelayed(runnable, Constant.DELAY_TIME.toLong())
    }

    private fun dismissNow() {
        if (dialog != null) {
            dialog?.dismiss()
        }
    }

    constructor(parcel: Parcel) :this () {}

    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoadingUtils> {
        override fun createFromParcel(parcel: Parcel): LoadingUtils {
            return LoadingUtils(parcel)
        }

        override fun newArray(size: Int): Array<LoadingUtils?> {
            return arrayOfNulls(size)
        }
    }

}
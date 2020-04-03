package com.yinduowang.installment.app.widget.popwindow

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import com.jess.arms.utils.ArmsUtils

import com.yinduowang.installment.R

class NewServiceChargePopwindow(private val activity: Activity, account: String, approveFees: String, channelServiceFees: String, managementFees: String, platformServiceFees: String, descStr: String) {

    private lateinit var popView: View
    private lateinit var popWindow: PopupWindow
    private lateinit var sure: TextView
    private  lateinit var approve_fees: TextView
    private lateinit var channel_service_fees: TextView
    private lateinit var management_fees: TextView
    private lateinit var platform_service_fees: TextView
    private lateinit var desc: TextView

    init {


        serviceCharge(account, approveFees, channelServiceFees, managementFees, platformServiceFees, descStr)
    }

    /**
     * 服务费弹框
     */
    private fun serviceCharge(account: String, approveFees: String, channelServiceFees: String, managementFees: String, platformServiceFees: String, descStr: String) {
        popView = activity.layoutInflater.inflate(R.layout.layout_new_pop_service_charge, null)
        sure = popView.findViewById<View>(R.id.sure) as TextView
        approve_fees = popView.findViewById<View>(R.id.approve_fees) as TextView
        channel_service_fees = popView.findViewById<View>(R.id.channel_service_fees) as TextView
        management_fees = popView.findViewById<View>(R.id.management_fees) as TextView
        platform_service_fees = popView.findViewById<View>(R.id.platform_service_fees) as TextView
        desc = popView.findViewById<View>(R.id.desc) as TextView

        approve_fees.text = approveFees+"元"
        channel_service_fees.text = channelServiceFees+"元"
        management_fees.text = managementFees+"元"
        platform_service_fees.text = platformServiceFees+"元"
        desc.text = descStr


        sure.setOnClickListener { popWindow.dismiss() }


        popWindow = PopupWindow(popView, (ArmsUtils.getScreenWidth(activity)*0.75).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popWindow.animationStyle = R.style.popwin_anim_style
        //添加背景使其点击popupWindow以外的区域（包括返回键）消失，否则不消失
        //popWindow.setBackgroundDrawable(new ColorDrawable());
        popWindow.isOutsideTouchable = false
        val lp = activity.window.attributes
        lp.alpha = 0.5f
        activity.window.attributes = lp
        popWindow.showAtLocation(activity.window.decorView, Gravity.CENTER, 0, 0)
        popWindow.setOnDismissListener {
            lp.alpha = 1.0f
            activity.window.attributes = lp
        }
    }

}

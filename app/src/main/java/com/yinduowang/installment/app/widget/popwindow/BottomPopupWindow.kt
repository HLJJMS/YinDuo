package com.yinduowang.installment.app.widget.popwindow

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.RelativeLayout

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton
import com.yinduowang.installment.R

import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * @Description: 底部弹出popupwindow通用类封装（两个按钮三个按钮）
 * @Author: tyh
 * @Date: 2019-10-18 09:59:32
 * @Version: 1.0, 2019-10-18
 * @LastEditors:tyh
 * @LastEditTime:
 * @Deprecated: false
 */
class BottomPopupWindow(activity: Activity, isThreeButton: Boolean) {


    companion object {
        const val CANCEL = 3
        const val ONE_BUTTON = 1
        const val TWO_BUTTON = 2
    }

    private val activity: Reference<Activity>
    private var popupWindow: PopupWindow? = null
    private var popupWindowViewGroup: ViewGroup? = null

    private var oneButton: QMUIRoundButton? = null
    private var twoButton: QMUIRoundButton? = null
    private var cancel: QMUIRoundButton? = null
    //    动画播放时间
    private val time = 300
    //    是否为三个按钮的popwindow
    private var isThreeButton = true
    var buttonClick: ButtonClick? = null

    init {
        this.isThreeButton = isThreeButton
        this.activity = WeakReference(activity)
        setPopupWindow()
    }


    fun setPopupWindow() {
        if (isThreeButton) {
            setThreeButtonPopupWindow()
        } else {
            setTwoButtonPopupWindow()
        }

    }

    /**
     * @Description: 设置三个按钮的popup
     * @Author: tyh
     * @Date: 2019-10-18 09:59:32
     * @Version: 1.0, 2019-10-18
     * @LastEditors:tyh
     * @LastEditTime:
     * @Deprecated: false
     */
    fun setThreeButtonPopupWindow() {
        popupWindowViewGroup = activity.get()?.getLayoutInflater()?.inflate(
                R.layout.popup_three_button, null, true) as ViewGroup
        setGM()
        oneButton = popupWindowViewGroup!!.findViewById<View>(R.id.customer) as QMUIRoundButton

        twoButton = popupWindowViewGroup!!.findViewById<View>(R.id.call) as QMUIRoundButton

        cancel = popupWindowViewGroup!!.findViewById<View>(R.id.cancel) as QMUIRoundButton

        //跳转至美洽客服 游客

        oneButton!!.setOnClickListener {
            buttonClick?.setClick(ONE_BUTTON)
            popupWindow!!.dismiss()
        }

        twoButton!!.setOnClickListener {
            buttonClick?.setClick(TWO_BUTTON)
            popupWindow!!.dismiss()
        }

        cancel!!.setOnClickListener {
            buttonClick?.setClick(CANCEL)
            popupWindow!!.dismiss()
        }
    }

    /**
     * @Description: 设置2个按钮的popup
     * @Author: tyh
     * @Date: 2019-10-18 09:59:32
     * @Version: 1.0, 2019-10-18
     * @LastEditors:tyh
     * @LastEditTime:
     * @Deprecated: false
     */
    fun setTwoButtonPopupWindow() {
        popupWindowViewGroup = activity.get()?.getLayoutInflater()?.inflate(
                R.layout.popup_two_button, null, true) as ViewGroup
        setGM()
        oneButton = popupWindowViewGroup!!.findViewById<View>(R.id.ok) as QMUIRoundButton
        cancel = popupWindowViewGroup!!.findViewById<View>(R.id.cancel) as QMUIRoundButton


        oneButton!!.setOnClickListener {
            buttonClick?.setClick(ONE_BUTTON)
            popupWindow!!.dismiss()
        }

        cancel!!.setOnClickListener {
            buttonClick?.setClick(CANCEL)
            popupWindow!!.dismiss()
        }
    }

    /**
     * @Description: 设置通用的popup
     * @Author: tyh
     * @Date: 2019-10-18 09:59:32
     * @Version: 1.0, 2019-10-18
     * @LastEditors:tyh
     * @LastEditTime:
     * @Deprecated: false
     */

    fun setGM() {
        popupWindow = PopupWindow(popupWindowViewGroup, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT, false)
        popupWindow!!.setOnDismissListener {
            Handler().postDelayed({
                setBackgroundAlpha(1.0f)
                activity.get()?.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }, time.toLong())
        }
        popupWindow!!.width = ViewGroup.LayoutParams.MATCH_PARENT
        popupWindow!!.height = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow!!.animationStyle = R.style.popwindow_anim_style
    }

    /**
     * @Description: 设置第一按钮文字
     * @Author: tyh
     * @Date: 2019-10-18 09:59:32
     * @Version: 1.0, 2019-10-18
     * @LastEditors:tyh
     * @LastEditTime:
     * @Deprecated: false
     */

    fun setOneButtontText(msg: String) {
        oneButton!!.text = msg
    }

    /**
     * @Description: 设置第2按钮文字
     * @Author: tyh
     * @Date: 2019-10-18 09:59:32
     * @Version: 1.0, 2019-10-18
     * @LastEditors:tyh
     * @LastEditTime:
     * @Deprecated: false
     */
    fun setTwoButtontText(msg: String) {
        twoButton!!.text = msg
    }

    /**
     * @Description: 设置取消按钮文字
     * @Author: tyh
     * @Date: 2019-10-18 09:59:32
     * @Version: 1.0, 2019-10-18
     * @LastEditors:tyh
     * @LastEditTime:
     * @Deprecated: false
     */
    fun setCancelButtontText(msg: String) {
        cancel!!.text = msg
    }

    /**
     * @Description: 设置取消按钮文字（是否加粗）
     * @Author: tyh
     * @Date: 2019-10-18 09:59:32
     * @Version: 1.0, 2019-10-18
     * @LastEditors:tyh
     * @LastEditTime:
     * @Deprecated: false
     */
    fun setCancelButtontText(msg: String, blod: Boolean) {
        cancel!!.text = msg
        cancel!!.paint.isFakeBoldText = blod
    }


    //

    /**
     * @Description: 设置popupwindow显示的背景颜色
     * @Author: tyh
     * @Date: 2019-10-18 09:59:32
     * @Version: 1.0, 2019-10-18
     * @LastEditors:tyh
     * @LastEditTime:
     * @Deprecated: false
     */
    private fun setBackgroundAlpha(color: Float?) {
        val lp = activity.get()?.getWindow()?.attributes
        lp?.alpha = color!!
        activity.get()?.getWindow()?.attributes = lp
    }


    /**
     * @Description:   popupwindow显示
     * @Author: tyh
     * @Date: 2019-10-18 09:59:32
     * @Version: 1.0, 2019-10-18
     * @LastEditors:tyh
     * @LastEditTime:
     * @Deprecated: false
     */
    fun show() {
        popupWindow!!.showAtLocation(activity.get()?.getWindow()?.decorView,
                Gravity.BOTTOM, 0, 0)
        setBackgroundAlpha(0.618f)
        activity.get()?.getWindow()?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    /**
     * @Description:   popupwindow消失
     * @Author: tyh
     * @Date: 2019-10-18 09:59:32
     * @Version: 1.0, 2019-10-18
     * @LastEditors:tyh
     * @LastEditTime:
     * @Deprecated: false
     */
    fun dismiss() {
        popupWindow!!.dismiss()
    }

    fun setOnButtonClick(buttonClick: ButtonClick) {
        this.buttonClick = buttonClick
    }

    interface ButtonClick {
        fun setClick(id: Int)
    }
}


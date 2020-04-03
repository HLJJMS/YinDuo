package com.yinduowang.installment.app.widget

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.jess.arms.utils.DeviceUtils
import com.yinduowang.installment.R

class LoadingDialog(val activity: Activity, style: Int) : Dialog(activity, style) {

    private var ivLoading: ImageView? = null

    init {
        setContentView(R.layout.layout_dialog_loading)
        val layoutParams = window!!.attributes
        layoutParams.x = 0
        layoutParams.y = 0
        window!!.attributes = layoutParams

        findViewById<View>(R.id.flContent).setOnClickListener {}
        ivLoading = findViewById(R.id.ivLoading)

        setCanceledOnTouchOutside(false)
    }

    override fun show() {
        super.show()
        var anim = AnimationUtils.loadAnimation(activity, R.anim.anim_rotate) as Animation
        anim.fillAfter = true//设置旋转后停止
        ivLoading?.startAnimation(anim)
    }

    override fun dismiss() {
        super.dismiss()
        ivLoading?.clearAnimation()
    }
}
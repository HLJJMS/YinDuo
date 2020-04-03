package com.yinduowang.installment.app.utils

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

class NoLineClickable( val mListener: View.OnClickListener) : ClickableSpan(), View.OnClickListener {

    override fun onClick(v: View) {
        mListener.onClick(v)
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.color = ds.linkColor
        ds.isUnderlineText = false    //去除超链接的下划线
    }

}

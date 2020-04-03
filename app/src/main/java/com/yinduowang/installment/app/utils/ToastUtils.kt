package com.yinduowang.installment.app.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.yinduowang.installment.R

object ToastUtils {

    fun makeText(context: Context?, msg: Int?) {
        makeText(context, msg, Gravity.BOTTOM)
    }

    fun makeText(context: Context?, msg: Int?, gravity: Int) {
        makeText(context, context?.getString(msg as Int), gravity, 0, 200)
    }

    fun makeText(context: Context?, msg: String?, gravity: Int) {
        makeText(context, msg, gravity, 0, 0)
    }
    fun makeText(context: Context?, msg: String?) {
        makeText(context, msg, Gravity.BOTTOM, 0, 200)
    }


    fun makeText(context: Context?, msg: String?, gravity: Int, xOffset: Int, yOffset: Int) {
        if (context != null && msg != null) {
            val toast = Toast(context)
            val inflate = context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v = inflate.inflate(R.layout.layout_toast_textview, null)
            val tv = v.findViewById<TextView>(R.id.toast_tv)
            toast.view = v
            toast.setGravity(gravity or Gravity.FILL_HORIZONTAL, xOffset, yOffset)
            toast.duration = Toast.LENGTH_SHORT
            tv.text = msg
            toast.show()
        }
    }
}

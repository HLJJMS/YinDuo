package com.yinduowang.installment.app.widget.popwindow

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import androidx.core.content.ContextCompat
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.qmuiteam.qmui.layout.IQMUILayout.HIDE_RADIUS_SIDE_BOTTOM
import com.qmuiteam.qmui.layout.QMUIFrameLayout
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.yinduowang.installment.R
import com.yinduowang.installment.mvp.ui.activity.ForgetPayPasswordActivity
import java.util.concurrent.TimeUnit


class PasswordInputPopupWindow(private val activty: Activity) {
    private lateinit var popView: View
    private lateinit var popupWindow: PopupWindow

    init {
        initPopupWindow()
    }
     //初始化
    private fun initPopupWindow() {
         popView = LayoutInflater.from(activty).inflate(R.layout.layout_popup_input_password, null)
         val edPwdKey=popView.findViewById<EditText>(R.id.edPwdKey)
         val ivCancle=popView.findViewById<ImageView>(R.id.iv_cancle_chose)
         val edPwdKey1=popView.findViewById<TextView>(R.id.edPwdKey1)
         val edPwdKey2=popView.findViewById<TextView>(R.id.edPwdKey2)
         val edPwdKey3=popView.findViewById<TextView>(R.id.edPwdKey3)
         val edPwdKey4=popView.findViewById<TextView>(R.id.edPwdKey4)
         val edPwdKey5=popView.findViewById<TextView>(R.id.edPwdKey5)
         val edPwdKey6=popView.findViewById<TextView>(R.id.edPwdKey6)
         val view1=popView.findViewById<View>(R.id.view1)
         val view2=popView.findViewById<View>(R.id.view2)
         val view3=popView.findViewById<View>(R.id.view3)
         val view4=popView.findViewById<View>(R.id.view4)
         val view5=popView.findViewById<View>(R.id.view5)
         val view6=popView.findViewById<View>(R.id.view6)
         val tvForgetPassword=popView.findViewById<TextView>(R.id.tvForgetPassword)
         popupWindow = PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
         popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true
        popupWindow.setBackgroundDrawable(BitmapDrawable())
        val qmuilayout = popView.findViewById<QMUIFrameLayout>(R.id.qmuilayout)
        qmuilayout.setRadius(QMUIDisplayHelper.dp2px(activty,12),HIDE_RADIUS_SIDE_BOTTOM)
        val lp = activty.window.attributes
        lp.alpha = 0.5f
        activty.window.attributes = lp
        popupWindow.setOnDismissListener {
            lp.alpha = 1.0f
            activty.window.attributes = lp
        }
         popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED)
         popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
         ivCancle.setOnClickListener {
             popupWindow.dismiss()
         }
         tvForgetPassword.clicks().throttleFirst(500,TimeUnit.MILLISECONDS).subscribe {
             popupWindow.dismiss()
             val intent = Intent(activty, ForgetPayPasswordActivity::class.java)
             intent.putExtra("intentType", "1")
             activty.startActivity(intent)
         }
         edPwdKey.textChanges().subscribe { s->
             //分别判断输入数字来显示
             edPwdKey1.visibility= if (s.toString().trim().length>0) View.VISIBLE else View.INVISIBLE
             if (s.toString().trim().length==1) {
                 view1.setBackgroundResource(R.color.color_333333)
                 edPwdKey1.setTextColor(ContextCompat.getColor(activty,R.color.black))}
             else{
                 view1.setBackgroundResource(R.color.color_D0D0D0)
                 edPwdKey1.setTextColor(ContextCompat.getColor(activty,R.color.color_D0D0D0))
             }
             edPwdKey2.visibility= if (s.toString().trim().length>1) View.VISIBLE else View.INVISIBLE
             if (s.toString().trim().length==2)  {
                 view2.setBackgroundResource(R.color.color_333333)
                 edPwdKey2.setTextColor(ContextCompat.getColor(activty,R.color.black))}
             else{
                 view2.setBackgroundResource(R.color.color_D0D0D0)
                 edPwdKey2.setTextColor(ContextCompat.getColor(activty,R.color.color_D0D0D0))
             }
             edPwdKey3.visibility= if (s.toString().trim().length>2) View.VISIBLE else View.INVISIBLE
             if (s.toString().trim().length==3) {
                 view3.setBackgroundResource(R.color.color_333333)
                 edPwdKey3.setTextColor(ContextCompat.getColor(activty,R.color.black))}
             else{
                 view3.setBackgroundResource(R.color.color_D0D0D0)
                 edPwdKey3.setTextColor(ContextCompat.getColor(activty,R.color.color_D0D0D0))
             }
             edPwdKey4.visibility= if (s.toString().trim().length>3) View.VISIBLE else View.INVISIBLE
             if (s.toString().trim().length==4) {
                 view4.setBackgroundResource(R.color.color_333333)
                 edPwdKey4.setTextColor(ContextCompat.getColor(activty,R.color.black))}
             else{
                 view4.setBackgroundResource(R.color.color_D0D0D0)
                 edPwdKey4.setTextColor(ContextCompat.getColor(activty,R.color.color_D0D0D0))
             }
             edPwdKey5.visibility= if (s.toString().trim().length>4) View.VISIBLE else View.INVISIBLE
             if (s.toString().trim().length==5) {
                 view5.setBackgroundResource(R.color.color_333333)
                 edPwdKey5.setTextColor(ContextCompat.getColor(activty,R.color.black))}
             else{
                 view5.setBackgroundResource(R.color.color_D0D0D0)
                 edPwdKey5.setTextColor(ContextCompat.getColor(activty,R.color.color_D0D0D0))
             }
             edPwdKey6.visibility= if (s.toString().trim().length>5) View.VISIBLE else View.INVISIBLE
             if (s.toString().trim().length==6) {
                 view6.setBackgroundResource(R.color.color_333333)
                 edPwdKey6.setTextColor(ContextCompat.getColor(activty,R.color.black))}
             else{
                 view6.setBackgroundResource(R.color.color_D0D0D0)
                 edPwdKey6.setTextColor(ContextCompat.getColor(activty,R.color.color_D0D0D0))
             }
             if (s.toString().trim().length==6) {
                 mListener?.let{
                     it.complete( s.toString().trim())
                     popupWindow.dismiss()
                 }

             }
         }

         showSoft(edPwdKey)
    }
    private fun showSoft(editText: EditText){
        Handler().postDelayed(Runnable {
            editText.setFocusable(true)
            editText.setFocusableInTouchMode(true)
            editText.requestFocus();
            val inputMethodManager= editText?.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(editText, 0);
        },100)
    }

    // 展示
     fun show(){
        popupWindow?.let {
            popupWindow.showAtLocation(activty.window.decorView, Gravity.BOTTOM, 0, 0)
        }
    }
    private var mListener:OnInputCompleteListener?=null
    fun setOnInputCompleteListener(listener:OnInputCompleteListener):PasswordInputPopupWindow{
        mListener=listener
        return this@PasswordInputPopupWindow
    }
    interface OnInputCompleteListener{
        fun complete(password:String)
    }

    fun getWindows(): PopupWindow {
        return popupWindow

    }

}
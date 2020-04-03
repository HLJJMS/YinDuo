package com.yinduowang.installment.app.widget

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.jess.arms.utils.ArmsUtils
import com.qmuiteam.qmui.widget.QMUITopBar
import com.yinduowang.installment.R

class TitleBar(val mContext: Context, attrs: AttributeSet) : QMUITopBar(mContext, attrs) {


    var leftView: View? = null
    var ivLeftImageButton: ImageView? = null
    var tvLeftTextButton: TextView? = null
    var rightView: View? = null
    var ivRightImageButton: ImageView? = null
    var tvRightTextButton: TextView? = null
    var tvTitle: TextView? = null

    init {
        // 默认不显示底部分割线
        setBackgroundDividerEnabled(false)
    }

    /**
     * 显示标题
     * */
    fun showTitle(text: String?) {
        tvTitle = setTitle(text)

    }

    fun showTitle(@StringRes text: Int) {
        tvTitle = setTitle(mContext.resources.getString(text))
    }

    fun setTitleTextColor(@ColorInt textColor: Int) {
        if (tvTitle != null) {
            tvTitle?.setTextColor(textColor)
        } else {
            throw Exception("请先调用setTitleText()方法完成标题初始化")
        }
    }

    fun setTitleTextColorResources(textColor: Int) {
        if (tvTitle != null) {
            tvTitle?.setTextColor(ContextCompat.getColor(mContext, textColor))
        } else {
            throw Exception("请先调用setTitleText()方法完成标题初始化")
        }
    }

    fun setTitleTextSize(textSize: Float) {
        if (tvTitle != null) {
            tvTitle?.textSize = textSize
        } else {
            throw Exception("请先调用setTitleText()方法完成标题初始化")
        }
    }

    fun setTitleTextStyle(textStyle: Int) {
        if (tvTitle != null) {
            tvTitle?.setTypeface(tvTitle!!.typeface, textStyle)
        } else {
            throw Exception("请先调用setTitleText()方法完成标题初始化")
        }
    }

    /**
     * TextUtils.TruncateAt.END
     * */
    fun setTitleEllipsize(where: TextUtils.TruncateAt) {
        if (tvTitle != null) {
            tvTitle?.ellipsize = where
        } else {
            throw Exception("请先调用setTitleText()方法完成标题初始化")
        }
    }

    /**
     * 设置标题显示与隐藏
     * @Deprecated
     */
    fun setTitleVisibility(visibility: Int) {
        if (tvTitle != null) {
            tvTitle?.visibility = visibility
        } else {
            throw Exception("请先调用setTitleText()方法完成标题初始化")
        }
    }

    /**
     * 设置左侧图片按钮及监听
     * */
    fun setLeftImageView(resId: Int) {
        setLeftView(true)
        setImageViewResource(ivLeftImageButton, resId)
        setViewListener(leftView, null)
        addLeftView(leftView, R.id.flLeftView)
    }

    /**
     * 设置左侧图片按钮及监听
     * */
    fun setLeftImageView(resId: Int, listener: OnClickListener?) {
        setLeftView(true)
        setImageViewResource(ivLeftImageButton, resId)
        setViewListener(leftView, listener)
        addLeftView(leftView, R.id.flLeftView)
    }

    /**
     * 设置左侧文字按钮
     * */
    fun setLeftTextView(text: String) {
        setLeftView(false)
        setTextViewString(tvLeftTextButton, text)
        setViewListener(leftView, null)
        addLeftView(leftView, R.id.flLeftView)
    }

    /**
     * 设置左侧文字按钮及监听
     * */
    fun setLeftTextView(text: String, listener: OnClickListener?) {
        setLeftView(false)
        setTextViewString(tvLeftTextButton, text)
        setViewListener(leftView, listener)
        addLeftView(leftView, R.id.flLeftView)
    }

    /**
     * 设置文字颜色
     * */
    fun setLeftTextViewColor(@ColorInt textColor: Int) {
        if (tvLeftTextButton != null) {
            tvLeftTextButton?.setTextColor(textColor)
        } else {
            throw Exception("请先调用setLeftTextView()方法完成按钮初始化")
        }
    }

    /**
     * 设置文字颜色
     * */
    fun setLeftTextViewColorResources(textColor: Int) {
        if (tvLeftTextButton != null) {
            tvLeftTextButton?.setTextColor(ContextCompat.getColor(mContext, textColor))
        } else {
            throw Exception("请先调用setLeftTextView()方法完成按钮初始化")
        }
    }

    /**
     * 设置文字大小
     * */
    fun setLeftTextViewSize(textSize: Float) {
        if (tvLeftTextButton != null) {
            tvLeftTextButton?.textSize = textSize
        } else {
            throw Exception("请先调用setLeftTextView()方法完成按钮初始化")
        }
    }

    /**
     * 设置文字样式
     * */
    fun setLeftTextViewStyle(textStyle: Int) {
        if (tvLeftTextButton != null) {
            tvLeftTextButton?.setTypeface(tvLeftTextButton?.typeface, textStyle)
        } else {
            throw Exception("请先调用setLeftTextView()方法完成按钮初始化")
        }
    }

    private fun setLeftView(isImage: Boolean) {
        leftView = LayoutInflater.from(mContext).inflate(R.layout.layout_top_bar_left, null)
        showLeftViewImageOrText(isImage)
    }

    private fun showLeftViewImageOrText(isImage: Boolean) {
        leftView?.let {
            ivLeftImageButton = it.findViewById(R.id.ivLeftImageButton)
            tvLeftTextButton = it.findViewById(R.id.tvLeftTextButton)
        }
        if (isImage) {
            ivLeftImageButton?.let { it.visibility = View.VISIBLE }
            tvLeftTextButton?.let { it.visibility = View.GONE }
        } else {
            ivLeftImageButton?.let { it.visibility = View.GONE }
            tvLeftTextButton?.let { it.visibility = View.VISIBLE }
        }
    }

    /**
     * 设置右侧图片按钮
     * */
    fun setRightImageView(resId: Int) {
        setRightView(true)
        setImageViewResource(ivRightImageButton, resId)
        setViewListener(rightView, null)
        addRightView(rightView, R.id.flRightView)
    }

    /**
     * 设置右侧图片按钮及监听
     * */
    fun setRightImageView(resId: Int, listener: OnClickListener?) {
        setRightView(true)
        setImageViewResource(ivRightImageButton, resId)
        setViewListener(rightView, listener)
        addRightView(rightView, R.id.flRightView)
    }

    /**
     * 设置右侧文字按钮
     * */
    fun setRightTextView(text: String) {
        setRightView(false)
        setTextViewString(tvRightTextButton, text)
        setViewListener(rightView, null)
        addRightView(rightView, R.id.flRightView)
    }

    /**
     * 设置右侧文字按钮及监听
     * */
    fun setRightTextView(text: String, listener: OnClickListener?) {
        setRightView(false)
        setTextViewString(tvRightTextButton, text)
        setViewListener(rightView, listener)
        addRightView(rightView, R.id.flRightView)
    }

    /**
     * 设置文字颜色
     * */
    fun setRightTextViewColor(@ColorInt textColor: Int) {
        if (tvRightTextButton != null) {
            tvRightTextButton?.setTextColor(textColor)
        } else {
            throw Exception("请先调用setRightTextView()方法完成按钮初始化")
        }
    }

    /**
     * 设置文字颜色
     * */
    fun setRightTextViewColorResources(textColor: Int) {
        if (tvRightTextButton != null) {
            tvRightTextButton?.setTextColor(ContextCompat.getColor(mContext, textColor))
        } else {
            throw Exception("请先调用setRightTextView()方法完成按钮初始化")
        }
    }

    /**
     * 设置文字大小
     * */
    fun setRightTextViewSize(textSize: Float) {
        if (tvRightTextButton != null) {
            tvRightTextButton?.textSize = textSize
        } else {
            throw Exception("请先调用setRightTextView()方法完成按钮初始化")
        }
    }

    /**
     * 设置文字样式
     * */
    fun setRightTextViewStyle(textStyle: Int) {
        if (tvRightTextButton != null) {
            tvRightTextButton?.setTypeface(tvRightTextButton?.typeface, textStyle)
        } else {
            throw Exception("请先调用setRightTextView()方法完成按钮初始化")
        }
    }

    private fun setRightView(isImage: Boolean) {
        rightView = LayoutInflater.from(mContext).inflate(R.layout.layout_top_bar_right, null)
        showRightViewImageOrText(isImage)
    }

    private fun showRightViewImageOrText(isImage: Boolean) {
        rightView?.let {
            ivRightImageButton = it.findViewById(R.id.ivRightImageButton)
            tvRightTextButton = it.findViewById(R.id.tvRightTextButton)
        }
        if (isImage) {
            ivRightImageButton?.let { it.visibility = View.VISIBLE }
            tvRightTextButton?.let { it.visibility = View.GONE }
        } else {
            ivRightImageButton?.let { it.visibility = View.GONE }
            tvRightTextButton?.let { it.visibility = View.VISIBLE }
        }
    }

    /**
     * 设置按钮内边距 单位dp
     * */
    fun setViewPadding(view: View?, left: Float, top: Float, right: Float, bottom: Float) {
        if (view != null) {
            view?.setPadding(ArmsUtils.dip2px(mContext, left), ArmsUtils.dip2px(mContext, top), ArmsUtils.dip2px(mContext, right), ArmsUtils.dip2px(mContext, bottom))
        } else {
            throw Exception("请先调用setRightImageView()或setLeftImageView()或setLeftTextView()或setRightTextView()方法完成按钮初始化")
        }
    }

    /**
     * 设置按钮宽度和高度 单位dp
     * */
    fun setViewWidthAndHeight(imageView: ImageView?, width: Float?, height: Float?) {
        if (imageView != null) {
            var layoutParams = imageView?.layoutParams
            if (width != null)
                layoutParams?.width = ArmsUtils.dip2px(mContext, width)
            if (height != null)
                layoutParams?.height = ArmsUtils.dip2px(mContext, height)
        } else {
            throw Exception("请先调用setRightImageView()或setLeftImageView()或setLeftTextView()或setRightTextView()方法完成按钮初始化")
        }
    }

    fun setRightTextViewStyle(@ColorInt textColor: Int, textSize: Float, textStyle: Int?) {
        setTextViewStyle(tvRightTextButton, textColor, textSize, textStyle)
    }

    fun setLeftTextViewStyle(@ColorInt textColor: Int, textSize: Float, textStyle: Int?) {
        setTextViewStyle(tvLeftTextButton, textColor, textSize, textStyle)
    }

    /**
     * 设置文字样式
     * */
    fun setTextViewStyle(textView: TextView?, @ColorInt textColor: Int, textSize: Float, textStyle: Int?) {
        if (textView != null) {
            textView.setTextColor(textColor)
            textView.textSize = textSize
            if (textStyle != null)
                textView.setTypeface(textView.typeface, textStyle)
        } else {
            throw Exception("请先调用setRightTextView()或setLeftTextView()方法完成按钮初始化")
        }
    }

    private fun setTextViewString(textView: TextView?, text: String) {
        if (textView != null) {
            textView.text = text
        }
    }

    private fun setImageViewResource(imageView: ImageView?, resId: Int) {
        if (imageView != null) {
            imageView.setImageResource(resId)
        }
    }

    /**
     * 左右按钮点击事件设置
     * */
    private fun setViewListener(view: View?, listener: OnClickListener?) {
        if (view != null) {
            if (listener == null) {
                view?.setOnClickListener {
                    if (mContext is Activity) {
                        mContext.finish()
                    }
                }
            } else {
                view?.setOnClickListener(listener)
            }
        }
    }

    /**
     * 显示黑色返回按钮
     * */
    fun showBlackBack() {
        setLeftImageView(R.mipmap.ic_back_login)
        setViewPadding(ivLeftImageButton, 15f, 0f, 15f, 0f)
        setViewWidthAndHeight(ivLeftImageButton, 48f, null)
    }

    /**
     * 显示黑色返回按钮 并且实现点击事件
     * */
    fun showBlackBack(listener: OnClickListener) {
        setLeftImageView(R.mipmap.ic_back_login, listener)
        setViewPadding(ivLeftImageButton, 15f, 0f, 15f, 0f)
        setViewWidthAndHeight(ivLeftImageButton, 48f, null)
    }

    /**
     * 显示白色返回按钮
     * */
    fun showWhiteBack() {
        setLeftImageView(R.mipmap.ic_back_white)
        setViewPadding(ivLeftImageButton, 15f, 0f, 15f, 0f)
        setViewWidthAndHeight(ivLeftImageButton, 48f, null)
    }

    /**
     * 显示白色返回按钮 并且实现点击事件
     * */
    fun showWhiteBack(listener: OnClickListener) {
        setLeftImageView(R.mipmap.ic_back_white, listener)
        setViewPadding(ivLeftImageButton, 17f, 0f, 17f, 0f)
        setViewWidthAndHeight(ivLeftImageButton, 48f, null)
    }

    /**
     * 显示白色返回按钮 并且实现点击事件
     * */
    fun showBigIconWhiteBack(listener: OnClickListener) {
        setLeftImageView(R.mipmap.ic_back_white, listener)
        setViewPadding(ivLeftImageButton, 15f, 0f, 15f, 0f)
        setViewWidthAndHeight(ivLeftImageButton, 48f, null)
    }

    /**
     * 显示右侧加号按钮 并且实现点击事件
     * */
    fun showRightAddButton(listener: OnClickListener) {
        setRightImageView(R.mipmap.ic_add, listener)
        setViewPadding(ivRightImageButton, 16f, 0f, 16f, 0f)
        setViewWidthAndHeight(ivRightImageButton, 47f, null)
    }

    /**
     * 显示右侧文字按钮 并且实现点击事件
     * 默认颜色#666666
     * 字体大小15sp
     * 距离左侧15dp
     * */
    fun showRightTextButton(text: String) {
        showRightTextButton(text, ContextCompat.getColor(mContext, R.color.color_666666), 15f, null, null)
    }

    /**
     * 显示右侧文字按钮 并且实现点击事件
     * 默认颜色#666666
     * 字体大小15sp
     * 距离左侧15dp
     * */
    fun showRightTextButton(text: String, listener: OnClickListener?) {
        showRightTextButton(text, ContextCompat.getColor(mContext, R.color.color_666666), 15f, null, listener)
    }

    /**
     * 显示右侧文字按钮 并且设置样式和实现点击事件
     * */
    fun showRightTextButton(text: String, @ColorInt textColor: Int, textSize: Float, textStyle: Int?, listener: OnClickListener?) {
        setRightTextView(text, listener)
        setViewPadding(tvRightTextButton, 15f, 0f, 15f, 0f)
        setTextViewStyle(tvRightTextButton, textColor, textSize, textStyle)
    }

    /**
     * 标题底部横线是否显示
     */
    fun setBottomLineShow(isShow: Boolean) {
        setBackgroundDividerEnabled(isShow)
    }

    /**
     * 显示标题和返回
     */
    fun showTitleAndBack(@StringRes titleResId: Int) {
        showTitle(titleResId)
        showBlackBack()
    }

    /**
     * 显示标题和返回
     */
    fun showTitleAndBack(@StringRes titleResId: Int, listener: OnClickListener) {
        showTitle(titleResId)
        showBlackBack(listener)
    }

    /**
     * 显示标题和返回
     */
    fun showTitleAndBack(text: String) {
        showTitle(text)
        showBlackBack()
    }

    /**
     * 显示标题和返回
     */
    fun showTitleAndBack(text: String, listener: OnClickListener) {
        showTitle(text)
        showBlackBack(listener)
    }

    /**↓↓↓↓↓↓↓↓↓↓↓↓旧功能封装↓↓↓↓↓↓↓↓↓↓↓**/


    /**
     * 只显示返回可自行处理点击事件
     * @Deprecated
     */
    fun showBackAddListener(resId: Int): View? {
        showBlackBack()
        return ivLeftImageButton
    }

    /**
     * 只显示标题
     * @Deprecated
     */
    fun showTitleOld(title: String): View? {
        showTitle(title)
        return tvTitle
    }

    /**
     * 显示标题和返回
     * @Deprecated
     */
    fun showTitleAndBack(titleResId: Int, isShow: Boolean) {
        showTitle(titleResId)
        showBlackBack()
    }

    /**
     * 显示标题和返回
     * @Deprecated
     */
    fun showTitleAndBack(titleResId: Int, isShow: Boolean, listener: View.OnClickListener) {
        showTitle(titleResId)
        showBlackBack(listener)
    }

    /**
     * 显示标题和返回
     * @Deprecated
     */
    fun showTitleAndBack(titleResId: Int, backResId: Int) {
        showTitle(titleResId)
        showBlackBack()
    }


    /**
     * 显示标题和返回
     * @Deprecated
     */
    fun showTitleAndBack(title: String, backResId: Int, listener: View.OnClickListener) {
        showTitle(title)
        showBlackBack(listener)
    }

    /**
     * 显示标题和返回
     * @Deprecated
     */
    fun showTitleAndBack(title: String, backResId: Int) {
        showTitle(title)
        showBlackBack()
    }

    /**
     * 只显示右侧图片
     * @Deprecated
     */
    fun showIvRight(resId: Int, listener: View.OnClickListener): View? {
        setRightImageView(resId, listener)
        return ivRightImageButton
    }

    /**
     * 只显示右侧文字
     * @Deprecated
     */
    fun showTvRight(rightString: String): View? {
        setRightTextView(rightString)
        return tvRightTextButton
    }

    /**
     * 设置titlebar背景色
     * @Deprecated
     */
    fun setTitleBarBackground(color: Int) {
        setBackgroundAlpha(0)
    }
}
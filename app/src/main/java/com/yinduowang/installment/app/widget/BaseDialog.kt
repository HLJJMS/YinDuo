package com.yinduowang.installment.app.widget

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Typeface
import android.view.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R

class BaseDialog {
    lateinit var mContext: Context
    lateinit var activity: Activity
    lateinit var display: Display

    lateinit var dialog: Dialog
    lateinit var tvTitle: TextView
    lateinit var btnLeft: TextView
    lateinit var btnRight: TextView
    lateinit var vLine: View
    lateinit var rootView: View
    private lateinit var builder: BaseDialogBuilder
    // 标题默认值
    var textTitleSize = 16f
    var textTitleColor = R.color.color_333333
    var textTitleStyle = Typeface.NORMAL
    var textTitleGravity = Gravity.CENTER
    var textTitleLineSpacingExtra = 0f
    // 右侧按钮默认值
    var btnRightTextSize = 16f
    var btnRightTextColor = R.color.colorAccent
    var btnRightTextStyle = Typeface.NORMAL
    // 左侧按钮默认值
    var btnLeftTextSize = 16f
    var btnLeftTextColor = R.color.color_9A9A9A
    var btnLeftTextStyle = Typeface.NORMAL
    var paddingTop = 0
    var paddingBottom = 0
    var paddingLeft = 0
    var paddingRight = 0


    constructor(mContext: Context) {
        this.mContext = mContext
        val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        display = windowManager.defaultDisplay
    }

    constructor(activity: Activity) {
        this.activity = activity
        this.mContext = activity
        val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        display = windowManager.defaultDisplay
    }


    fun builder(): BaseDialogBuilder {
        // 获取Dialog布局
        rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_base_dialog, null)
        // 获取自定义Dialog布局中的控件
        tvTitle = rootView.findViewById<View>(R.id.tvTitle) as TextView
        btnLeft = rootView.findViewById<View>(R.id.btnLeft) as TextView
        btnRight = rootView.findViewById<View>(R.id.btnRight) as TextView
        vLine = rootView.findViewById<View>(R.id.vLine)
        // 定义Dialog布局和参数
        dialog = Dialog(mContext, R.style.AlertDialogStyle)
        dialog.setContentView(rootView)
        // 调整dialog背景大小
        rootView.layoutParams = FrameLayout.LayoutParams((display.width * 0.70).toInt(), LinearLayout.LayoutParams.WRAP_CONTENT)

        paddingTop = ArmsUtils.dip2px(mContext, 22f)
        paddingBottom = ArmsUtils.dip2px(mContext, 26f)

        builder = BaseDialogBuilder(mContext, this)
        return builder
    }

    class BaseDialogBuilder(context: Context, dialog: BaseDialog) {
        val mContext: Context = context
        val mDialog: BaseDialog = dialog

        var textTitle = ""
        var textTitleSize = -1f
        var textTitleColor = -1
        var textTitleStyle = -1
        var textTitleGravity = -1
        var textTitleLineSpacingExtra = -1f

        var btnLeftText = ""
        var btnLeftTextSize = -1f
        var btnLeftTextColor = -1
        var btnLeftTextStyle = -1
        var btnLeftOnClickListener: BtnClickListener? = null
        //        fun isInitializedBtnLeftOnClickListener() = ::btnLeftOnClickListener.isInitialized
        var btnRightText = ""
        var btnRightTextSize = -1f
        var btnRightTextColor = -1
        var btnRightTextStyle = -1
        var btnRightOnClickListener: BtnClickListener? = null
//        fun isInitializedBtnRightOnClickListener() = ::btnRightOnClickListener.isInitialized

        var paddingTop = 0
        var paddingBottom = 0
        var paddingLeft = 0
        var paddingRight = 0

        var canceledOnTouchOutside = true
        var cancelable = true
        var btnClickAndDismissDialog = true

        fun setBtnClickAndDismissDialog(dismiss: Boolean): BaseDialogBuilder {
            var btnClickAndDismissDialog = dismiss
            return this
        }

        fun setCancelable(flag: Boolean): BaseDialogBuilder {
            cancelable = flag
            return this
        }

        fun setCanceledOnTouchOutside(cancel: Boolean): BaseDialogBuilder {
            canceledOnTouchOutside = cancel
            return this
        }

        /**
         * @param text 标题内容
         * */
        fun setTitle(text: String): BaseDialogBuilder {
            textTitle = text
            return this
        }

        /**
         * @param text 标题内容
         * */
        fun setTitle(@StringRes text: Int): BaseDialogBuilder {
            textTitle = mContext.resources.getString(text)
            return this
        }

        /**
         * @param textSize 标题字号 单位sp
         * */
        fun setTitleTextSize(textSize: Float): BaseDialogBuilder {
            textTitleSize = textSize
            return this
        }

        /**
         * @param textColor 标题颜色
         * */
        fun setTitleTextColor(@ColorInt textColor: Int): BaseDialogBuilder {
            textTitleColor = textColor
            return this
        }

        /**
         * @param textColor 标题颜色
         * */
        fun setTitleTextColorResources(textColor: Int): BaseDialogBuilder {
            textTitleColor = ContextCompat.getColor(mContext, textColor)
            return this
        }

        /**
         * @param lineSpacingExtra 标题行间距
         * */

        fun setTitleLineSpacingExtra(lineSpacingExtra: Float): BaseDialogBuilder {
            textTitleLineSpacingExtra = lineSpacingExtra
            return this;
        }


        /**
         * @param textStyle 标题是否加粗
         *                  普通样式 Typeface.NORMAL
         *                  加粗样式 Typeface.BOLD
         * */
        fun setTitleStyle(textStyle: Int): BaseDialogBuilder {
            textTitleStyle = textStyle
            return this
        }

        /**
         * @param textStyle 标题显示方式
         *                  Gravity.CENTER
         * */
        fun setTitleGravity(gravity: Int): BaseDialogBuilder {
            textTitleStyle = gravity
            return this
        }

        /**
         * @param text 标题内容
         * @param textSize 标题字号 单位sp
         * */
        fun setTitle(text: String, textSize: Float): BaseDialogBuilder {
            textTitle = text
            textTitleSize = textSize
            return this
        }

        /**
         * @param text 标题内容
         * @param textSize 标题字号 单位sp
         * */
        fun setTitle(text: Int, textSize: Float): BaseDialogBuilder {
            textTitle = mContext.resources.getString(text)
            textTitleSize = textSize
            return this
        }

        /**
         * @param text 标题内容
         * @param textColor 标题颜色
         * */
        fun setTitle(text: String, @ColorInt textColor: Int): BaseDialogBuilder {
            textTitle = text
            textTitleColor = textColor
            return this
        }

        /**
         * @param text 标题内容
         * @param textColor 标题颜色
         * */
        fun setTitle(@StringRes text: Int, @ColorInt textColor: Int): BaseDialogBuilder {
            textTitle = mContext.resources.getString(text)
            textTitleColor = textColor
            return this
        }

        /**
         * @param text 标题内容
         * @param textSize 标题字号 单位sp
         * @param textColor 标题颜色
         * */
        fun setTitle(text: String, textSize: Float, @ColorInt textColor: Int): BaseDialogBuilder {
            textTitle = text
            textTitleSize = textSize
            textTitleColor = textColor
            return this
        }

        /**
         * @param text 标题内容
         * @param textSize 标题字号 单位sp
         * @param textColor 标题颜色
         * */
        fun setTitle(@StringRes text: Int, textSize: Float, @ColorInt textColor: Int): BaseDialogBuilder {
            textTitle = mContext.resources.getString(text)
            textTitleSize = textSize
            textTitleColor = textColor
            return this
        }

        /**
         * @param text 标题内容
         * @param textSize 标题字号 单位sp
         * @param textColor 标题颜色 @ColorInt
         * @param textStyle 标题是否加粗
         *                  普通样式 Typeface.NORMAL
         *                  加粗样式 Typeface.BOLD
         * */
        fun setTitle(text: String, textSize: Float, @ColorInt textColor: Int, textStyle: Int): BaseDialogBuilder {
            textTitle = text
            textTitleSize = textSize
            textTitleColor = textColor
            textTitleStyle = textStyle
            return this
        }

        /**
         * @param text 标题内容
         * @param textSize 标题字号 单位sp
         * @param textColor 标题颜色 @ColorInt
         * @param textStyle 标题是否加粗
         *                  普通样式 Typeface.NORMAL
         *                  加粗样式 Typeface.BOLD
         * */
        fun setTitle(@StringRes text: Int, textSize: Float, @ColorInt textColor: Int, textStyle: Int): BaseDialogBuilder {
            textTitle = mContext.resources.getString(text)
            textTitleSize = textSize
            textTitleColor = textColor
            textTitleStyle = textStyle
            return this
        }

        /**
         * @param top 标题上内边距高度 单位dp
         * */
        fun setTitlePaddingTop(top: Float): BaseDialogBuilder {
            paddingTop = ArmsUtils.dip2px(mContext, top)
            return this
        }

        /**
         * @param right 标题右内边距高度 单位dp
         * */
        fun setTitlePaddingRight(right: Float): BaseDialogBuilder {
            paddingRight = ArmsUtils.dip2px(mContext, right)
            return this
        }

        /**
         * @param left 标题左内边距高度 单位dp
         * */
        fun setTitlePaddingLeft(left: Float): BaseDialogBuilder {
            paddingLeft = ArmsUtils.dip2px(mContext, left)
            return this
        }

        /**
         * @param bottom 标题下内边距高度 单位dp
         * */
        fun setTitlePaddingBottom(bottom: Float): BaseDialogBuilder {
            paddingBottom = ArmsUtils.dip2px(mContext, bottom)
            return this
        }

        /**
         * @param top 标题上内边距高度 单位dp
         * @param bottom 标题上内边距高度 单位dp
         * @param left 标题左内边距高度 单位dp
         * @param right 标题右内边距高度 单位dp
         * */
        fun setTitlePadding(left: Float, top: Float, right: Float, bottom: Float): BaseDialogBuilder {
            paddingLeft = ArmsUtils.dip2px(mContext, left)
            paddingTop = ArmsUtils.dip2px(mContext, top)
            paddingRight = ArmsUtils.dip2px(mContext, right)
            paddingBottom = ArmsUtils.dip2px(mContext, bottom)
            return this
        }

        /**
         * @param text 右侧按钮内容
         * */
        fun setBtnRight(text: String): BaseDialogBuilder {
            btnRightText = text
            return this
        }

        /**
         * @param text 右侧按钮内容
         * */
        fun setBtnRight(@StringRes text: Int): BaseDialogBuilder {
            btnRightText = mContext.resources.getString(text)
            return this
        }

        /**
         * @param textSize 右侧按钮字号 单位sp
         * */
        fun setBtnRightTextSize(textSize: Float): BaseDialogBuilder {
            btnRightTextSize = textSize
            return this
        }

        /**
         * @param textColor 右侧按钮颜色
         * */
        fun setBtnRightTextColorResources(textColor: Int): BaseDialogBuilder {
            btnRightTextColor = ContextCompat.getColor(mContext, textColor)
            return this
        }

        /**
         * @param textColor 右侧按钮颜色
         * */
        fun setBtnRightTextColor(@ColorInt textColor: Int): BaseDialogBuilder {
            btnRightTextColor = textColor
            return this
        }

        /**
         * @param textStyle 右侧按钮是否加粗
         *                  普通样式 Typeface.NORMAL
         *                  加粗样式 Typeface.BOLD
         * */
        fun setBtnRightStyle(textStyle: Int): BaseDialogBuilder {
            btnRightTextStyle = textStyle
            return this
        }

        /**
         * @param listener 设置按钮点击监听事件
         * */
        fun setBtnRightClickListener(listener: BtnClickListener): BaseDialogBuilder {
            btnRightOnClickListener = listener
            return this
        }

        /**
         * @param text 左侧按钮内容
         * @param listener 设置按钮点击监听事件
         * */
        fun setBtnRight(text: String, listener: BtnClickListener): BaseDialogBuilder {
            btnRightText = text
            btnRightOnClickListener = listener
            return this
        }

        /**
         * @param text 左侧按钮内容
         * @param listener 设置按钮点击监听事件
         * */
        fun setBtnRight(@StringRes text: Int, listener: BtnClickListener): BaseDialogBuilder {
            btnRightText = mContext.resources.getString(text)
            btnRightOnClickListener = listener
            return this
        }

        /**
         * @param text 右侧按钮内容
         * @param textSize 右侧按钮字号 单位sp
         * */
        fun setBtnRight(text: String, textSize: Float): BaseDialogBuilder {
            btnRightText = text
            btnRightTextSize = textSize
            return this
        }

        /**
         * @param text 右侧按钮内容
         * @param textSize 右侧按钮字号 单位sp
         * */
        fun setBtnRight(@StringRes text: Int, textSize: Float): BaseDialogBuilder {
            btnRightText = mContext.resources.getString(text)
            btnRightTextSize = textSize
            return this
        }

        /**
         * @param text 右侧按钮内容
         * @param textColor 右侧按钮颜色
         * */
        fun setBtnRight(text: String, @ColorInt textColor: Int): BaseDialogBuilder {
            btnRightText = text
            btnRightTextColor = textColor
            return this
        }

        /**
         * @param text 右侧按钮内容
         * @param textColor 右侧按钮颜色
         * */
        fun setBtnRight(@StringRes text: Int, @ColorInt textColor: Int): BaseDialogBuilder {
            btnRightText = mContext.resources.getString(text)
            btnRightTextColor = textColor
            return this
        }

        /**
         * @param text 右侧按钮内容
         * @param textSize 右侧按钮字号 单位sp
         * @param textColor 右侧按钮颜色
         * */
        fun setBtnRight(text: String, textSize: Float, @ColorInt textColor: Int): BaseDialogBuilder {
            btnRightText = text
            btnRightTextSize = textSize
            btnRightTextColor = textColor
            return this
        }

        /**
         * @param text 右侧按钮内容
         * @param textSize 右侧按钮字号 单位sp
         * @param textColor 右侧按钮颜色
         * */
        fun setBtnRight(@StringRes text: Int, textSize: Float, @ColorInt textColor: Int): BaseDialogBuilder {
            btnRightText = mContext.resources.getString(text)
            btnRightTextSize = textSize
            btnRightTextColor = textColor
            return this
        }

        /**
         * @param text 右侧按钮内容
         * @param textSize 右侧按钮字号 单位sp
         * @param textColor 右侧按钮颜色 @ColorInt
         * @param textStyle 右侧按钮是否加粗
         *                  普通样式 Typeface.NORMAL
         *                  加粗样式 Typeface.BOLD
         * */
        fun setBtnRight(text: String, textSize: Float, @ColorInt textColor: Int, textStyle: Int, listener: BtnClickListener): BaseDialogBuilder {
            btnRightText = text
            btnRightTextSize = textSize
            btnRightTextColor = textColor
            btnRightTextStyle = textStyle
            btnRightOnClickListener = listener
            return this
        }

        /**
         * @param text 右侧按钮内容
         * @param textSize 右侧按钮字号 单位sp
         * @param textColor 右侧按钮颜色 @ColorInt
         * @param textStyle 右侧按钮是否加粗
         *                  普通样式 Typeface.NORMAL
         *                  加粗样式 Typeface.BOLD
         * */
        fun setBtnRight(@StringRes text: Int, textSize: Float, @ColorInt textColor: Int, textStyle: Int, listener: BtnClickListener): BaseDialogBuilder {
            btnRightText = mContext.resources.getString(text)
            btnRightTextSize = textSize
            btnRightTextColor = textColor
            btnRightTextStyle = textStyle
            btnRightOnClickListener = listener
            return this
        }

        /**
         * @param text 左侧按钮内容
         * */
        fun setBtnLeft(text: String): BaseDialogBuilder {
            btnLeftText = text
            return this
        }

        /**
         * @param text 左侧按钮内容
         * */
        fun setBtnLeft(@StringRes text: Int): BaseDialogBuilder {
            btnLeftText = mContext.resources.getString(text)
            return this
        }

        /**
         * @param textSize 左侧按钮字号 单位sp
         * */
        fun setBtnLeftTextSize(textSize: Float): BaseDialogBuilder {
            btnLeftTextSize = textSize
            return this
        }

        /**
         * @param textColor 左侧按钮颜色
         * */
        fun setBtnLeftTextColorResources(textColor: Int): BaseDialogBuilder {
            btnLeftTextColor = ContextCompat.getColor(mContext, textColor)
            return this
        }

        /**
         * @param textColor 左侧按钮颜色 必须是R.color.white
         * */
        fun setBtnLeftTextColor(@ColorInt textColor: Int): BaseDialogBuilder {
            btnLeftTextColor = textColor
            return this
        }

        /**
         * @param textStyle 左侧按钮是否加粗
         *                  普通样式 Typeface.NORMAL
         *                  加粗样式 Typeface.BOLD
         * */
        fun setBtnLeftStyle(textStyle: Int): BaseDialogBuilder {
            btnLeftTextStyle = textStyle
            return this
        }

        /**
         * @param listener 设置按钮点击监听事件
         * */
        fun setBtnLeftClickListener(listener: BtnClickListener): BaseDialogBuilder {
            btnLeftOnClickListener = listener
            return this
        }

        /**
         * @param text 左侧按钮内容
         * @param listener 设置按钮点击监听事件
         * */
        fun setBtnLeft(text: String, listener: BtnClickListener): BaseDialogBuilder {
            btnLeftText = text
            btnLeftOnClickListener = listener
            return this
        }

        /**
         * @param text 左侧按钮内容
         * @param listener 设置按钮点击监听事件
         * */
        fun setBtnLeft(@StringRes text: Int, listener: BtnClickListener): BaseDialogBuilder {
            btnLeftText = mContext.resources.getString(text)
            btnLeftOnClickListener = listener
            return this
        }

        /**
         * @param text 左侧按钮内容
         * @param textSize 左侧按钮字号 单位sp
         * */
        fun setBtnLeft(text: String, textSize: Float): BaseDialogBuilder {
            btnLeftText = text
            btnLeftTextSize = textSize
            return this
        }

        /**
         * @param text 左侧按钮内容
         * @param textSize 左侧按钮字号 单位sp
         * */
        fun setBtnLeft(@StringRes text: Int, textSize: Float): BaseDialogBuilder {
            btnLeftText = mContext.resources.getString(text)
            btnLeftTextSize = textSize
            return this
        }

        /**
         * @param text 左侧按钮内容
         * @param textColor 左侧按钮颜色 必须是R.color.white
         * */
        fun setBtnLeft(text: String, @ColorInt textColor: Int): BaseDialogBuilder {
            btnLeftText = text
            btnLeftTextColor = textColor
            return this
        }

        /**
         * @param text 左侧按钮内容
         * @param textColor 左侧按钮颜色
         * */
        fun setBtnLeft(@StringRes text: Int, @ColorInt textColor: Int): BaseDialogBuilder {
            btnLeftText = mContext.resources.getString(text)
            btnLeftTextColor = textColor
            return this
        }

        /**
         * @param text 左侧按钮内容
         * @param textSize 左侧按钮字号 单位sp
         * @param textColor 左侧按钮颜色
         * */
        fun setBtnLeft(text: String, textSize: Float, @ColorInt textColor: Int): BaseDialogBuilder {
            btnLeftText = text
            btnLeftTextSize = textSize
            btnLeftTextColor = textColor
            return this
        }

        /**
         * @param text 左侧按钮内容
         * @param textSize 左侧按钮字号 单位sp
         * @param textColor 左侧按钮颜色 必须是R.color.white
         * */
        fun setBtnLeft(@StringRes text: Int, textSize: Float, @ColorInt textColor: Int): BaseDialogBuilder {
            btnLeftText = mContext.resources.getString(text)
            btnLeftTextSize = textSize
            btnLeftTextColor = textColor
            return this
        }

        /**
         * @param text 左侧按钮内容
         * @param textSize 左侧按钮字号 单位sp
         * @param textColor 左侧按钮颜色 @ColorInt
         * @param textStyle 左侧按钮是否加粗
         *                  普通样式 Typeface.NORMAL
         *                  加粗样式 Typeface.BOLD
         * */
        fun setBtnLeft(text: String, textSize: Float, @ColorInt textColor: Int, textStyle: Int, listener: BtnClickListener): BaseDialogBuilder {
            btnLeftText = text
            btnLeftTextSize = textSize
            btnLeftTextColor = textColor
            btnLeftTextStyle = textStyle
            btnLeftOnClickListener = listener
            return this
        }

        /**
         * @param text 左侧按钮内容
         * @param textSize 左侧按钮字号 单位sp
         * @param textColor 左侧按钮颜色 @ColorInt
         * @param textStyle 左侧按钮是否加粗
         *                  普通样式 Typeface.NORMAL
         *                  加粗样式 Typeface.BOLD
         * */
        fun setBtnLeft(@StringRes text: Int, textSize: Float, @ColorInt textColor: Int, textStyle: Int, listener: BtnClickListener): BaseDialogBuilder {
            btnLeftText = mContext.resources.getString(text)
            btnLeftTextSize = textSize
            btnLeftTextColor = textColor
            btnLeftTextStyle = textStyle
            btnLeftOnClickListener = listener
            return this
        }

        interface BtnClickListener {
            fun onClick(dialog: BaseDialog)
        }

        fun create(): BaseDialog {
            return mDialog
        }
    }

    private fun setLayout() {
        // 设置标题内容
        if (builder.textTitle.isNotEmpty()) {
            tvTitle.text = builder.textTitle
        } else {
            throw Exception("！！！！！！！弹窗标题不可以为空！！！！！！！")
        }
        // 设置标题字号
        if (builder.textTitleSize != -1f) {
            tvTitle.textSize = builder.textTitleSize
        } else {
            tvTitle.textSize = textTitleSize
        }
        // 设置标题颜色
        if (builder.textTitleColor != -1) {
            tvTitle.setTextColor(builder.textTitleColor)
        } else {
            tvTitle.setTextColor(ContextCompat.getColor(mContext, textTitleColor))
        }
        // 设置标题是否加粗
        if (builder.textTitleStyle != -1) {
            tvTitle.setTypeface(tvTitle.typeface, builder.textTitleStyle)
        } else {
            tvTitle.setTypeface(tvTitle.typeface, textTitleStyle)
        }
        // 设置标题行间距
        if (builder.textTitleLineSpacingExtra != -1f) {
            tvTitle.setLineSpacing(builder.textTitleLineSpacingExtra, 1f)
        }

        // 设置标题内边距
        var top = paddingTop
        var bottom = paddingBottom
        var left = paddingLeft
        var right = paddingRight
        if (builder.paddingTop != 0) {
            top = builder.paddingTop
        }
        if (builder.paddingBottom != 0) {
            bottom = builder.paddingBottom
        }
        if (builder.paddingLeft != 0) {
            left = builder.paddingLeft
        }
        if (builder.paddingRight != 0) {
            right = builder.paddingRight
        }
        tvTitle.setPadding(left, top, right, bottom)
        // 标题显示方式 默认居中
        if (builder.textTitleGravity != -1) {
            tvTitle.gravity = builder.textTitleGravity
        } else {
            tvTitle.gravity = textTitleGravity
        }

        // 设置右侧按钮
        if (builder.btnRightText.isNotEmpty()) {
            btnRight.visibility = View.VISIBLE
            // 设置右侧按钮显示内容
            btnRight.text = builder.btnRightText
            // 设置标题字号
            if (builder.btnRightTextSize != -1f) {
                btnRight.textSize = builder.btnRightTextSize
            } else {
                btnRight.textSize = btnRightTextSize
            }
            // 设置标题颜色
            if (builder.btnRightTextColor != -1) {
                btnRight.setTextColor(builder.btnRightTextColor)
            } else {
                btnRight.setTextColor(ContextCompat.getColor(mContext, btnRightTextColor))
            }
            // 设置标题是否加粗
            if (builder.btnRightTextStyle != -1) {
                btnRight.setTypeface(btnRight.typeface, builder.btnRightTextStyle)
            } else {
                btnRight.setTypeface(btnRight.typeface, btnRightTextStyle)
            }
            btnRight.setOnClickListener {
                // 设置点击事件
                if (builder.btnRightOnClickListener != null) {
                    builder.btnRightOnClickListener?.let {
                        it.onClick(this)
                        if (builder.btnClickAndDismissDialog) {
                            dismiss()
                        }
                    }
                } else {
                    if (builder.btnClickAndDismissDialog) {
                        dismiss()
                    }
                }
            }
        } else {
            if (!builder.btnLeftText.isNotEmpty()) {
                throw Exception("！！！！！！！弹窗最少要有一个按钮！！！！！！！")
            } else {
                btnRight.visibility = View.GONE
                vLine.visibility = View.GONE
            }
        }
        // 设置左侧按钮
        if (builder.btnLeftText.isNotEmpty()) {
            btnLeft.visibility = View.VISIBLE
            // 设置左侧按钮显示内容
            btnLeft.text = builder.btnLeftText
            // 设置标题字号
            if (builder.btnLeftTextSize != -1f) {
                btnLeft.textSize = builder.btnLeftTextSize
            } else {
                btnLeft.textSize = btnLeftTextSize
            }
            // 设置标题颜色
            if (builder.btnLeftTextColor != -1) {
                btnLeft.setTextColor(builder.btnLeftTextColor)
            } else {
                btnLeft.setTextColor(ContextCompat.getColor(mContext, btnLeftTextColor))
            }
            // 设置标题是否加粗
            if (builder.btnLeftTextStyle != -1) {
                btnLeft.setTypeface(btnLeft.typeface, builder.btnLeftTextStyle)
            } else {
                btnLeft.setTypeface(btnLeft.typeface, btnLeftTextStyle)
            }
            btnLeft.setOnClickListener {
                // 设置点击事件
                if (builder.btnLeftOnClickListener != null) {
                    builder.btnLeftOnClickListener?.let {
                        it.onClick(this)
                        if (builder.btnClickAndDismissDialog) {
                            dismiss()
                        }
                    }
                } else {
                    if (builder.btnClickAndDismissDialog) {
                        dismiss()
                    }
                }
            }
        } else {
            if (!builder.btnRightText.isNotEmpty()) {
                throw Exception("！！！！！！！弹窗最少要有一个按钮！！！！！！！")
            } else {
                vLine.visibility = View.GONE
                btnLeft.visibility = View.GONE
            }
        }
        if (!builder.cancelable)
            dialog.setCancelable(false)
        if (!builder.canceledOnTouchOutside)
            dialog.setCanceledOnTouchOutside(false)
    }

    val isShowing: Boolean
        get() = ::dialog.isInitialized && dialog.isShowing

    fun show() {
        setLayout()
        if (::dialog.isInitialized) {
            dialog.show()
        }
    }

    fun dismiss() {
        if (::dialog.isInitialized) {
            dialog.dismiss()
        }
    }
}

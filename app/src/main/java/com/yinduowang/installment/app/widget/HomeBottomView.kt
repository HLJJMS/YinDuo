package com.yinduowang.installment.app.widget

import android.content.Context
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.tencent.bugly.crashreport.CrashReport
import com.yinduowang.installment.R

/**
 * 首页底部菜单
 */
class HomeBottomView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) ,View.OnClickListener{

    private lateinit var listener: HomeBottomBtnChangeListener
    fun isInitializedListener() = ::listener.isInitialized

    private lateinit var ivHomeBottomOne: ImageView
    private lateinit var ivHomeBottomTwo: ImageView
    private lateinit var ivHomeBottomThree: ImageView
    private lateinit var tvHomeBottomOne: TextView
    private lateinit var tvHomeBottomTwo: TextView
    private lateinit var tvHomeBottomThree: TextView

    var status = TYPE_ONE
        set(status) {
            var isChange = true
            if (isInitializedListener()) {
                isChange = listener.change(status, this.status)
            }
            // 如果没有改变则不执行一下逻辑
            if (!isChange) {
                return
            }
            field = status
            when (status) {
                TYPE_ONE -> {
                    tvHomeBottomOne.setTextColor(ContextCompat.getColor(context,R.color.color_444444))
                    ivHomeBottomOne.setImageResource(R.mipmap.ic_shop)
                    tvHomeBottomTwo.setTextColor(ContextCompat.getColor(context,R.color.color_D0D0D0))
                    ivHomeBottomTwo.setImageResource(R.mipmap.ic_loan_gray)
                    tvHomeBottomThree.setTextColor(ContextCompat.getColor(context,R.color.color_D0D0D0))
                    ivHomeBottomThree.setImageResource(R.mipmap.ic_person_gray)
                }
                TYPE_TWO -> {
                    tvHomeBottomOne.setTextColor(ContextCompat.getColor(context,R.color.color_D0D0D0))
                    ivHomeBottomOne.setImageResource(R.mipmap.ic_shop_gray)
                    tvHomeBottomTwo.setTextColor(ContextCompat.getColor(context,R.color.color_444444))
                    ivHomeBottomTwo.setImageResource(R.mipmap.ic_loan)
                    tvHomeBottomThree.setTextColor(ContextCompat.getColor(context,R.color.color_D0D0D0))
                    ivHomeBottomThree.setImageResource(R.mipmap.ic_person_gray)
                }
                TYPE_THREE -> {
                    tvHomeBottomOne.setTextColor(ContextCompat.getColor(context,R.color.color_D0D0D0))
                    ivHomeBottomOne.setImageResource(R.mipmap.ic_shop_gray)
                    tvHomeBottomTwo.setTextColor(ContextCompat.getColor(context,R.color.color_D0D0D0))
                    ivHomeBottomTwo.setImageResource(R.mipmap.ic_loan_gray)
                    tvHomeBottomThree.setTextColor(ContextCompat.getColor(context,R.color.color_444444))
                    ivHomeBottomThree.setImageResource(R.mipmap.ic_person)
                }
                else -> {
                }
            }
        }

    init {
        initview()
    }

    fun setMenuNames(menuNames : Array<String>){
        tvHomeBottomOne.setText(menuNames[0])
        tvHomeBottomTwo.setText(menuNames[1])
        tvHomeBottomThree.setText(menuNames[2])
    }

    /**
     * 初始化控件
     */

    private fun initview() {
        var view = LayoutInflater.from(context).inflate(R.layout.layout_main_bottom_view, null)
        view.findViewById<LinearLayout>(R.id.llHomeBottomOne).setOnClickListener(this)
        view.findViewById<LinearLayout>(R.id.llHomeBottomTwo).setOnClickListener(this)
        view.findViewById<LinearLayout>(R.id.llHomeBottomThree).setOnClickListener(this)
        ivHomeBottomOne = view.findViewById(R.id.ivHomeBottomOne)
        ivHomeBottomTwo = view.findViewById(R.id.ivHomeBottomTwo)
        ivHomeBottomThree = view.findViewById(R.id.ivHomeBottomThree)
        tvHomeBottomOne = view.findViewById(R.id.tvHomeBottomOne)
        tvHomeBottomTwo = view.findViewById(R.id.tvHomeBottomTwo)
        tvHomeBottomThree = view.findViewById(R.id.tvHomeBottomThree)
        val lp = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER)
        addView(view, lp)
    }

    override fun onClick(view: View) {
        try {
            when (view.id) {
                // 点击第一个按钮
                R.id.llHomeBottomOne -> status = TYPE_ONE
                // 点击第二个按钮
                R.id.llHomeBottomTwo -> status = TYPE_TWO
                // 点击第三个按钮
                R.id.llHomeBottomThree -> status = TYPE_THREE
            }
        } catch (e: Exception) {
            e.printStackTrace()
            CrashReport.postCatchedException(e)
        }
    }

    companion object {
        const val TYPE_ONE = 0
        const val TYPE_TWO = 1
        const val TYPE_THREE = 2
    }



    fun setHomeBottomBtnChangeListener(listener: HomeBottomBtnChangeListener) {
        this.listener = listener
    }

    interface HomeBottomBtnChangeListener {
        fun change(newStatus: Int, oldStatus: Int):Boolean
    }
}

package com.yinduowang.installment.mvp.ui.adapter

import androidx.core.content.ContextCompat
import android.view.ViewGroup
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.utils.ArmsUtils
import com.yinduowang.installment.R

/**
 * Description：支付方式横向分期的那个recycler
 * Author：田羽衡
 * Version：<v1.0，2019/11/1 >
 * LastEditTime：2019/11/1
 * LastEditors:
 * Deprecated： false
 */

class PaymentTypeAdapter(data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_payment_periods, data) {
    var position = 0
    var width = 0;
    override fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        width = ArmsUtils.getScreenWidth(mContext) - ArmsUtils.dip2px(mContext, 16f)
        return super.onCreateDefViewHolder(parent, viewType)
    }

    override fun convert(helper: BaseViewHolder, item: String?) {
        var ll = helper?.getView<LinearLayout>(R.id.ll)
        ll?.layoutParams?.width = width / 4
        helper?.setText(R.id.tvNumber, item + "期")
        if (helper?.layoutPosition == position) {
            helper.setTextColor(R.id.tvNumber, ContextCompat.getColor(mContext, R.color.color_365AF7))
            helper.setBackgroundRes(R.id.tvNumber, R.drawable.bg_payment_periods_select)
        } else {
            helper?.setTextColor(R.id.tvNumber, ContextCompat.getColor(mContext, R.color.color_666666))
            helper?.setBackgroundRes(R.id.tvNumber, R.drawable.bg_payment_periods_null)
        }
    }

    fun setAdapterPosition(position: Int) {
        this.position = position
        notifyDataSetChanged()
    }
}
